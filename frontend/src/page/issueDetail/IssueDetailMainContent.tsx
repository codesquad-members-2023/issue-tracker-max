import { styled } from "styled-components";
import { CommentEditor } from "../../components/comment/CommentEditor";
import { CommentList } from "../../components/comment/CommentList";
import { IssueContent } from "./IssueContent";
import { IssueDetailBodyProps } from "./IssueDetailBody";

export type IssueDetailMainContentProps = Omit<
  IssueDetailBodyProps,
  "assignees" | "labels" | "milestone"
>;

export function IssueDetailMainContent(props: IssueDetailMainContentProps) {
  return (
    <Div>
      <IssueContent {...props} />
      <CommentList
        comments={props.comments}
        writer={props.writer}
        fetchIssue={props.fetchIssue}
      />
      <CommentEditor issueId={props.id} fetchIssue={props.fetchIssue} />
    </Div>
  );
}

const Div = styled.div`
  flex: 1 0 0;
  max-width: 960px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;
