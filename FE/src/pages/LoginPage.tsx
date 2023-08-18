import { keyframes, styled } from "styled-components";
import Button from "../components/common/Button/Button";
import TextInput from "../components/common/TextInput/TextInput";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { isValidEmail } from "../utils/isValidEmail";
import Alert from "../components/Alert/Alert";
import { useAuth } from "../context/AuthContext";
import { decodeBase64 } from "../utils/decodeBase64";

export default function LoginPage() {
  const [hasIdError, setHasIdError] = useState<boolean>(false);
  const [hasPasswordError, setHasPasswordError] = useState<boolean>(false);
  const [idInputValue, setIdInputValue] = useState<string>("");
  const [passwordInputValue, setPasswordInputValue] = useState<string>("");
  const [loginError, setLoginError] = useState<boolean>(false);
  const [loginErrorMessage, setLoginErrorMessage] = useState<string>();

  const { updateProfile } = useAuth();
  const navigate = useNavigate();
  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };

  const goLoginPage = () => {
    setLoginError(false);
    navigate("/");
  };

  const goJoinPage = () => {
    navigate("/join");
  };

  const goGitHubLogin = () => {
    window.location.assign(
      "https://github.com/login/oauth/authorize?client_id=Iv1.7ee4a0c3b7d0f238&redirect_uri=http://localhost:5173/redirect/oauth",
    );
  };

  const handleIdInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIdInputValue(e.target.value);
  };

  const handlePasswordInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setPasswordInputValue(e.target.value);
  };

  const onLogin = async () => {
    const URL = `http://3.34.141.196/api/account/login`;

    const postData = {
      email: idInputValue,
      password: passwordInputValue,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(postData),
      });

      if (response.status === 200) {
        const data = await response.json();
        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);
        updateProfile(decodeBase64(data.accessToken));
        goMainPage();
      } else {
        setLoginErrorMessage("아이디, 혹은 비밀번호가 일치하지 않습니다");
        setLoginError(true);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  useEffect(() => {
    if (idInputValue === "") {
      setHasIdError(false);
      return;
    }

    setHasIdError(!isValidEmail(idInputValue));
  }, [idInputValue]);

  useEffect(() => {
    (passwordInputValue.length > 0 && passwordInputValue.length < 6) ||
    passwordInputValue.length > 12
      ? setHasPasswordError(true)
      : setHasPasswordError(false);
  }, [passwordInputValue]);

  return (
    <Main>
      <Logo href={""}>
        <LogoImg src={"/logo/mediumLogo.svg"} alt={"Issue Tracker"} />
      </Logo>
      <Container>
        <Button
          label={"GitHub 계정으로 로그인"}
          type={"outline"}
          size={"large"}
          width={"100%"}
          onClick={goGitHubLogin}
        />
        <Span>or</Span>
        <Form>
          <Id>
            <TextInput
              id={"id"}
              label={"이메일"}
              hasCaption={hasIdError}
              caption={"이메일 형식으로 입력해주세요"}
              captionType={"error"}
              onChange={handleIdInputChange}
            />
          </Id>
          <Password>
            <TextInput
              id={"password"}
              inputType={"password"}
              label={"비밀번호"}
              hasCaption={hasPasswordError}
              caption={"영문, 숫자 조합 6자 이상 12자 이하"}
              captionType={"error"}
              onChange={handlePasswordInputChange}
            />
          </Password>
        </Form>
        <Button
          label={"아이디로 로그인"}
          size={"large"}
          width={"100%"}
          onClick={onLogin}
          disabled={
            !idInputValue ||
            !passwordInputValue ||
            hasIdError ||
            hasPasswordError
          }
        />
        <Button
          label={"계정 만들기"}
          type={"ghost"}
          size={"medium"}
          onClick={goJoinPage}
        />
      </Container>
      {loginError && (
        <Alert
          content={loginErrorMessage!}
          leftButtonLabel={"다시 로그인하기"}
          rightButtonLabel={"회원가입 하기"}
          onClickLeftButton={goLoginPage}
          onClickRightButton={goJoinPage}
        />
      )}
    </Main>
  );
}

const slideInRight = keyframes`
  from {
    transform: translateX(-50%);
    opacity: 0;

  }
  to {
    transform: translateX(0%);
    opacity: 1;
  }
`;

const Main = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  gap: 64px;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Logo = styled.a`
  width: 342px;
  height: 72px;
`;

const LogoImg = styled.img`
  width: 342px;
  height: 72px;
  filter: ${({ theme }) => theme.filter.neutral.text.strong};
`;

const Container = styled.div`
  width: 320px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  animation: ${slideInRight} 0.5s ease-in-out;
`;

const Span = styled.span`
  width: 100%;
  text-align: center;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const Id = styled.div`
  width: 100%;
  height: 76px;
`;

const Password = styled.div`
  width: 100%;
  height: 76px;
`;
