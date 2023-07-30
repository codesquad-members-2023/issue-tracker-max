import { Outlet, useLocation } from 'react-router-dom';
import { Header } from 'components/Header';

export const Layout = () => {
  const location = useLocation();

  return (
    <>
      {location.pathname !== '/login' && <Header />}
      <Outlet />
    </>
  );
};
