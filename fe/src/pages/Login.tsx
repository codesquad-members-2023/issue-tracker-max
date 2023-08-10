import { useState } from "react";
import styled from "styled-components";

import { Button } from "components/Common/Button/Button";
import { TextInput } from "components/Common/Input/TextInput";
import { useThemeMode } from "contexts/ThemeModeContext";
import logoLight from "assets/img/logo_large.svg";
import logoDark from "assets/img/logo_large-dark.svg";

/* 회원가입 버튼 누른 후 처리 추가하기 */
/* 아이디 중복처리 관련 */
/* 비밀번호 처리 관련 */
export const LoginPage = () => {
  const { mode } = useThemeMode();

  const [formMode, setFormMode] = useState("login");

  const toggleFormMode = () => {
    setFormMode((prevMode) => (prevMode === "login" ? "signup" : "login"));
  };

  return (
    <Layout>
      <LogoBox>
        <img
          src={mode === "light" ? logoLight : logoDark}
          alt="Issue Tracker 로고"
        />
      </LogoBox>

      {formMode === "login" ? (
        <FormBox>
          <LButton size="L" variant="outline">
            GitHub 계정으로 로그인
          </LButton>
          <span>or</span>
          <TextInput $labelText="아이디" />
          <TextInput $labelText="비밀번호" />
          <LButton size="L" variant="contained" disabled>
            아이디로 로그인
          </LButton>
          <Button size="M" variant="ghost" onClick={toggleFormMode}>
            회원가입
          </Button>
        </FormBox>
      ) : (
        <FormBox>
          <TextInput $labelText="아이디" />
          <TextInput $labelText="이메일" />
          <TextInput $labelText="비밀번호" />
          <TextInput $labelText="비밀번호 확인" />
          <LButton size="L" variant="contained" disabled>
            회원가입
          </LButton>
          <Button size="M" variant="ghost" onClick={toggleFormMode}>
            로그인
          </Button>
        </FormBox>
      )}
    </Layout>
  );
};

const Layout = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const LogoBox = styled.div`
  padding-bottom: 64px;
`;

const FormBox = styled.div`
  margin: 0 auto;
  width: 320px;
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 16px;
`;

const LButton = styled(Button)`
  width: 320px;
`;
