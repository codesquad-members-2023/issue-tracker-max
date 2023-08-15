import { styled } from "styled-components";
import { IssueDetailMainContent } from "./IssueDetailMainContent";
import { IssueDetailSidePanel } from "./IssueDetailSidePanel";

type IssueDetailBodyProps = {
  id: number;
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
  reactions: {
    unicode: string;
    users: string[];
    selectedUserReactionId: number;
  }[];
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
    reactions: {
      unicode: string;
      users: string[];
      selectedUserReactionId: number;
    }[]
  }[];
  assignees: {
    id: number;
    name: string;
    avatarUrl: string;
  }[];
  labels: {
    id: number;
    name: string;
    color: "LIGHT" | "DARK";
    background: string;
  }[];
  milestone: {
    id: number;
    name: string;
    issues: {
      openedIssueCount: number;
      closedIssueCount: number;
    };
  } | null;
  fetchIssue: () => void;
};

export function IssueDetailBody(props: IssueDetailBodyProps) {
  return (
    <Div>
      <IssueDetailMainContent {...props}/>
      <IssueDetailSidePanel {...props} />
    </Div>
  );
}

const Div = styled.div`
  align-self: stretch;
  display: flex;
  align-items: flex-start;
  gap: 32px;
`;
