import { css } from '@emotion/react';
import { Comment } from '@components/common/comment/Comment';
import { AddNewComment } from './AddNewComment';

type Props = {
  issueId: number;
  contents: string;
  createdAt: string;
  author: User;
  comments: CommentType[]; // todo 타입
  onAddComment: (comment: CommentType) => void;
};

export const CommentContainer: React.FC<Props> = ({
  author,
  issueId,
  comments,
  onAddComment,
}: Props) => {
  return (
    <div css={commentContainerStyle}>
      {comments &&
        comments.map((item) => (
          <Comment
            typeVariant="default"
            key={item.id}
            comment={item}
            issueId={issueId}
            issueAuthor={author}
            createdAt={item.createdAt}
            defaultValue={item.contents}
          />
        ))}

      <AddNewComment
        issueId={issueId}
        issueAuthor={author}
        onAddComment={onAddComment}
      />
    </div>
  );
};

const commentContainerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 960px;
`;
