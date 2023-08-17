import { useNavigate } from "react-router-dom";
import { styled, useTheme } from "styled-components";
import { Button } from "../../components/Button";
import { Sidebar } from "../../components/sidebar/Sidebar";
import { getAccessToken, getUserInfo } from "../../utils/localStorage";
import { IssueData } from "./IssueDetail";

type IssueDetailSidePanelProps = {
  fetchIssue: () => void;
} & Pick<IssueData, "id" | "assignees" | "labels" | "milestone" | "writer">;

export function IssueDetailSidePanel({
  id: issueId,
  assignees,
  labels,
  milestone,
  writer,
  fetchIssue,
}: IssueDetailSidePanelProps) {
  const theme = useTheme();
  const navigate = useNavigate();

  const loginUserInfo = getUserInfo();

  const patchIssueAssignees = async (ids: number[]) => {
    const response = await fetch(`/api/issues/${issueId}/assignees`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify({
        assignees: ids,
      }),
    });
    const { code, message } = await response.json();

    if (code === 200) {
      fetchIssue();
      return;
    }

    throw new Error(message);
  };

  const patchIssueLabels = async (ids: number[]) => {
    const response = await fetch(`/api/issues/${issueId}/labels`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify({
        labels: ids,
      }),
    });
    const { code, message } = await response.json();

    if (code === 200) {
      fetchIssue();
      return;
    }

    throw new Error(message);
  };

  const patchIssueMilestone = async (id: number | null) => {
    const response = await fetch(`/api/issues/${issueId}/milestones`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify({
        milestone: id,
      }),
    });
    const { code, message } = await response.json();

    if (code === 200) {
      fetchIssue();
      return;
    }

    throw new Error(message);
  };

  const deleteIssue = async () => {
    const response = await fetch(`/api/issues/${issueId}`, {
      method: "DELETE",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const { code, message } = await response.json();

    if (code === 200) {
      navigate("/");
      return;
    }

    throw new Error(message);
  };

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
      {writer.name === loginUserInfo?.name && (
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
      )}
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
