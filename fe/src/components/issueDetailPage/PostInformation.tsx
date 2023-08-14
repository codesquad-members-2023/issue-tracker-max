import { PostInformationHeader } from './PostInformationHeader';
import { PostInformationHeaderMeta } from './PostInformationHeaderMeta';

type Props = {
  issueDetailPageData: IssueDetailPageData;
};

export const PostInformation: React.FC<Props> = ({
  issueDetailPageData,
}: Props) => {
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
          status={issueDetailPageData.status}
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
