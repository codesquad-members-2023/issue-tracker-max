import { Body } from '@components/issueDetailPage/Body';
import { PostInformation } from '@components/issueDetailPage/PostInformation';

type Props = {};

export const IssueDetailPage: React.FC = ({}: Props) => {
  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      <PostInformation />
      <Body />
    </div>
  );
};
