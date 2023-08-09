import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { SidebarOptionData } from "../../components/sidebar/Sidebar";
import { NewIssueBody } from "./NewIssueBody";
import { NewIssueFooter } from "./NewIssueFooter";

type AssigneeData = {
  id: number;
  loginId: string;
  avatarUrl: string;
  selected: boolean;
};

type LabelData = {
  id: number;
  name: string;
  background: string;
  color?: string;
  description: string | null;
  selected: boolean;
};

type MilestoneData = {
  id: number;
  name: string;
  status: "OPENED" | "CLOSED";
  description: string;
  issues: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
  selected: boolean;
};

export type OptionData = AssigneeData | LabelData | MilestoneData;

export function NewIssue() {
  const [assignees, setAssignees] = useState<AssigneeData[]>([]);
  const [labels, setLabels] = useState<LabelData[]>([]);
  const [milestones, setMilestones] = useState<MilestoneData[]>([]);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchSidebarOptions();
  }, []);

  const fetchSidebarOptions = async () => {
    const url = "https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io";

    const responses = await Promise.all([
      fetch(`${url}/api/assignees`),
      fetch(`${url}/api/labels`),
      fetch(`${url}/api/milestones`),
    ]);
    const dataList = await Promise.all(
      responses.map((response) => response.json()),
    );

    setAssignees(
      dataList[0].assignees.map((assignee: AssigneeData) => ({
        ...assignee,
        selected: false,
      })),
    );
    setLabels(
      dataList[1].labels.map((label: LabelData) => ({
        ...label,
        selected: false,
      })),
    );
    setMilestones(
      dataList[2].milestones.map((milestone: MilestoneData) => ({
        ...milestone,
        selected: false,
      })) ?? [],
    );
  };

  const invalidTitle = title.trim().length === 0;

  const changeOptionSelection = <T extends OptionData>(
    options: T[],
    id: number,
    multiple: boolean,
  ) =>
    options.map((option) => ({
      ...option,
      selected: multiple
        ? option.id === id
          ? !option.selected
          : option.selected
        : option.id === id,
    }));

  const createOptionsForSideBar = <T extends OptionData>(options: T[]) => {
    return options.map((option) => {
      if ("avatarUrl" in option) {
        return {
          id: option.id,
          name: option.loginId,
          profile: option.avatarUrl,
          selected: option.selected,
          onClick: () => {
            setAssignees((a) => changeOptionSelection(a, option.id, true));
          },
        };
      } else if ("background" in option) {
        return {
          id: option.id,
          name: option.name,
          background: option.background,
          color: option.color,
          selected: option.selected,
          onClick: () => {
            setLabels((a) => changeOptionSelection(a, option.id, true));
          },
        };
      } else if ("issues" in option) {
        return {
          id: option.id,
          name: option.name,
          selected: option.selected,
          issues: option.issues,
          onClick: () => {
            setMilestones((a) => changeOptionSelection(a, option.id, false));
          },
        };
      }
    }) as SidebarOptionData[];
  };

  const onTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const onSubmitButtonClick = async () => {
    const issueData = {
      title: title,
      content: content,
      assignees: assignees
        .filter((option) => option.selected)
        .map((option) => option.id),
      labels: labels
        .filter((option) => option.selected)
        .map((option) => option.id),
      milestone: milestones?.find((option) => option.selected)?.id ?? null,
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
        assignees={createOptionsForSideBar(assignees)}
        labels={createOptionsForSideBar(labels)}
        milestones={createOptionsForSideBar(milestones)}
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
