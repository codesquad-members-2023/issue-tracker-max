import { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import { Button } from "components/Common/Button/Button";
import { TextInput } from "components/Common/Input/TextInput";
import { useThemeMode } from "contexts/ThemeModeContext";
import { useFetch } from "../hook/useApiRequest";
import logoLight from "assets/img/logo_large.svg";
import logoDark from "assets/img/logo_large-dark.svg";

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

export const SignUpPage = () => {
  const navigate = useNavigate();
  const { mode } = useThemeMode();
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [userIdError, setUserIdError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [signUpErrorMessage, setSignUpErrorMessage] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  // const { login } = useAuth();

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

  const handleConfirmPasswordChange = (value: string): void => {
    setConfirmPassword(value);
  };

  const isPasswordConfirmed = () => password === confirmPassword;

  const { makeRequest, error } = useFetch<ApiResponse>();

  const handleSignUp = async () => {
    const SIGNUP_API_URL = "http://43.200.169.143:8080/api/signup";
    const requestBody = {
      email: userId,
      password,
      profile: `https://source.boringavatars.com/beam/20/${userId}`,
    };

    try {
      await makeRequest(SIGNUP_API_URL, "POST", requestBody);
      navigate("/login");
      setSignUpErrorMessage("");
    } catch (error) {
      const errorData = error as ErrorResponse;

      if (errorData && errorData.data) {
        setSignUpErrorMessage(
          errorData.data.data || "회원가입에 실패하셨습니다",
        );
      } else {
        setSignUpErrorMessage("회원가입에 실패하셨습니다");
      }
    }
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
        <TextInput
          $labelText="비밀번호 확인"
          value={confirmPassword}
          onValueChange={handleConfirmPasswordChange}
          type="password"
        />
        <LButton
          size="L"
          variant="contained"
          disabled={
            Boolean(userIdError || passwordError || !userId || !password) ||
            !isPasswordConfirmed()
          }
          onClick={handleSignUp}
        >
          회원가입
        </LButton>
        <LoginErrorBox style={{ color: "red" }}>
          {error && signUpErrorMessage}
        </LoginErrorBox>
        <Button size="M" variant="ghost" onClick={() => navigate("/login")}>
          로그인
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
