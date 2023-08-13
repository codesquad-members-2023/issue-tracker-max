import { styled } from "styled-components";
import UserProfileButton from "../components/UserProfileButton/UserProfileButton";
import { useNavigate } from "react-router-dom";
import TextArea from "../components/common/TextArea/TextArea";
import { useState } from "react";
import LabelInput from "../components/common/TextInput/LabelInput";
import SideBar from "../components/SideBar/SideBar";
import Button from "../components/common/Button/Button";

export default function AddIssuePage() {
  const [issueTitle, setIssueTitle] = useState<string>("");
  const [issueContent, setIssueContent] = useState<string>("");
  const [assignees, setAssignees] = useState([1]);
  const [labels, setLabels] = useState([4]);
  const [milestone, setMilestone] = useState(null);
  const navigate = useNavigate();
  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };
  setAssignees;
  setLabels;
  setMilestone;
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

    const data = {
      title: issueTitle,
      content: issueContent,
      assigneeIds: assignees,
      labelIds: labels,
      milestoneId: milestone,
    };

    const headers = {
      "Content-Type": "application/json",
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
    <Page>
      <Main>
        <Title>새로운 이슈 작성</Title>
        <ContentsContainer>
          <UserProfileButton src={"/logo/profile.jpg"} onClick={() => {}} />
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
          <SideBar></SideBar>
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
