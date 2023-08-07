import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../components/Button";
import { TextInput } from "../components/TextInput";
import { Icon } from "../components/icon/Icon";

type InputChangeEvent = React.ChangeEvent<HTMLInputElement>;

export function Auth() {
  const navigate = useNavigate();

  const [isSignUp, setIsSignUp] = useState(false);
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");
  const [email, setEmail] = useState("");

  const [isValidId, setIsValidId] = useState(false);
  const [isValidPassword, setIsValidPassword] = useState(false);
  const [isValidEmail, setIsValidEmail] = useState(false);
  const isPasswordMatch = isValidPassword && password === passwordCheck;

  const isValidLogin = isValidId && isValidPassword;
  const isValidSingUp = isValidId && isPasswordMatch && isValidEmail;

  const isButtonDisabled = isSignUp ? isValidSingUp : isValidLogin;

  const onChangeId = (event: InputChangeEvent) => {
    const value = event.target.value;
    setId(value);
    setIsValidId(value.length >= 6 && value.length <= 16);
  };

  const onChangePassword = (event: InputChangeEvent) => {
    const value = event.target.value;
    setPassword(value);
    setIsValidPassword(value.length >= 6 && value.length <= 12);
  };

  const onChangePasswordCheck = (event: InputChangeEvent) => {
    setPasswordCheck(event.target.value);
  };

  const onChangeEmail = (event: InputChangeEvent) => {
    const value = event.target.value;
    setEmail(value);
    setIsValidEmail(validateEmail(value));
  };

  const validateEmail = (email: string) => {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(String(email).toLowerCase());
  };

  const resetInput = () => {
    setId("");
    setPassword("");
    setPasswordCheck("");
    setEmail("");
  };

  const togglesIsSignUp = () => {
    resetInput();
    setIsSignUp(!isSignUp);
  };

  const submit = () => {
    if (isSignUp) {
      console.log("회원가입", id, password, passwordCheck, email);
    } else {
      console.log("로그인", id, password);
    }
  };

  const checkInputValid = (value: string, isValid: boolean) => {
    return !(value === "") && !isValid;
  };

  return (
    <Div>
      <AuthWrapper>
        <Anchor onClick={() => navigate("/auth")}>
          <Icon name="LogoLarge" color="neutralTextStrong" />
        </Anchor>
        <AuthPanel>
          <Button size="L" buttonType="Outline" flexible="Flexible">
            <ButtonText>
              GitHub 계정으로 {isSignUp ? "회원가입" : "로그인"}
            </ButtonText>
          </Button>
          <div>or</div>
          <TextInput
            width={320}
            size="L"
            label="아이디"
            placeholder="아이디"
            value={id}
            isError={checkInputValid(id, isValidId)}
            caption={
              checkInputValid(id, isValidId)
                ? "6자리에서 16자리 까지 입력해주세요"
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
                ? "6자리에서 12자리 까지 입력해주세요"
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
                value={passwordCheck}
                type="password"
                isError={checkInputValid(passwordCheck, isPasswordMatch)}
                caption={
                  checkInputValid(passwordCheck, isPasswordMatch)
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
