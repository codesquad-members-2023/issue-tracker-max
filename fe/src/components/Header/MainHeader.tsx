import styled from "styled-components";
import logoLight from "assets/img/logo_medium.svg";
import logoDark from "assets/img/logo_medium-dark.svg";
import UserTestProfile from "assets/img/profile_test.svg";

import { useThemeMode } from "contexts/ThemeModeContext";

export const MainHeader = () => {
  const { mode } = useThemeMode();

  return (
    <HeaderLayout>
      <img
        src={mode === "light" ? logoLight : logoDark}
        alt="Issue Tracker 로고"
      />
      <img src={UserTestProfile} alt="내 프로필 이미지" width={32} />
    </HeaderLayout>
  );
};

const HeaderLayout = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 27px 0;
`;
