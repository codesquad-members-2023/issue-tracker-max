import { styled } from "styled-components";
import { CommentEditor } from "../../components/comment/CommentEditor";
import { CommentList } from "../../components/comment/CommentList";
import { IssueContent } from "./IssueContent";

type IssueDetailMainContentProps = {
  id: number;
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  comments: {
    id: number;
    userId: string;
    avatarUrl: string;
    content: string;
    createdAt: Date;
    modifiedAt: Date | null;
  }[];
  fetchIssue: () => void;
};

export function IssueDetailMainContent(props: IssueDetailMainContentProps) {
  return (
    <Div>
      <IssueContent {...props} />
      <CommentList
        {...{
          comments: props.comments,
          writer: props.writer,
          fetchIssue: props.fetchIssue,
        }}
      />
      <CommentEditor {...{issueId: props.id, fetchIssue: props.fetchIssue}} />
    </Div>
  );
}

const Div = styled.div`
  flex: 1 0 0;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 24px;
`;
