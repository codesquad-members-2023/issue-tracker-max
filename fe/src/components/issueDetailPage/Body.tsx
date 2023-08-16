import { Theme, css } from '@emotion/react';
import { SideBarRightPanel } from './SideBarRightPanel';
import { CommentContainer } from './CommentContainer';
import { Comment } from '@components/common/comment/Comment';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  selectionsOptions: SelectionState['detailPage'];
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
  selectionsOptions,
  selections,
  onAddComment,
  onDeleteComment,
  onChangeSelect,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}) => {
  return (
    <div css={bodyStyle}>
      <div className="container">
        <Comment
          key={issueDetailPageData.id}
          issueId={issueDetailPageData.id}
          typeVariant="issue"
          issueAuthor={issueDetailPageData.author}
          createdAt={issueDetailPageData.createdAt}
          defaultValue={issueDetailPageData.contents}
        />
        <CommentContainer
          issueDetailPageData={issueDetailPageData}
          onAddComment={onAddComment}
          onDeleteComment={onDeleteComment}
        />
      </div>
      <SideBarRightPanel
        issueId={issueDetailPageData.id}
        selectionsOptions={selectionsOptions}
        selections={selections}
        onChangeSelect={onChangeSelect}
        onSingleSelectedMilestone={onSingleSelectedMilestone}
        onMultipleSelectedAssignee={onMultipleSelectedAssignee}
        onMultipleSelectedLabel={onMultipleSelectedLabel}
      />
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
`;
