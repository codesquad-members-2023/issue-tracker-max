import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";
import { Icon } from "components/Common/Icon/Icon";
import { ProfileImg } from "components/Common/Profile/Profile";
import { Button } from "components/Common/Button/Button";
import logoLight from "assets/img/logo_medium.svg";
import logoDark from "assets/img/logo_medium-dark.svg";
import { useAuth } from "../../contexts/AuthContext";

import { useThemeMode } from "contexts/ThemeModeContext";

export const MainHeader = () => {
  const navigate = useNavigate();
  const { toggleTheme, mode } = useThemeMode();
  const { logout, accessToken, user } = useAuth();
  console.log(user);

  const handleLoginClick = () => {
    navigate("/login");
  };
  const handleLoginOutClick = () => {
    logout();
    navigate("/");
  };

  return (
    <HeaderLayout>
      <Link to="/">
        <img
          src={mode === "light" ? logoLight : logoDark}
          alt="Issue Tracker 로고"
        />
      </Link>
      <div>
        <ThemeModeButton onClick={toggleTheme}>
          {mode === "light" ? (
            <Icon icon="Dark" size="L" fill="nuetralTextDefault" />
          ) : (
            <Icon icon="Light" size="L" fill="nuetralTextDefault" />
          )}
        </ThemeModeButton>
        {/* 로그인 된 사람 프로필 넣어야함 */}
        {accessToken ? (
          <>
            <ProfileImg size={32} $url={user?.profile} />
            <Button variant="ghost" size="M" onClick={handleLoginOutClick}>
              로그아웃
            </Button>
          </>
        ) : (
          <Button variant="ghost" size="M" onClick={handleLoginClick}>
            로그인
          </Button>
        )}
      </div>
    </HeaderLayout>
  );
};

const HeaderLayout = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 27px 0;

  > div {
    display: flex;
    gap: 16px;
    align-items: center;
  }
`;

const ThemeModeButton = styled.button``;
