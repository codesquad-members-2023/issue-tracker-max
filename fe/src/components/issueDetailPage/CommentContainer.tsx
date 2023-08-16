import { css } from '@emotion/react';
import { Comment } from '@components/common/comment/Comment';
import { AddNewComment } from './AddNewComment';

type Props = {
  issueDetailPageData: IssueDetailPageData;
  // issueId: number;
  // contents: string;
  // createdAt: string;
  // author: User;
  // comments: CommentType[]; // todo 타입
  onAddComment: (comment: CommentType) => void;
  onDeleteComment?: (id?: number) => void;
};

export const CommentContainer: React.FC<Props> = ({
  issueDetailPageData,
  // author,
  // issueId,
  // comments,
  onAddComment,
  onDeleteComment,
}: Props) => {
  return (
    <div css={commentContainerStyle}>
      {issueDetailPageData.comments &&
        issueDetailPageData.comments.map((item) => (
          <Comment
            typeVariant="default"
            key={item.id}
            comment={item}
            issueId={issueDetailPageData.id}
            issueAuthor={issueDetailPageData.author}
            createdAt={item.createdAt}
            defaultValue={item.contents}
            onDeleteComment={onDeleteComment}
          />
        ))}

      <AddNewComment
        issueId={issueDetailPageData.id}
        issueAuthor={issueDetailPageData.author}
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
