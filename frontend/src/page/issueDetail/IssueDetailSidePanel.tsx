import { styled, useTheme } from "styled-components";
import { Button } from "../../components/Button";
import { Sidebar } from "../../components/sidebar/Sidebar";
import { useNavigate } from "react-router-dom";
import { IssueData } from "./IssueDetail";

type IssueDetailSidePanelProps = {
  fetchIssue: () => void;
} & Pick<IssueData, "id" | "assignees" | "labels" | "milestone">;

export function IssueDetailSidePanel({
  id: issueId,
  assignees,
  labels,
  milestone,
  fetchIssue,
}: IssueDetailSidePanelProps) {
  const theme = useTheme();
  const navigate = useNavigate();

  const patchIssueAssignees = async (ids: number[]) => {
    await fetch(`/api/issues/${issueId}/assignees`, {
      method: "PATCH",
      body: JSON.stringify({
        assignees: ids,
      }),
    });

    fetchIssue();
  };

  const patchIssueLabels = async (ids: number[]) => {
    await fetch(`/api/issues/${issueId}/labels`, {
      method: "PATCH",
      body: JSON.stringify({
        labels: ids,
      }),
    });

    fetchIssue();
  };

  const patchIssueMilestone = async (id: number | null) => {
    await fetch(`/api/issues/${issueId}/milestones`, {
      method: "PATCH",
      body: JSON.stringify({
        milestone: id,
      }),
    });

    fetchIssue();
  };

  const deleteIssue = async () => {
    await fetch(`/api/issues/${issueId}`, {
      method: "DELETE",
    });

    navigate("/");
  }

  return (
    <Div>
      <Sidebar
        issueAssignees={assignees}
        issueLabels={labels}
        issueMilestone={milestone}
        onAssigneeClick={{ args: "NumberArray", handler: patchIssueAssignees }}
        onLabelClick={{ args: "NumberArray", handler: patchIssueLabels }}
        onMilestoneClick={{ args: "Number", handler: patchIssueMilestone }}
      />
      <Delete>
        <Button
          buttonType="Ghost"
          size="S"
          icon="Trash"
          color={theme.color.dangerTextDefault}
          onClick={deleteIssue}
        >
          이슈 삭제
        </Button>
      </Delete>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;

const Delete = styled.div`
  margin-right: 16px;

  & button > div {
    gap: 4px;
  }

  & span {
    padding: 0px;
  }
`;
