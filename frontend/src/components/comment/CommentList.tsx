import { styled } from "styled-components";
import { CommentItem } from "./CommentItem";

type CommentListProps = {
  comments: {
    id: number;
    userId: string;
    avatarUrl: string;
    content: string;
    createdAt: Date;
    modifiedAt: Date | null;
  }[];
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  fetchIssue: () => void;
};

export function CommentList({
  comments,
  writer,
  fetchIssue,
}: CommentListProps) {
  return (
    <Div>
      {comments.map((comment) => (
        <CommentItem key={comment.id} {...{ ...comment, writer, fetchIssue }} />
      ))}
    </Div>
  );
}

const Div = styled.div`
  align-self: stretch;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;
