// import { Comment } from '@components/common/comment/Commentt';
import { AddNewComment } from './AddNewComment';

type AuthorType = {
  userId: number;
  loginId: string;
  image: string;
};

type Props = {
  contents: string;
  createdAt: string;
  author: AuthorType;
  comments: any[]; // todo 타입
};

export const CommentContainer: React.FC<Props> = ({
  contents,
} // createdAt,
// author,
// comments,
: Props) => {
  console.log('contents', contents);

  // const isCommentsExist = comments.length > 0;

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
        width: '960px',
      }}
    >
      {/* <Comment
        typeVariant="default"
        // letterCount={textAreaValue.length}
        placeholder={contents}
        // onAddFileUrl={onAddFileUrl}
        // onChangeTextArea={onChangeTextArea}
      />
      {isCommentsExist &&
        comments.map((comment) => (
          <Comment
            key={comment.id}
            typeVariant="default"
            placeholder={contents}
          />
        ))} */}

      <AddNewComment />
    </div>
  );
};
