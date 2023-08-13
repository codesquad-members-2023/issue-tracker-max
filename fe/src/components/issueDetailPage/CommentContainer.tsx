import { Comment } from '@components/common/comment/Comment';
import { useState } from 'react';
import { AddNewComment } from './AddNewComment';

// type AuthorType = {
//   userId: number;
//   loginId: string;
//   image: string;
// };

type Props = {
  issueId: number;
  contents: string;
  createdAt: string;
  author: User;
  comments: any[]; // todo 타입
};

export const CommentContainer: React.FC<Props> = ({
  author,
  issueId,
  comments,
}: Props) => {
  const isCommentsExist = comments.length > 0;

  const [commentList, setCommentList] = useState<any[]>(comments);

  const onAddComment = (comment: any) => {
    console.log('작동오케이?');

    setCommentList([...commentList, comment]);
  };

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
        width: '960px',
      }}
    >
      {isCommentsExist &&
        commentList.map((comment) => (
          <Comment
            key={comment.id}
            issueId={issueId}
            issueAuthor={author} //이슈 작성자 정보
            userId={comment.author.userId} //코멘트 작성자 id
            loginId={comment.author.loginId}
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
