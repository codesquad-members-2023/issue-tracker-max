import { css } from '@emotion/react';
import { Comment } from '@components/common/comment/Comment';

type Props = {
  issueId: number;
  issueAuthor: User;
  userId: number;
  onAddComment: (comment: any) => void;
};

export const AddNewComment: React.FC<Props> = ({
  issueId,
  issueAuthor,
  userId,
  onAddComment,
}) => {
  // console.log('issueId', issueId);
  // console.log('issueAuthor', issueAuthor);
  // console.log('userId', userId);

  return (
    <div css={addNewCommentStyles}>
      <Comment
        issueId={issueId}
        issueAuthor={issueAuthor}
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
