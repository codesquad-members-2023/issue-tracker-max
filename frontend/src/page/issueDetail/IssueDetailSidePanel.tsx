import { styled } from "styled-components";
import { Sidebar } from "../../components/sidebar/Sidebar";

type IssueDetailSidePanelProps = {
  id: number;
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
}

export function IssueDetailSidePanel({
  id,
  assignees,
  labels,
  milestone,
}: IssueDetailSidePanelProps) {
  return (
    <Div>
      <Sidebar
        onAssigneeClick={() => {}}
        onLabelClick={() => {}}
        onMilestoneClick={() => {}}
      />
      {/* <DeleteButton /> */}
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;
