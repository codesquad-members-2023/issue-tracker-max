import { Outlet, useLocation } from "react-router-dom";
import { Header } from "components/Header";
import styled from "styled-components";

export const Layout = () => {
  const location = useLocation();

  return (
    <BodyLayout>
      <PageLayout>
        {location.pathname !== "/login" && <Header />}
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
