import { keyframes, styled } from "styled-components";
import TextInput from "../components/common/TextInput/TextInput";
import { ChangeEvent, useEffect, useState } from "react";
import Button from "../components/common/Button/Button";
import { useNavigate } from "react-router-dom";
import { isValidEmail } from "../utils/isValidEmail";
import { MAX_FILE_SIZE } from "../constant/constant";
import Alert from "../components/Alert/Alert";

export default function JoinPage() {
  const navigate = useNavigate();
  const [joinError, setJoinError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>();
  const [profileImgFile, setProfileImgFile] = useState<File>();
  const [profileImg, setProfileImg] = useState<string>("/icons/user.png");
  const [emailValue, setEmailValue] = useState<string>();
  const [hasEmailError, setHasEmailError] = useState<boolean>(false);
  const [hasPasswordError, setHasPasswordError] = useState<boolean>(false);
  const [hasPasswordCheckError, setHasPasswordCheckError] =
    useState<boolean>(false);
  const [passwordValue, setPasswordValue] = useState<string>("");
  const [checkPasswordValue, setCheckPasswordValue] = useState<string>("");
  const [nicknameValue, setNicknameValue] = useState<string>("");

  const onUpload = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file && file.size <= MAX_FILE_SIZE) {
      setProfileImgFile(file);
      setProfileImg(URL.createObjectURL(file));
    } else {
      setProfileImgFile(undefined);
      setProfileImg("/icons/user.png");
    }
  };

  const removeProfileImg = () => {
    setProfileImg("/icons/user.png");
  };

  const goLoginPage = () => {
    navigate("/");
  };

  const continueJoin = () => {
    setJoinError(false);
  };

  const handleEmailInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmailValue(e.target.value);
  };

  const handlePasswordInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setPasswordValue(e.target.value);
  };

  const handleCheckPasswordInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setCheckPasswordValue(e.target.value);
  };

  const handleNicknameInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setNicknameValue(e.target.value);
  };

  const confirmJoin = async () => {
    const url = "http://3.34.141.196/api/account/signup";
    const formData = new FormData();
    const data = {
      email: emailValue,
      password: passwordValue,
      nickname: nicknameValue,
      profileImageUrl: null,
    };

    formData.append(
      "request",
      new Blob([JSON.stringify(data)], { type: "application/json" }),
    );
    profileImgFile && formData.append("file", profileImgFile);

    try {
      const response = await fetch(url, {
        method: "POST",
        body: formData,
      });

      if (response.status === 204) {
        const responseData = await response.json();
        localStorage.setItem("accessToken", responseData.accessToken);
        localStorage.setItem("refreshToken", responseData.refreshToken);
      } else {
        const responseData = await response.json();
        setErrorMessage(responseData.message);
        setJoinError(true);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  useEffect(() => {
    if (!emailValue) {
      setHasEmailError(false);
      return;
    }
    isValidEmail(emailValue) ? setHasEmailError(false) : setHasEmailError(true);
  }, [emailValue]);

  useEffect(() => {
    (passwordValue.length > 0 && passwordValue.length < 6) ||
    passwordValue.length > 12
      ? setHasPasswordError(true)
      : setHasPasswordError(false);
  }, [passwordValue]);

  useEffect(() => {
    if (checkPasswordValue === "") {
      setHasPasswordCheckError(false);
      return;
    }
    passwordValue === checkPasswordValue
      ? setHasPasswordCheckError(false)
      : setHasPasswordCheckError(true);
  }, [passwordValue, checkPasswordValue]);

  return (
    <Main>
      <Logo href={"/"}>
        <LogoImg src={"/logo/mediumLogo.svg"} alt={"Issue Tracker"} />
      </Logo>
      <Container>
        <ProfileImgForm>
          <ProfileImg src={profileImg} />
          <ProfileUploadLabel htmlFor={"profileImg"}>+</ProfileUploadLabel>
          <ImgRemoveButton onClick={removeProfileImg}>-</ImgRemoveButton>
          <ProfileUploadInput
            id={"profileImg"}
            type="file"
            accept="image/*"
            onChange={onUpload}
          />
        </ProfileImgForm>
        <Form>
          <TextInput
            id={"email"}
            label={"이메일"}
            hasCaption={hasEmailError}
            caption={"이메일 형식으로 입력해주세요"}
            captionType={"error"}
            onChange={handleEmailInputChange}
          />
          <TextInput
            id={"password"}
            inputType={"password"}
            label={"비밀번호"}
            hasCaption={hasPasswordError}
            caption={"영문, 숫자 조합 6자 이상 12자 이하"}
            captionType={"error"}
            onChange={handlePasswordInputChange}
          />
          <TextInput
            id={"checkPassword"}
            inputType={"password"}
            label={"비밀번호 확인"}
            hasCaption={hasPasswordCheckError}
            caption={"위의 비밀번호와 일치하지 않습니다"}
            captionType={"error"}
            onChange={handleCheckPasswordInputChange}
          />
          <TextInput
            id={"nickname"}
            label={"닉네임"}
            caption={"영문, 숫자 조합 6자 이상 12자 이하"}
            onChange={handleNicknameInputChange}
          />
        </Form>
        <ButtonTap>
          <Button
            label={"로그인으로 돌아가기"}
            type={"ghost"}
            onClick={goLoginPage}
          />
          <Button
            label={"가입하기"}
            onClick={confirmJoin}
            disabled={
              !emailValue ||
              !passwordValue ||
              !checkPasswordValue ||
              !nicknameValue ||
              hasEmailError ||
              hasPasswordCheckError ||
              hasPasswordError
            }
          />
        </ButtonTap>
      </Container>
      {joinError && (
        <Alert
          content={errorMessage!}
          leftButtonLabel={"로그인 페이지로 돌아가기"}
          rightButtonLabel={"이어서 가입하기"}
          onClickLeftButton={goLoginPage}
          onClickRightButton={continueJoin}
        />
      )}
    </Main>
  );
}

const slideInRight = keyframes`
  from {
    transform: translateX(50%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
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
  align-items: center;
  gap: 16px;
  animation: ${slideInRight} 0.5s ease-in-out;
`;

const ProfileImgForm = styled.div`
  position: relative;
  padding: 30px;
  width: 200px;
  height: 200px;
`;

const ProfileImg = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
`;

const ProfileUploadLabel = styled.label`
  position: absolute;
  right: 30px;
  bottom: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  background-color: ${({ theme }) => theme.colorSystem.brand.surface.default};
  border-radius: 50%;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.colorSystem.brand.text.default};
  &:hover {
    width: 40px;
    height: 40px;
  }
`;

const ImgRemoveButton = styled.button`
  position: absolute;
  left: 30px;
  bottom: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  background-color: ${({ theme }) => theme.colorSystem.danger.surface.default};
  border-radius: 50%;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.colorSystem.brand.text.default};
  &:hover {
    width: 40px;
    height: 40px;
  }
`;

const ProfileUploadInput = styled.input`
  display: none;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  height: 304px;
  justify-content: space-between;
`;

const ButtonTap = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 50px;
`;
