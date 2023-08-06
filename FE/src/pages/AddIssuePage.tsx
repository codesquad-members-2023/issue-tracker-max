import { styled } from "styled-components";
import UserProfileButton from "../components/UserProfileButton/UserProfileButton";
import SideBar from "../components/SideBar/SideBar";
import Button from "../components/common/Button/Button";
import TextInput from "../components/common/TextInput/TextInput";
import { useNavigate } from "react-router-dom";

export default function AddIssuePage() {
  const navigate = useNavigate();
  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };

  return (
    <Page>
      <Main>
        <Title>새로운 이슈 작성</Title>
        <ContentsContainer>
          <UserProfileButton src={"/logo/profile.jpg"} onClick={() => {}} />
          <AddIssueForm>
            <TextInput id={"newIssueTitle"} label={"제목"} />
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
            onClick={() => {}}
          />
        </ButtonTap>
      </Main>
    </Page>
  );
}

const Page = styled.div`
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
  width: 912px;
`;

const ButtonTap = styled.div`
  display: flex;
  gap: 32px;
  justify-content: end;
`;
