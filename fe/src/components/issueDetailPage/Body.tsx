import { useTheme } from '@emotion/react';
import { SideBarRightPanel } from './SideBarRightPanel';
import { CommentContainer } from './CommentContainer';
import { IssueContainer } from './IssueContainer';
type Props = {
  issueDetailPageData: IssueDetailPageData;
};

export const Body: React.FC<Props> = ({ issueDetailPageData }: Props) => {
  const theme = useTheme() as any;

  const author = {
    userId: 1,
    loginId: '작성자입니다',
    image: '이미지url',
  };

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
          issueId={issueDetailPageData.id}
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
        />
      </div>

      <SideBarRightPanel />
    </div>
  );
};
