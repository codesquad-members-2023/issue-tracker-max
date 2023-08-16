import { css } from '@emotion/react';
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
      <div css={postInformationStyle}>
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

const postInformationStyle = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
`;
