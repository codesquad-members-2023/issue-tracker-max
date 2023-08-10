import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { NewIssueBody } from "./NewIssueBody";
import { NewIssueFooter } from "./NewIssueFooter";

export function NewIssue() {
  const [assignees, setAssignees] = useState<number[]>([]);
  const [labels, setLabels] = useState<number[]>([]);
  const [milestone, setMilestone] = useState<number | null>(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [invalidTitle, setInvalidTitle] = useState(false);
  const navigate = useNavigate();

  const onTitleFocus = () => {
    setInvalidTitle(title.length === 0);
  };

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
    setInvalidTitle(e.target.value.length === 0);
  };

  const onContentChange = (value: string) => {
    setContent(value);
  };

  const onAssigneeClick = useCallback((id: number) => {
    setAssignees((a) => {
      if (a.includes(id)) {
        return a.filter((a) => a !== id);
      }
      return [...a, id];
    });
  }, []);

  const onLabelClick = useCallback((id: number) => {
    setLabels((l) => {
      if (l.includes(id)) {
        return l.filter((l) => l !== id);
      }
      return [...l, id];
    });
  }, []);

  const onMilestoneClick = useCallback((id: number) => {
    setMilestone((m) => (m === id ? null : id));
  }, []);

  const onSubmitButtonClick = async () => {
    const issueData = {
      title: title,
      content: content,
      assignees: assignees,
      labels: labels,
      milestone: milestone,
    };

    console.log("이슈 등록 데이터", issueData);
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
        onAssigneeClick={onAssigneeClick}
        onLabelClick={onLabelClick}
        onMilestoneClick={onMilestoneClick}
        onTitleChange={onTitleChange}
        onTitleFocus={onTitleFocus}
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
