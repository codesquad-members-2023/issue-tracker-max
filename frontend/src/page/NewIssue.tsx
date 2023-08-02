import { styled } from "styled-components";
import { Button } from "../components/Button";
import { Sidebar } from "../components/SideBar";
import { TextArea } from "../components/TextArea";
import { TextInput } from "../components/TextInput";

export function NewIssue() {
  const assigneeOptions = [
    {
      name: "담당자가 없는 이슈",
      selected: false,
      onClick: () => {
        console.log("담당자가 없는 이슈");
      },
    },
    {
      name: "jjinbbang52",
      selected: false,
      onClick: () => {
        console.log("jjinbbang52");
      },
    },
    {
      name: "htmlH3AD",
      selected: false,
      onClick: () => {
        console.log("htmlH3AD");
      },
    },
    {
      name: "samsamis9",
      selected: false,
      onClick: () => {
        console.log("samsamis9");
      },
    },
    {
      name: "dev_angel0",
      selected: false,
      onClick: () => {
        console.log("dev_angel0");
      },
    },
  ];

  const labelOptions = [
    {
      name: "레이블이 없는 이슈",
      selected: false,
      onClick: () => {
        console.log("레이블이 없는 이슈");
      },
    },
    {
      name: "documentation",
      background: "#0025E6",
      selected: false,
      onClick: () => {
        console.log("documentation");
      },
    },
    {
      name: "bug",
      background: "#FF3B30",
      selected: false,
      onClick: () => {
        console.log("bug");
      },
    },
  ];

  const milestoneOptions = [
    {
      name: "마일스톤이 없는 이슈",
      selected: false,
      onClick: () => {
        console.log("마일스톤이 없는 이슈");
      },
    },
    {
      name: "Sprint#1",
      selected: false,
      onClick: () => {
        console.log("Sprint#1");
      },
    },
  ];

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
          <TextInput size="L" placeholder="제목" label="제목" />
          <TextArea placeholder="코멘트" label="코멘트" />
        </NewIssueContent>
        <Sidebar
          assigneeOptions={assigneeOptions}
          labelOptions={labelOptions}
          milestoneOptions={milestoneOptions}
        />
      </NewIssueBody>
      <Line />
      <NewIssueFooter>
        <Button size="M" buttonType="Ghost" icon="xSquare">
          작성 취소
        </Button>
        <Button size="L" buttonType="Container" disabled>
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
  align-items: flex-start;
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
    flex-grow: 1;
  }
`;

const Line = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  margin: 0;
`;
