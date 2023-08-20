import { styled } from "styled-components";
import { IssueData } from "./IssueDetail";
import { IssueDetailMainContent } from "./IssueDetailMainContent";
import { IssueDetailSidePanel } from "./IssueDetailSidePanel";

export type IssueDetailBodyProps = {
  fetchIssue: () => void;
} & Omit<IssueData, "title" | "status" | "statusModifiedAt">;

export function IssueDetailBody(props: IssueDetailBodyProps) {
  return (
    <Div>
      <IssueDetailMainContent {...props} />
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
