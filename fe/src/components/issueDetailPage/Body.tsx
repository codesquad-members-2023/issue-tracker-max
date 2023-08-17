import { Theme, css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { CommentContainer } from './CommentContainer';
import { Comment } from '@components/common/comment/Comment';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { Button } from '@components/common/Button';
import { deleteIssue } from 'apis/api';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  selections: SelectionState['newIssuePage'];
  onAddComment: (comment: CommentType) => void;
  onDeleteComment?: (id?: number) => void;
  onChangeSelect?: (key: string) => void;
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
};

export const Body: React.FC<Props> = ({
  issueDetailPageData,
  selections,
  onAddComment,
  onDeleteComment,
  onChangeSelect,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}) => {
  const [isDeleteError, setIsDeleteError] = useState<boolean>(false);
  const navigate = useNavigate();

  const onDeleteIssue = async () => {
    try {
      setIsDeleteError(false);
      await deleteIssue(issueDetailPageData.id);
      navigate('/issues');
    } catch (error) {
      console.error(error);
      setIsDeleteError(true);
    }
  };

  return (
    <div css={bodyStyle}>
      <div className="container">
        <Comment
          typeVariant="issue"
          key={issueDetailPageData.id}
          issueDetailPageData={issueDetailPageData}
          createdAt={issueDetailPageData.createdAt}
          defaultValue={issueDetailPageData.contents}
        />
        <CommentContainer
          issueDetailPageData={issueDetailPageData}
          onAddComment={onAddComment}
          onDeleteComment={onDeleteComment}
        />
      </div>
      <div className="rightPanel">
        <SideBar>
          <ListSideBar
            issueDetailPageData={issueDetailPageData}
            onSingleSelectedMilestone={onSingleSelectedMilestone}
            onMultipleSelectedAssignee={onMultipleSelectedAssignee}
            onMultipleSelectedLabel={onMultipleSelectedLabel}
            selections={selections}
            onChangeSelect={onChangeSelect}
          />
        </SideBar>
        <Button typeVariant="ghost" size="S" onClick={onDeleteIssue}>
          <Trash className="button-delete" />
          <span className="button-delete">이슈 삭제</span>
        </Button>
        {isDeleteError && (
          <span className="text-error">이슈 삭제에 실패했습니다.</span>
        )}
      </div>
    </div>
  );
};

const bodyStyle = (theme: Theme) => css`
  width: 100%;
  display: flex;
  gap: 32px;
  bordertop: ${theme.border.default} ${theme.neutral.border.default};
  paddingtop: 24px;

  .container {
    width: 960px;
    display: flex;
    flex-direction: column;
    gap: 24px;
  }

  .rightPanel {
    display: flex;
    flex-direction: column;
    gap: 16px;
    align-items: flex-end;

    & span {
      color: ${theme.neutral.text.default};
      font: ${theme.fonts.availableMedium16};
    }

    .button-delete {
      color: ${theme.danger.text.default};
      stroke: ${theme.danger.text.default};
    }

    .text-error {
      color: ${theme.danger.text.default};
    }
  }
`;
