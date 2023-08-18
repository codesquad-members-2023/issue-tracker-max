import { styled } from "styled-components";
import { useNavigate } from "react-router-dom";
import TextArea from "../components/common/TextArea/TextArea";
import { useState } from "react";
import LabelInput from "../components/common/TextInput/LabelInput";
// import SideBar from "../components/SideBar/SideBar";
import Button from "../components/common/Button/Button";
import SideBar from "../components/SideBar/SideBar";
import { AssigneesList, Label, Milestone } from "../type";
import Header from "../components/Header/Header";
import { useAuth } from "../context/AuthContext";

type Props = {
  toggleTheme(): void;
};

export default function AddIssuePage({ toggleTheme }: Props) {
  const { profile } = useAuth();
  const [issueTitle, setIssueTitle] = useState<string>("");
  const [issueContent, setIssueContent] = useState<string>("");
  const [selectedAssignees, setSelectedAssignees] = useState<AssigneesList[]>(
    [],
  );
  const [selectedLabels, setSelectedLabels] = useState<Label[]>([]);
  const [selectedMilestone, setSelectedMilestone] = useState<Milestone>();
  const navigate = useNavigate();

  const handleAssignee = (assignee: AssigneesList, assigneeListId: number) => {
    if (
      selectedAssignees.length === 0 ||
      selectedAssignees.some((assignee) => assignee.id !== assigneeListId)
    ) {
      setSelectedAssignees((prevList) => [...prevList, assignee]);
    }
    if (selectedAssignees.some((assignee) => assignee.id === assigneeListId)) {
      setSelectedAssignees(
        selectedAssignees.filter((assignee) => assignee.id !== assigneeListId),
      );
    }
  };

  const handleLabel = (label: Label, labelListId: number) => {
    if (
      selectedLabels.length === 0 ||
      selectedLabels.some((label) => label.id !== labelListId)
    ) {
      setSelectedLabels((prevList) => [...prevList, label]);
    }
    if (selectedLabels.some((assignee) => assignee.id === labelListId)) {
      setSelectedLabels(
        selectedLabels.filter((label) => label.id !== labelListId),
      );
    }
  };

  const handleMilestone = (milestone: Milestone, milestoneListId: number) => {
    if (
      selectedMilestone === undefined ||
      selectedMilestone!.id !== milestoneListId
    )
      setSelectedMilestone(milestone);
    if (selectedMilestone!.id === milestoneListId)
      setSelectedMilestone(undefined);
  };

  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };

  const handleTitleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIssueTitle(e.target.value);
  };

  const handleContentInputChange = (
    e: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setIssueContent(e.target.value);
  };

  const createIssue = async () => {
    const url = "http://3.34.141.196/api/issues";

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const data = {
      title: issueTitle,
      content: issueContent,
      assigneeIds:
        selectedAssignees.length === 0
          ? null
          : selectedAssignees.map((assignee) => assignee.id),
      labelIds:
        selectedLabels.length === 0
          ? null
          : selectedLabels.map((label) => label.id),
      milestoneId: selectedMilestone ? selectedMilestone.id : null,
    };

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        const responseData = await response.json();
        navigate(`/detail/${responseData.id}`);
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  return (
    <>
      <Header
        toggleTheme={toggleTheme}
        profileImg={profile ? profile.profileImageUri : "/icons/user.png"}
      />
      <Page>
        <Main>
          <Title>새로운 이슈 작성</Title>
          <ContentsContainer>
            <UserProfileImg src={"/logo/profile.jpg"} />
            <AddIssueForm>
              <LabelInput
                id={"newTitle"}
                label={"제목"}
                placeholder={"제목을 입력해주세요"}
                value={issueTitle}
                onChange={handleTitleInputChange}
              />
              <TextArea
                inputValue={issueContent}
                onChange={handleContentInputChange}
              />
            </AddIssueForm>
            <SideBar
              assignees={selectedAssignees}
              labels={selectedLabels}
              milestone={selectedMilestone}
              handleAssignee={handleAssignee}
              handleLabel={handleLabel}
              handleMilestone={handleMilestone}
            />
          </ContentsContainer>
          <ButtonTap>
            <Button
              icon={"xSquare"}
              label={"작성 취소"}
              type={"ghost"}
              size={"medium"}
              onClick={goMainPage}
            />
            <Button
              label={"완료"}
              type={"container"}
              size={"large"}
              onClick={createIssue}
              disabled={!issueTitle}
            />
          </ButtonTap>
        </Main>
      </Page>
    </>
  );
}

const Page = styled.div`
  margin-bottom: 50px;
  display: flex;
  gap: 32px;
  flex-direction: column;
  align-items: center;
  min-width: 100vw;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Main = styled.main`
  width: 1280px;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.h1`
  font: ${({ theme }) => theme.font.displayBold32};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;

const ContentsContainer = styled.div`
  position: relative;
  display: flex;
  gap: 24px;
  width: 100%;
  padding: 24px 0px;
  &::before {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    width: 1280px;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
  &::after {
    content: "";
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 1280px;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const AddIssueForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 912px;
`;

const ButtonTap = styled.div`
  display: flex;
  gap: 32px;
  justify-content: end;
`;

const UserProfileImg = styled.img`
  width: 32px;
  height: 32px;
  border-radius: ${({ theme }) => theme.radius.half};
`;
