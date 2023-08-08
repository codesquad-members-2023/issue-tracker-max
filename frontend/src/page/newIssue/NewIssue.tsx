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
  const navigate = useNavigate();

  const invalidTitle = title.trim().length === 0;

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
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

    try {
      const response = await fetch(
        "https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io/api/issues",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(issueData),
        },
      );
      const data = await response.json();
      if (!data.success) {
        throw new Error(data.message);
      }
    } catch (error) {
      throw new Error(
        `${error} : 이슈를 등록하는 과정에서 오류가 발생했습니다.`,
      );
    } finally {
      navigate("/");
    }
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
        onAssigneeClick={onAssigneeClick}
        onLabelClick={onLabelClick}
        onMilestoneClick={onMilestoneClick}
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
