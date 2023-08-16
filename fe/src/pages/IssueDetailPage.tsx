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
import { DetailPageDataProvider } from 'context/DetailContext';

type Props = {};

export const IssueDetailPage: React.FC = ({}) => {
  const { id } = useParams<{ id: string }>();

  const [issueDetailPageData, setIssueDetailPageData] =
    useState<IssueDetailPageData>(initialPageData);

  const [selectionsIds, setSelectionsIds] = useState<
    SelectionState['newIssuePage']
  >({
    assignees: [],
    labels: [],
    milestones: null,
  });

  const [selectionsOptions, setSelectionsOptions] = useState<
    SelectionState['detailPage']
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

      setSelectionsOptions({
        assignees: pageData.assignees,
        labels: pageData.labels,
        milestones: pageData.milestone,
      });

      setSelectionsIds({
        assignees: initialAssignees,
        labels: initialLabels,
        milestones: initialMilestone,
      });
    }
  };

  // 패널을 두번 닫아줘야 변경이 됨
  const onChangeSelect = async (key: string) => {
    if (!id) {
      return;
    }
    try {
      switch (key) {
        case 'assignees':
          console.log('어사이니 변경', selectionsIds.assignees);
          const updatedAssignees = await editIssueAssignees(
            id,
            selectionsIds.assignees,
          );
          setIssueDetailPageData((prev) => ({
            ...prev,
            assignees: updatedAssignees,
          }));
          break;

        case 'labels':
          console.log('라벨 변경', selectionsIds.labels);
          const updatedLabels = await editIssueLabel(id, selectionsIds.labels);
          setIssueDetailPageData((prev) => ({
            ...prev,
            labels: updatedLabels,
          }));
          break;

        case 'milestones':
          console.log('마일스톤 변경', selectionsIds.milestones);
          const updatedMilestone = await editIssueMilestone(
            id,
            selectionsIds.milestones,
          );
          setIssueDetailPageData((prev) => ({
            ...prev,
            milestone: updatedMilestone,
          }));
          break;

        default:
          break;
      }
    } catch (err) {
      console.error('에러인뎁쇼', err);
    } finally {
      console.log('음');
    }
  };

  const onMultipleSelectedAssignee = (id: number) => {
    setSelectionsIds((prev) => ({
      ...prev,
      assignees: prev.assignees.includes(id)
        ? prev.assignees.filter((itemId) => itemId !== id)
        : [...prev.assignees, id],
    }));
  };

  const onMultipleSelectedLabel = (id: number) => {
    setSelectionsIds((prev) => ({
      ...prev,
      labels: prev.labels.includes(id)
        ? prev.labels.filter((itemId) => itemId !== id)
        : [...prev.labels, id],
    }));
  };

  const onSingleSelectedMilestone = (id: number) => {
    setSelectionsIds((prev) => ({
      ...prev,
      milestones: prev.milestones === id ? null : id,
    }));
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
        onAddComment={onAddComment}
        onDeleteComment={onDeleteComment}
        //
        selectionsOptions={selectionsOptions}
        selections={selectionsIds}
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
