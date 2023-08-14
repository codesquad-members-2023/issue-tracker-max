import { styled } from "styled-components";
import { Sidebar } from "../../components/sidebar/Sidebar";

type IssueDetailSidePanelProps = {
  id: number;
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
}

export function IssueDetailSidePanel({
  id: issueId,
  assignees,
  labels,
  milestone,
  fetchIssue
}: IssueDetailSidePanelProps) {

  const patchIssueAssignees = async (ids: number[]) => {
    await fetch(
      `/api/issues/${issueId}/assignees`,
      {
        method: "PATCH",
        body: JSON.stringify({
          assignees: ids,
        }),
      },
    );

    fetchIssue();
  }

  const patchIssueLabels = async (ids: number[]) => {
    await fetch(
      `/api/issues/${issueId}/labels`,
      {
        method: "PATCH",
        body: JSON.stringify({
          labels: ids,
        }),
      },
    );

    fetchIssue();
  }

  const patchIssueMilestone = async (id: number | null) => {
    await fetch(
      `/api/issues/${issueId}/milestones`,
      {
        method: "PATCH",
        body: JSON.stringify({
          milestone: id,
        }),
      },
    );

    fetchIssue();
  }

  return (    
    <Div>
      <Sidebar
        issueAssignees={assignees}
        issueLabels={labels}
        issueMilestone={milestone}
        onAssigneeClick={patchIssueAssignees}
        onLabelClick={patchIssueLabels}
        onMilestoneClick={patchIssueMilestone}
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
