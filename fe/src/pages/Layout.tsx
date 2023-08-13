import styled from "styled-components";
import { Outlet, useLocation } from "react-router-dom";
import { MainHeader } from "components/Header/MainHeader";

export const Layout = () => {
  const location = useLocation();
  return (
    <BodyLayout>
      <PageLayout>
        {location.pathname !== "/login" && <MainHeader />}
        <Outlet />
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
