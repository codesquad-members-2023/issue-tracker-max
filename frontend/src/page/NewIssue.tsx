import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../components/Button";
import { TextArea } from "../components/TextArea";
import { TextInput } from "../components/TextInput";
import { Sidebar, SidebarOptionData } from "../components/sidebar/Sidebar";

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

type OptionData = AssigneeData | LabelData | MilestoneData;

export function NewIssue() {
  const [assignees, setAssignees] = useState<AssigneeData[]>([]);
  const [labels, setLabels] = useState<LabelData[]>([]);
  const [milestones, setMilestones] = useState<MilestoneData[]>([]);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchOptions();
  }, []);

  const fetchOptions = async () => {
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
      dataList[1].labels.map((label: LabelData) => ({ ...label, selected: false })),
    );
    setMilestones(
      dataList[2].milestones.map((milestone: MilestoneData) => ({
        ...milestone,
        selected: false,
      })) ?? [],
    );
  };

  const invalidTitle = title.trim().length === 0;
  const titleCaption = invalidTitle
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
          id: option.id,
          name: option.loginId,
          profile: option.avatarUrl,
          selected: option.selected,
          onClick: () => {
            setAssignees((a) => toggleOptionSelection(a, option.id));
          },
        };
      } else if ("background" in option) {
        return {
          id: option.id,
          name: option.name,
          background: option.background,
          selected: option.selected,
          onClick: () => {
            setLabels((a) => toggleOptionSelection(a, option.id));
          },
        };
      } else if ("issues" in option) {
        return {
          id: option.id,
          name: option.name,
          selected: option.selected,
          issues: option.issues,
          onClick: () => {
            setMilestones((a) => switchOptionSelection(a, option.id));
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
            isError={invalidTitle}
            caption={titleCaption}
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
          assigneeOptions={createOptionsForSideBar(assignees)}
          labelOptions={createOptionsForSideBar(labels)}
          milestoneOptions={createOptionsForSideBar(milestones)}
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
          disabled={invalidTitle}
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

  & > div:first-of-type {
    width: 100%;
    align-self: stretch;
    flex-shrink: 0;

    & > div,
    & input {
      width: 100%;
    }
  }

  & > div:last-of-type {
    align-self: stretch;
  }
`;

const Line = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  margin: 0;
`;
