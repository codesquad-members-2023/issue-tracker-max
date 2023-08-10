import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { TextInput } from "components/Common/Input/TextInput";
import { TextArea } from "components/Common/TextArea/TextArea";
import { Sidebar } from "components/Common/Sidebar/Sidebar";
import { Button } from "components/Common/Button/Button";
import { Icon } from "components/Common/Icon/Icon";

import UserTestProfile from "assets/img/profile_test.svg";

export const NewIssuePage = () => {
  const navigate = useNavigate();

  return (
    <Layout>
      <h2>새로운 이슈 작성</h2>

      <ContentBox>
        <img src={UserTestProfile} width={32} />
        <FormBox>
          <TextInput $labelText="제목" />
          <div style={{ height: "448px" }}>
            <NewIssueTextArea labelText="코멘트를 입력하세요" />
          </div>
        </FormBox>
        <Sidebar></Sidebar>
      </ContentBox>

      <ButtonsBox>
        <Button size="M" variant="ghost" onClick={() => navigate("/")}>
          <Icon icon="XSquare" size="M" />
          작성 취소
        </Button>
        <Button
          size="L"
          variant="contained"
          disabled
          onClick={() => navigate("/")}
        >
          완료
        </Button>
      </ButtonsBox>
    </Layout>
  );
};

const Layout = styled.div`
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  height: 818px;

  h2 {
    font: ${({ theme: { font } }) => font.displayB32};
    color: ${({ theme: { color } }) => color.nuetralTextStrong};
  }
`;

const ContentBox = styled.div`
  display: flex;
  gap: 24px;
  align-items: flex-start;
  padding-top: 24px;
  border-top: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
`;

const FormBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 912px;
`;
const NewIssueTextArea = styled(TextArea)`
  height: 100%;
`;
const ButtonsBox = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 32px;
  align-items: center;
  padding-top: 24px;
  border-top: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
`;
