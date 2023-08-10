import { styled } from "styled-components";
import { IssueDetailMainContent } from "./IssueDetailMainContent";
import { IssueDetailSidePanel } from "./IssueDetailSidePanel";

type IssueDetailBodyProps = {
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
  assignees: {
    id: number;
    loginId: string;
    avatarUrl: string;
  }[];
  labels: {
    id: number;
    name: string;
    color: string;
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
