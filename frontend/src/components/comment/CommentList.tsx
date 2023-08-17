import { styled } from "styled-components";
import { CommentItem } from "./CommentItem";
import { IssueData } from "../../page/issueDetail/IssueDetail";

type CommentListProps = {
  fetchIssue: () => void;
} & Pick<IssueData, "comments" | "writer">;

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
  gap: 16px;
`;
