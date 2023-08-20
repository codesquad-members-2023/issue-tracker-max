import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { styled } from "styled-components";
import { useAuth } from "../context/AuthContext";
import Header from "../components/Header/Header";

type Props = {
  toggleTheme(): void;
};

export function OauthPage({ toggleTheme }: Props) {
  const navigate = useNavigate();
  const { profile } = useAuth();
  const [searchParams] = useSearchParams();
  const code = searchParams.get("code");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      const URL = `http://3.34.141.196/api/account/oauth/callback?code=${code}`;
      try {
        const response = await fetch(URL);
        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }
        const jsonData = await response.json();
        localStorage.setItem("accessToken", jsonData.accessToken);
        localStorage.setItem("refreshToken", jsonData.refreshToken);
        navigate("/issues/isOpen=true");
      } catch (error) {
        setErrorMessage("error");
      }
    };
    fetchData();
  }, []);

  return (
    <>
      <Header
        toggleTheme={toggleTheme}
        profileImg={profile ? profile.profileImageUri : "/icons/user.png"}
      />
      <Div>
        <span>{errorMessage || `Github 계정으로 로그인 중...`}</span>
      </Div>
    </>
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
