import styled from "styled-components";
import { Link } from "react-router-dom";
import { Icon } from "components/Common/Icon/Icon";
import logoLight from "assets/img/logo_medium.svg";
import logoDark from "assets/img/logo_medium-dark.svg";
import UserTestProfile from "assets/img/profile_test.svg";

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
        <img src={UserTestProfile} alt="내 프로필 이미지" width={32} />
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

const ThemeModeButton = styled.button`
  /* position: absolute;
  bottom: 40px;
  right: 80px;
  z-index: 10; */
`;
