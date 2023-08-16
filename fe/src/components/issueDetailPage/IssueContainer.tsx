import { Comment } from '@components/common/comment/Comment';

type AuthorType = {
  userId: number;
  loginId: string;
  image: string;
};

type Props = {
  issueId: number;
  author: AuthorType;
  contents: string;
  createdAt: string;
};

export const IssueContainer: React.FC<Props> = ({
  issueId,
  author,
  contents,
  createdAt,
}: Props) => {
  return (
    <Comment
      key={issueId}
      issueId={issueId}
      typeVariant="issue"
      issueAuthor={author}
      userId={author.userId}
      loginId={author.loginId}
      createdAt={createdAt}
      defaultValue={contents}
    />
  );
};
