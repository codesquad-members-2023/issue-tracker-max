import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { styled } from "styled-components";
import { Icon } from "../../components/icon/Icon";
import { setAccessToken, setUserInfo } from "../../utils/localStorage";
import { UserInfoData } from "./Auth";

export function OauthLoading() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const code = searchParams.get("code");
  const [errorMessage, setErrorMessage] = useState("");

  const login = async () => {
    const res = await fetch(`/api/login/oauth/github?code=${code}`, {
      method: "POST",
    });

    const { code: resCode, message, data } = await res.json();
    if (resCode === 200) {
      setAccessToken(data.jwt.accessToken);
      setUserInfo(data.user as UserInfoData);

      navigate("/");
    } else {
      setErrorMessage(message);
    }
  };

  login();

  return (
    <Div>
      <Icon name="LogoLarge" color="neutralTextStrong" />
      <span>{errorMessage || `Github 계정으로 로그인 중...`}</span>
    </Div>
  );
}

const Div = styled.div`
  width: 100vh;
  height: 100vh;
  display: flex;
  flex-direction: column;
  gap: 40px;
  justify-content: center;
  align-items: center;
  font-size: 32px;
`;
