import styled from "styled-components";
import { Outlet, useLocation } from "react-router-dom";
import { MainHeader } from "components/Header/MainHeader";
import { Icon } from "components/Common/Icon/Icon";

import { useThemeMode } from "contexts/ThemeModeContext";

export const Layout = () => {
  const location = useLocation();
  const { toggleTheme, mode } = useThemeMode();
  return (
    <BodyLayout>
      <PageLayout>
        {location.pathname !== "/login" && <MainHeader />}
        <Outlet />

        <ThemeModeButton onClick={toggleTheme}>
          {mode === "light" ? (
            <Icon icon="Dark" size="L" fill="nuetralTextDefault" />
          ) : (
            <Icon icon="Light" size="L" fill="nuetralTextDefault" />
          )}
        </ThemeModeButton>
      </PageLayout>
    </BodyLayout>
  );
};

const BodyLayout = styled.div`
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  width: 100%;
  height: 100vh;
`;

const PageLayout = styled.div`
  width: 1280px;
  margin: 0 auto;
`;

const ThemeModeButton = styled.button`
  position: absolute;
  bottom: 40px;
  right: 80px;
  z-index: 10;
`;
