import { useState, useEffect } from 'react';
import { Body } from '@components/issueDetailPage/Body';
import { PostInformation } from '@components/issueDetailPage/PostInformation';
import {
  deleteComment,
  editIssueAssignees,
  editIssueLabel,
  editIssueMilestone,
  editIssueStatus,
  getIssueDetail,
} from 'apis/api';
import { useParams } from 'react-router-dom';

export const IssueDetailPage: React.FC = () => {
  const { id } = useParams();
  const [issueDetailPageData, setIssueDetailPageData] =
    useState<IssueDetailPageData>(initialPageData);

  const [selectionsIds, setSelectionsIds] = useState<
    SelectionState['newIssuePage']
  >({
    assignees: [],
    labels: [],
    milestones: null,
  });

  useEffect(() => {
    fetchIssueDetailPageData();
  }, []);

  const fetchIssueDetailPageData = async () => {
    if (id) {
      const pageData: IssueDetailPageData = await getIssueDetail(id);

      setIssueDetailPageData(pageData);

      const initialAssignees = pageData.assignees.map(
        (assignee) => assignee.userId,
      );
      const initialLabels = pageData.labels.map((label) => label.id);
      const initialMilestone = pageData.milestone
        ? pageData.milestone.id
        : null;

      setSelectionsIds({
        assignees: initialAssignees,
        labels: initialLabels,
        milestones: initialMilestone,
      });
    }
  };

  const onChangeSelect = (key: string) => {
    if (!issueDetailPageData.id) {
      return;
    }

    switch (key) {
      case 'assignees':
        editIssueAssignees(issueDetailPageData.id, selectionsIds.assignees);
        break;

      case 'labels':
        editIssueLabel(issueDetailPageData.id, selectionsIds.labels);
        break;

      case 'milestones':
        editIssueMilestone(issueDetailPageData.id, selectionsIds.milestones);
        break;

      default:
        break;
    }
  };

  const onMultipleSelectedAssignee = (id: number) => {
    setSelectionsIds((prev) => {
      return {
        ...prev,
        assignees: prev.assignees.includes(id)
          ? prev.assignees.filter((itemId) => itemId !== id)
          : [...prev.assignees, id],
      };
    });
  };

  const onMultipleSelectedLabel = (id: number) => {
    setSelectionsIds((prev) => {
      return {
        ...prev,
        labels: prev.labels.includes(id)
          ? prev.labels.filter((itemId) => itemId !== id)
          : [...prev.labels, id],
      };
    });
  };

  const onSingleSelectedMilestone = (id: number) => {
    setSelectionsIds((prev) => {
      return {
        ...prev,
        milestones: prev.milestones === id ? null : id,
      };
    });
  };

  const onAddComment = (comment: any) => {
    setIssueDetailPageData({
      ...issueDetailPageData,
      comments: [...issueDetailPageData.comments, comment],
    });
  };

  const onDeleteComment = (id?: number) => {
    deleteComment(id);
    setIssueDetailPageData({
      ...issueDetailPageData,
      comments: issueDetailPageData.comments.filter(
        (comment) => comment.id !== id,
      ),
    });
  };

  const onToggleIssueStatus = (status: string, id: number) => {
    const newStatus = status === 'open' ? 'closed' : 'open';
    editIssueStatus([id], newStatus);
    setIssueDetailPageData({
      ...issueDetailPageData,
      status: newStatus,
    });
  };

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      <PostInformation
        issueDetailPageData={issueDetailPageData}
        onToggleIssueStatus={onToggleIssueStatus}
      />
      <Body
        issueDetailPageData={issueDetailPageData}
        selections={selectionsIds}
        onAddComment={onAddComment}
        onDeleteComment={onDeleteComment}
        onChangeSelect={onChangeSelect}
        onSingleSelectedMilestone={onSingleSelectedMilestone}
        onMultipleSelectedAssignee={onMultipleSelectedAssignee}
        onMultipleSelectedLabel={onMultipleSelectedLabel}
      />
    </div>
  );
};

const initialPageData: IssueDetailPageData = {
  id: 0,
  title: '',
  contents: '',
  status: 'open',
  createdAt: '',
  author: {
    userId: 0,
    loginId: '',
    image: '',
  },
  assignees: [],
  labels: [],
  milestone: {
    id: 0,
    name: '',
    progress: 0,
  },
  comments: [],
};
