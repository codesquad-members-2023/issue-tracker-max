import { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import { Button } from "components/Common/Button/Button";
import { TextInput } from "components/Common/Input/TextInput";
import { useThemeMode } from "contexts/ThemeModeContext";
import { useFetch } from "../hook/useApiRequest";
import logoLight from "assets/img/logo_large.svg";
import logoDark from "assets/img/logo_large-dark.svg";
import { useAuth } from "../contexts/AuthContext";
/* 회원가입 버튼 누른 후 처리 추가하기 */
/* 비밀번호 처리 관련 */

interface ApiResponse {
  jwtResponse: {
    accessToken: string;
  };
  profile: string;
}

interface ErrorResponse {
  data: {
    statusCode: number;
    data?: string;
  };
}

export const LoginPage = () => {
  const navigate = useNavigate();
  const { mode } = useThemeMode();
  const { login } = useAuth();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [userIdError, setUserIdError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [loginErrorMessage, setLoginErrorMessage] = useState("");

  const emailRegex =
    /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
  const passwordRegex = /^[A-Za-z0-9!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?~]{6,12}$/;

  const validateEmail = (email: string): boolean => emailRegex.test(email);
  const validatePassword = (password: string): boolean =>
    passwordRegex.test(password);

  const handleUserIdChange = (value: string): void => {
    setUserId(value);
    if (!validateEmail(value)) {
      setUserIdError("아이디는 이메일 형식이어야 합니다.");
    } else {
      setUserIdError("");
    }
  };

  const handlePasswordChange = (value: string): void => {
    setPassword(value);
    if (!validatePassword(value)) {
      setPasswordError("비밀번호는 최소 6자리에서 12자리여야 합니다.");
    } else {
      setPasswordError("");
    }
  };

  const { makeRequest, error } = useFetch<ApiResponse>();

  const handleLogin = async () => {
    const LOGIN_API_URL = "http://43.200.169.143:8080/api/login";
    const requestBody = {
      email: userId,
      password,
    };

    try {
      const data = await makeRequest(LOGIN_API_URL, "POST", requestBody);
      if (data) {
        const accessToken = data.jwtResponse.accessToken;
        const user = { profile: data.profile };
        login(accessToken, user);
        navigate("/");
      }
    } catch (error) {
      const errorData = error as ErrorResponse;

      if (errorData && errorData.data && errorData.data.statusCode === 401) {
        setLoginErrorMessage(errorData.data.data || "로그인에 실패하셨습니다");
      } else {
        setLoginErrorMessage("로그인에 실패하셨습니다");
      }
    }
  };

  const handleGithubLogin = async () => {
    // const GITHUB_LOGIN_API_URL = "http://43.200.169.143:8080/api/login";
  };

  return (
    <Layout>
      <LogoBox>
        <img
          src={mode === "light" ? logoLight : logoDark}
          alt="Issue Tracker 로고"
        />
      </LogoBox>

      <FormBox>
        <LButton size="L" variant="outline" onClick={handleGithubLogin}>
          <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ca67429ee7aec3b&scope=id,name,email,avatar_url">
            GitHub 계정으로 로그인
          </a>
          {/* <a href="https://github.com/login/oauth/authorize?client_id=Iv1.9ca67429ee7aec3b&scope=id,name,email,avatar_url">
              GitHub 계정으로 로그인
            </a> */}
        </LButton>
        <span>or</span>
        <TextInput
          $labelText="아이디"
          value={userId}
          onValueChange={handleUserIdChange}
          helperText={userIdError}
          $state={userIdError ? "error" : "enabled"}
        />
        <TextInput
          $labelText="비밀번호"
          value={password}
          onValueChange={handlePasswordChange}
          helperText={passwordError}
          $state={passwordError ? "error" : "enabled"}
          type="password"
        />
        <LButton
          size="L"
          variant="contained"
          disabled={Boolean(
            userIdError || passwordError || !userId || !password,
          )}
          onClick={handleLogin}
        >
          아이디로 로그인
        </LButton>
        <LoginErrorBox style={{ color: "red" }}>
          {error && loginErrorMessage}
        </LoginErrorBox>
        <Button size="M" variant="ghost" onClick={() => navigate("/signup")}>
          회원가입
        </Button>
      </FormBox>
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

const LoginErrorBox = styled.p`
  font: ${({ theme: { font } }) => font.displayD12};
  color: ${({ theme: { color } }) => color.dangerTextDefault};
`;

const LButton = styled(Button)`
  width: 320px;
`;
