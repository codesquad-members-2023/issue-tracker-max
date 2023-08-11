import styled from "styled-components";
import { Link } from "react-router-dom";
import { Icon } from "components/Common/Icon/Icon";
import { ProfileImg } from "components/Common/Profile/Profile";
import logoLight from "assets/img/logo_medium.svg";
import logoDark from "assets/img/logo_medium-dark.svg";

import { useThemeMode } from "contexts/ThemeModeContext";

export const MainHeader = () => {
  const { toggleTheme, mode } = useThemeMode();

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
        <ProfileImg
          size={32}
          $url={"https://source.boringavatars.com/beam/20/nag"}
        />
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
