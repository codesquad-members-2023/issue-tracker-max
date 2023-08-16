import { css } from '@emotion/react';
import { Comment } from '@components/common/comment/Comment';
import { AddNewComment } from './AddNewComment';

type Props = {
  issueId: number;
  contents: string;
  createdAt: string;
  author: User;
  comments: any[]; // todo 타입
  onAddComment: (comment: Comment) => void;
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
        comments.map((comment) => (
          <Comment
            key={comment.id}
            commentId={comment.id}
            issueId={issueId}
            issueAuthor={author} //이슈 작성자 정보
            userId={comment.author.userId} //코멘트 작성자 id
            loginId={comment.author.loginId}
            commentAuthor={comment.author} //코멘트 작성자 정보
            typeVariant="default"
            defaultValue={comment.contents}
          />
        ))}

      <AddNewComment
        issueId={issueId}
        issueAuthor={author}
        userId={author.userId}
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
