import { keyframes, styled } from "styled-components";
import TextInput from "../components/common/TextInput/TextInput";
import { ChangeEvent, useEffect, useState } from "react";
import Button from "../components/common/Button/Button";
import { useNavigate } from "react-router-dom";
import { MAX_FILE_SIZE } from "../constant/constant";
import Alert from "../components/Alert/Alert";
import Header from "../components/Header/Header";
import { useAuth } from "../context/AuthContext";

type Props = {
  toggleTheme(): void;
};

export default function EditProfile({ toggleTheme }: Props) {
  const navigate = useNavigate();
  const { profile } = useAuth();
  const [editError, setEditError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>();
  const [profileImgFile, setProfileImgFile] = useState<File>();
  const [profileImg, setProfileImg] = useState<string>(
    profile ? profile.profileImageUri : "/icons/user.png",
  );
  const [hasPasswordError, setHasPasswordError] = useState<boolean>(false);
  const [hasPasswordCheckError, setHasPasswordCheckError] =
    useState<boolean>(false);
  const [hasNicknameError, setHasNicknameError] = useState<boolean>(false);
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

  const goMainPage = () => {
    navigate("/issues/isOpen=true");
  };

  const continueEdit = () => {
    setEditError(false);
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

  const confirmEditProfile = async () => {
    const url = `http://3.34.141.196/api/members/${profile!.memberId}`;
    const formData = new FormData();
    const data = {
      nickname: nicknameValue,
      password: passwordValue,
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
        setEditError(true);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  useEffect(() => {
    nicknameValue === profile?.nickname
      ? setHasNicknameError(true)
      : setHasNicknameError(false);
  }, [nicknameValue]);

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
    <>
      <Header
        toggleTheme={toggleTheme}
        profileImg={profile ? profile.profileImageUri : "/icons/user.png"}
      />
      <Main>
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
              hasCaption={hasNicknameError}
              caption={"기존의 닉네임과 다른 닉네임을 입력해주세요"}
              onChange={handleNicknameInputChange}
            />
          </Form>
          <ButtonTap>
            <Button
              label={"메인으로 돌아가기"}
              type={"ghost"}
              onClick={goMainPage}
            />
            <Button
              label={"수정하기"}
              onClick={confirmEditProfile}
              disabled={
                !passwordValue ||
                !checkPasswordValue ||
                !nicknameValue ||
                hasPasswordCheckError ||
                hasPasswordError ||
                hasNicknameError
              }
            />
          </ButtonTap>
        </Container>
        {editError && (
          <Alert
            content={errorMessage!}
            leftButtonLabel={"메인으로 돌아가기"}
            rightButtonLabel={"이어서 수정하기"}
            onClickLeftButton={goMainPage}
            onClickRightButton={continueEdit}
          />
        )}
      </Main>
    </>
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
  padding-top: 100px;
  width: 100vw;
  display: flex;
  flex-direction: column;
  gap: 64px;
  justify-content: start;
  align-items: center;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
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
  height: 220px;
  justify-content: space-between;
`;

const ButtonTap = styled.div`
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 50px;
`;
