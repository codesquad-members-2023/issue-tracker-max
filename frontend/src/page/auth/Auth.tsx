import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TextInput } from "../../components/TextInput";
import { Icon } from "../../components/icon/Icon";
import { setAccessToken, setUserInfo } from "../../utils/localStorage";
import { SignUpSuccessModal } from "./SignUpSuccessModal";

type InputChangeEvent = React.ChangeEvent<HTMLInputElement>;

export type UserInfoData = {
  id: number;
  loginId: string;
  email: string;
  avatarUrl: string | null;
};

export function Auth() {
  const navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");
  const [isSignUp, setIsSignUp] = useState(false);
  const [loginId, setLoginId] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [email, setEmail] = useState("");

  const [isOpenModal, setIsOpenModal] = useState(false);

  const [isValidId, setIsValidId] = useState(false);
  const [isValidPassword, setIsValidPassword] = useState(false);
  const [isValidEmail, setIsValidEmail] = useState(false);
  const isPasswordMatch = isValidPassword && password === passwordConfirm;

  const isValidLogin = isValidId && isValidPassword;
  const isValidSingUp = isValidId && isPasswordMatch && isValidEmail;

  const isButtonDisabled = isSignUp ? isValidSingUp : isValidLogin;

  const onChangeId = (event: InputChangeEvent) => {
    const value = event.target.value;
    const isValid = /^[A-Za-z0-9]{5,20}$/.test(value);

    setErrorMessage("");
    setLoginId(value);
    setIsValidId(isValid);
  };

  const onChangePassword = (event: InputChangeEvent) => {
    const value = event.target.value;
    const isValid = /^[A-Za-z0-9]{8,16}$/.test(value);

    setErrorMessage("");
    setPassword(value);
    setIsValidPassword(isValid);
  };

  const onChangePasswordCheck = (event: InputChangeEvent) => {
    setErrorMessage("");
    setPasswordConfirm(event.target.value);
  };

  const onChangeEmail = (event: InputChangeEvent) => {
    const value = event.target.value;

    setErrorMessage("");
    setEmail(value);
    setIsValidEmail(validateEmail(value));
  };

  const validateEmail = (email: string) => {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(String(email).toLowerCase());
  };

  const resetInput = () => {
    setLoginId("");
    setPassword("");
    setPasswordConfirm("");
    setEmail("");
    setIsOpenModal(false);
  };

  const togglesIsSignUp = () => {
    resetInput();
    setIsSignUp(!isSignUp);
  };

  const submit = () => {
    if (isSignUp) {
      signUp();
    } else {
      login();
    }
  };

  const checkInputValid = (value: string, isValid: boolean) => {
    return !(value === "") && !isValid;
  };

  const signUp = async () => {
    const res = await fetch("/api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        loginId: loginId,
        email: email,
        password: password,
        passwordConfirm: passwordConfirm,
      }),
    });

    const { code, message } = await res.json();
    if (code === 201) {
      setIsOpenModal(true);
    } else {
      setErrorMessage(message);
    }
  };

  const login = async () => {
    const res = await fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        loginId: loginId,
        password: password,
      }),
    });

    const { code, message, data } = await res.json();
    if (code === 200) {
      setAccessToken(data.jwt.accessToken);
      setUserInfo(data.user as UserInfoData);

      navigate("/");
    } else {
      setErrorMessage(message);
    }
  };

  const oauthLogin = () => {
    const loginURL =
      "https://github.com/login/oauth/authorize?client_id=3fe3bba8b880698babf0&scope=login";

    window.location.assign(loginURL);
  };

  return (
    <Div>
      <AuthWrapper>
        <Anchor onClick={() => navigate("/auth")}>
          <Icon name="LogoLarge" color="neutralTextStrong" />
        </Anchor>
        <AuthPanel>
          <Button
            size="L"
            buttonType="Outline"
            flexible="Flexible"
            onClick={oauthLogin}
          >
            <ButtonText>GitHub 계정으로 로그인</ButtonText>
          </Button>
          <div>or</div>
          <TextInput
            width={320}
            size="L"
            label="아이디"
            placeholder="아이디"
            value={loginId}
            isError={checkInputValid(loginId, isValidId)}
            caption={
              checkInputValid(loginId, isValidId)
                ? "5자리에서 20자리 까지 입력해주세요"
                : ""
            }
            onChange={onChangeId}
          />
          <TextInput
            width={320}
            size="L"
            label="비밀번호"
            placeholder={"비밀번호"}
            value={password}
            type="password"
            isError={checkInputValid(password, isValidPassword)}
            caption={
              checkInputValid(password, isValidPassword)
                ? "8자리에서 16자리 까지 입력해주세요"
                : ""
            }
            onChange={onChangePassword}
          />
          {isSignUp && (
            <>
              <TextInput
                width={320}
                size="L"
                label="비밀번호 확인"
                placeholder={"비밀번호 확인"}
                value={passwordConfirm}
                type="password"
                isError={checkInputValid(passwordConfirm, isPasswordMatch)}
                caption={
                  checkInputValid(passwordConfirm, isPasswordMatch)
                    ? "비밀번호를 확인해 주세요"
                    : ""
                }
                onChange={onChangePasswordCheck}
              />
              <TextInput
                width={320}
                size="L"
                label="이메일"
                placeholder="이메일"
                value={email}
                isError={checkInputValid(email, isValidEmail)}
                caption={
                  checkInputValid(email, isValidEmail)
                    ? "이메일 형식에 맞게 입력해 주세요"
                    : ""
                }
                onChange={onChangeEmail}
              />
            </>
          )}
          {errorMessage !== "" && (
            <ErrorMessage>
              {isSignUp ? "회원가입" : "로그인"} 실패 : {errorMessage}
            </ErrorMessage>
          )}

          <Button
            size="L"
            buttonType="Container"
            flexible="Flexible"
            disabled={!isButtonDisabled}
            onClick={submit}
          >
            <ButtonText>{isSignUp ? "회원가입" : "아이디로 로그인"}</ButtonText>
          </Button>
          <Button size="S" buttonType="Ghost" onClick={togglesIsSignUp}>
            {isSignUp ? "로그인" : "회원가입"}
          </Button>
        </AuthPanel>
        {isOpenModal && <SignUpSuccessModal onClick={togglesIsSignUp} />}
      </AuthWrapper>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AuthWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 64px;
`;

const Anchor = styled.a`
  cursor: pointer;
`;

const AuthPanel = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
`;

const ButtonText = styled.div`
  width: 254px;
`;

const ErrorMessage = styled.span`
  color: ${({ theme }) => theme.color.dangerTextDefault};
  font: ${({ theme }) => theme.font.displayBold16};
`;
