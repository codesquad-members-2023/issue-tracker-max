import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../components/Button";
import { TextArea } from "../components/TextArea";
import { TextInput } from "../components/TextInput";
import { Sidebar, SidebarOptionData } from "../components/sidebar/Sidebar";

type AssigneeData = {
  id: number;
  name: string;
  avatarUrl: string;
  selected: boolean;
};

type LabelData = {
  id: number;
  name: string;
  background: string;
  color?: string;
  selected: boolean;
};

type MilestoneData = {
  id: number;
  name: string;
  status: "OPEN" | "CLOSED";
  description: string;
  issues: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
  selected: boolean;
};

type OptionData = AssigneeData | LabelData | MilestoneData;

export function NewIssue() {
  const initialAssigneeOptions = [
    {
      id: 1,
      name: "jjinbbang52",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 2,
      name: "htmlH3AD",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 3,
      name: "samsamis9",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 4,
      name: "dev_angel0",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 5,
      name: "jjinbbang52",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 6,
      name: "htmlH3AD",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 7,
      name: "samsamis9",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 8,
      name: "dev_angel0",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 9,
      name: "jjinbbang52",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 10,
      name: "htmlH3AD",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 11,
      name: "samsamis9",
      avatarUrl: "",
      selected: false,
    },
    {
      id: 12,
      name: "dev_angel0",
      avatarUrl: "",
      selected: false,
    },
  ];

  const initialLabelOptions = [
    {
      id: 1,
      name: "documentation",
      background: "#0025E6",
      selected: false,
    },
    {
      id: 2,
      name: "bug",
      background: "#FF3B30",
      selected: false,
    },
    {
      id: 3,
      name: "feature",
      background: "#F9D0C3",
      selected: false,
    },
    {
      id: 4,
      name: "design",
      background: "#E99695",
      selected: false,
    },
    {
      id: 5,
      name: "test",
      background: "#91C147",
      selected: false,
    },
    {
      id: 6,
      name: "chore",
      background: "#6FBDAB",
      selected: false,
    },
  ];

  const initialMilestoneOptions = [
    {
      id: 1,
      name: "Sprint#1",
      status: "OPEN" as "OPEN" | "CLOSED",
      description: "스프린트 #1",
      issues: {
        openedIssueCount: 2,
        closedIssueCount: 10,
      },
      selected: false,
    },
    {
      id: 2,
      name: "Sprint#2",
      status: "OPEN" as "OPEN" | "CLOSED",
      description: "스프린트 #2",
      issues: {
        openedIssueCount: 2,
        closedIssueCount: 4,
      },
      selected: false,
    },
    {
      id: 3,
      name: "Sprint#3",
      status: "OPEN" as "OPEN" | "CLOSED",
      description: "스프린트 #3",
      issues: {
        openedIssueCount: 5,
        closedIssueCount: 0,
      },
      selected: false,
    },
  ];

  const [assigneeOptions, setAssigneeOptions] = useState<AssigneeData[]>(
    initialAssigneeOptions,
  );
  const [labelOptions, setLabelOptions] =
    useState<LabelData[]>(initialLabelOptions);
  const [milestoneOptions, setMilestoneOptions] = useState<MilestoneData[]>(
    initialMilestoneOptions,
  );
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navigate = useNavigate();

  const invalidToSubmit = title.trim().length === 0;
  const titleCaption =
    title.trim().length === 0
      ? "제목은 1글자 이상 50글자 이하로 작성해주세요."
      : "";

  const toggleOptionSelection = <T extends OptionData>(
    options: T[],
    id: number,
  ) =>
    options.map((option) => {
      return {
        ...option,
        selected: option.id === id ? !option.selected : option.selected,
      };
    }) as T[];

  const switchOptionSelection = <T extends OptionData>(
    options: T[],
    id: number,
  ) => {
    return options.map((option) => {
      return {
        ...option,
        selected: option.id === id ? true : false,
      };
    }) as T[];
  };

  const createOptionsForSideBar = <T extends OptionData>(options: T[]) => {
    return options.map((option) => {
      if ("avatarUrl" in option) {
        return {
          name: option.name,
          profile: option.avatarUrl,
          selected: option.selected,
          onClick: () => {
            setAssigneeOptions((a) => toggleOptionSelection(a, option.id));
          },
        };
      } else if ("background" in option) {
        return {
          name: option.name,
          background: option.background,
          selected: option.selected,
          onClick: () => {
            setLabelOptions((a) => toggleOptionSelection(a, option.id));
          },
        };
      } else if ("issues" in option) {
        return {
          name: option.name,
          selected: option.selected,
          issues: option.issues,
          onClick: () => {
            setMilestoneOptions((a) => switchOptionSelection(a, option.id));
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
      assignees: assigneeOptions
        .filter((option) => option.selected)
        .map((option) => option.id),
      labels: labelOptions
        .filter((option) => option.selected)
        .map((option) => option.id),
      milestone: milestoneOptions.find((option) => option.selected)?.id ?? null,
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
      <NewIssueBody>
        <img
          style={{ width: "32px" }}
          src="https://avatars.githubusercontent.com/u/41321198?v=4"
        />
        <NewIssueContent>
          <TextInput
            size="L"
            placeholder="제목"
            label="제목"
            value={title}
            maxLength={50}
            caption={titleCaption}
            isError={title.trim().length === 0}
            onChange={onTitleChange}
          />
          <TextArea
            placeholder="코멘트"
            label="코멘트"
            value={content}
            maxLength={10000}
            onChange={onContentChange}
          />
        </NewIssueContent>
        <Sidebar
          assigneeOptions={createOptionsForSideBar(assigneeOptions)}
          labelOptions={createOptionsForSideBar(labelOptions)}
          milestoneOptions={createOptionsForSideBar(milestoneOptions)}
        />
      </NewIssueBody>
      <Line />
      <NewIssueFooter>
        <Button
          size="M"
          buttonType="Ghost"
          icon="xSquare"
          onClick={onCancelButtonClick}
        >
          작성 취소
        </Button>
        <Button
          size="L"
          buttonType="Container"
          onClick={onSubmitButtonClick}
          disabled={invalidToSubmit}
        >
          완료
        </Button>
      </NewIssueFooter>
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

const NewIssueBody = styled.div`
  display: flex;
  align-items: flex-start;
  gap: 24px;
  flex: 1 0 0;
  align-self: stretch;
`;

const NewIssueFooter = styled.div`
  align-self: flex-end;
  display: flex;
  align-items: center;
  gap: 32px;
`;

const NewIssueContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex: 1 0 0;
  align-self: stretch;

  & > div:first-child {
    width: 100%;
    align-self: stretch;
    flex-shrink: 0;

    & > div,
    & input {
      width: 100%;
    }
  }

  & > div:last-child {
    align-self: stretch;
  }
`;

const Line = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  margin: 0;
`;
