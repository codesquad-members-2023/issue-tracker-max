import { useTheme } from '@emotion/react';
import { SideBarRightPanel } from './SideBarRightPanel';
import { CommentContainer } from './CommentContainer';
import { IssueContainer } from './IssueContainer';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  onAddComment: (comment: Comment) => void;
  //
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
  //
  selectionsOptions,
  selections,
  onChangeSelect,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}: Props) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        gap: '32px',
        borderTop: `${theme.border.default} ${theme.neutral.border.default}`,
        paddingTop: '24px',
      }}
    >
      <div
        css={{
          width: '960px',
          display: 'flex',
          flexDirection: 'column',
          gap: '24px',
        }}
      >
        <IssueContainer
          issueId={issueDetailPageData.id} // 페이지 데이터만 내리기?
          author={issueDetailPageData.author}
          contents={issueDetailPageData.contents}
          createdAt={issueDetailPageData.createdAt}
        />
        <CommentContainer
          issueId={issueDetailPageData.id}
          contents={issueDetailPageData.contents}
          createdAt={issueDetailPageData.createdAt}
          author={issueDetailPageData.author}
          comments={issueDetailPageData.comments}
          onAddComment={onAddComment}
        />
      </div>

      <SideBarRightPanel
        issueId={issueDetailPageData.id}
        issueDetailPageData={issueDetailPageData}
        selectionsOptions={selectionsOptions}
        //
        selections={selections}
        onChangeSelect={onChangeSelect}
        onSingleSelectedMilestone={onSingleSelectedMilestone}
        onMultipleSelectedAssignee={onMultipleSelectedAssignee}
        onMultipleSelectedLabel={onMultipleSelectedLabel}
      />
    </div>
  );
};
