import { css } from '@emotion/react';
import { Comment } from '@components/common/comment/Comment';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  onAddComment: (comment: any) => void;
};

export const AddNewComment: React.FC<Props> = ({
  issueDetailPageData,
  onAddComment,
}) => {
  return (
    <div css={addNewCommentStyles}>
      <Comment
        issueDetailPageData={issueDetailPageData}
        defaultValue=""
        typeVariant="add"
        onAddComment={onAddComment}
      />
    </div>
  );
};

const addNewCommentStyles = css`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 24px;
`;
