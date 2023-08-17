import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { IssueAssignee } from "../../components/sidebar/AddAssignee";
import { IssueLabel } from "../../components/sidebar/AddLabel";
import { IssueMilestone } from "../../components/sidebar/AddMilestone";
import { getAccessToken } from "../../utils/localStorage";
import { NewIssueBody } from "./NewIssueBody";
import { NewIssueFooter } from "./NewIssueFooter";

export function NewIssue() {
  const navigate = useNavigate();

  const [assignees, setAssignees] = useState<IssueAssignee[]>([]);
  const [labels, setLabels] = useState<IssueLabel[]>([]);
  const [milestone, setMilestone] = useState<IssueMilestone | null>(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const invalidTitle = title.length === 0 || title.length > 50;

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const onContentChange = (value: string) => {
    setContent(value);
  };

  const updateIssueAssignees = useCallback((assignees: IssueAssignee[]) => {
    setAssignees(assignees);
  }, []);

  const updateIssueLabels = useCallback((labels: IssueLabel[]) => {
    setLabels(labels);
  }, []);

  const updateIssueMilestone = useCallback(
    (milestone: IssueMilestone | null) => {
      setMilestone(milestone);
    },
    [],
  );

  const onSubmitButtonClick = async () => {
    if (invalidTitle) {
      return;
    }

    const issueData = {
      title: title,
      content: content,
      assignees: assignees.map((assignee) => assignee.id),
      labels: labels.map((label) => label.id),
      milestone: milestone?.id ?? null,
    };

    const response = await fetch("/api/issues", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify(issueData),
    });
    const { code, data } = await response.json();

    if (code === 201) {
      navigate(`/issues/${data.savedIssueId}`);
      return;
    }

    throw new Error(data[0].defaultMessage);
  };

  const onCancelButtonClick = () => {
    navigate("/");
  };

  return (
    <Div>
      <NewIssueHeader>새로운 이슈 작성</NewIssueHeader>
      <Line />
      <NewIssueBody
        title={title}
        content={content}
        invalidTitle={invalidTitle}
        issueAssignees={assignees}
        issueLabels={labels}
        issueMilestone={milestone}
        onAssigneeClick={{ args: "DataArray", handler: updateIssueAssignees }}
        onLabelClick={{ args: "DataArray", handler: updateIssueLabels }}
        onMilestoneClick={{ args: "Data", handler: updateIssueMilestone }}
        onTitleChange={onTitleChange}
        onContentChange={onContentChange}
      />
      <Line />
      <NewIssueFooter
        invalidTitle={invalidTitle}
        onSubmitButtonClick={onSubmitButtonClick}
        onCancelButtonClick={onCancelButtonClick}
      />
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  height: 818px;

  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const NewIssueHeader = styled.div`
  font: ${({ theme }) => theme.font.displayBold32};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;

const Line = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  margin: 0;
`;
