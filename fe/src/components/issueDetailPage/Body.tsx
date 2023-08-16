import { Theme, css } from '@emotion/react';
import { SideBarRightPanel } from './SideBarRightPanel';
import { CommentContainer } from './CommentContainer';
import { IssueContainer } from './IssueContainer';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  onAddComment: (comment: CommentType) => void;
  onDeleteComment?: (id?: number) => void;
  selectionsOptions: SelectionState['detailPage'];
  selections: SelectionState['newIssuePage'];
  onChangeSelect?: (key: string) => void;
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
};

export const Body: React.FC<Props> = ({
  issueDetailPageData,
  onAddComment,
  onDeleteComment,
  selectionsOptions,
  selections,
  onChangeSelect,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}: Props) => {
  return (
    <div css={bodyStyle}>
      <div className="container">
        <IssueContainer
          issueId={issueDetailPageData.id}
          author={issueDetailPageData.author}
          contents={issueDetailPageData.contents}
          createdAt={issueDetailPageData.createdAt}
        />
        <CommentContainer
          issueDetailPageData={issueDetailPageData}
          // issueId={issueDetailPageData.id}
          // contents={issueDetailPageData.contents}
          // createdAt={issueDetailPageData.createdAt}
          // author={issueDetailPageData.author}
          // comments={issueDetailPageData.comments}
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
