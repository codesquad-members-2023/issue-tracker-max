import { PostInformationHeader } from './PostInformationHeader';
import { PostInformationHeaderMeta } from './PostInformationHeaderMeta';

type Props = {
  issueDetailPageData: IssueDetailPageData;
};

export const PostInformation: React.FC<Props> = ({
  issueDetailPageData,
}: Props) => {
  console.log('타이틀 확인', issueDetailPageData.title);

  return (
    <>
      <div
        css={{
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
        }}
      >
        <PostInformationHeader
          title={issueDetailPageData.title}
          id={issueDetailPageData.id}
        />
        <PostInformationHeaderMeta
          status={issueDetailPageData.status}
          createdAt={issueDetailPageData.createdAt}
          author={issueDetailPageData.author}
          comments={issueDetailPageData.comments}
        />
      </div>
    </>
  );
};
