import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import TextInput from "../components/common/TextInput/TextInput";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [hasIdError, setHasIdError] = useState<boolean>(false);
  const [hasPasswordError, setHasPasswordError] = useState<boolean>(false);
  const [idInputValue, setIdInputValue] = useState<string>("");
  const [passwordInputValue, setPasswordInputValue] = useState<string>("");

  const navigate = useNavigate();
  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };

  const goGoogle = () => {
    window.location.href = "https://www.google.com";
  };

  const handleIdInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIdInputValue(e.target.value);
  };

  const handlePasswordInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setPasswordInputValue(e.target.value);
  };

  useEffect(() => {
    (idInputValue.length > 0 && idInputValue.length < 6) ||
    idInputValue.length > 12
      ? setHasIdError(true)
      : setHasIdError(false);
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
          onClick={goGoogle}
        />
        <Span>or</Span>
        <Form>
          <Id>
            <TextInput
              id={"id"}
              label={"아이디"}
              hasCaption={hasIdError}
              caption={"영문, 숫자 조합 6자 이상 12자 이하"}
              captionType={"normal"}
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
          onClick={goMainPage}
          disabled={
            !idInputValue ||
            !passwordInputValue ||
            hasIdError ||
            hasPasswordError
          }
        />
        <Button
          label={"회원가입"}
          type={"ghost"}
          size={"medium"}
          onClick={() => {}}
        />
      </Container>
    </Main>
  );
}

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
