import React, { useContext, useEffect, useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import GlobalStyle from './style/Global';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  useLocation,
} from 'react-router-dom';
import Components from './pages/Components';
import Login from './pages/Login';
import Main from './pages/Main';

import LogoDarkLarge from './asset/logo/logo_dark_large.svg';
import LogoDarkMedium from './asset/logo/logo_dark_medium.svg';
import LogoLightLarge from './asset/logo/logo_light_large.svg';
import LogoLightMedium from './asset/logo/logo_light_medium.svg';
import { AppContext } from './main';

const AuthenticatedRoute = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const session = localStorage.getItem('session');
    setIsAuthenticated(!!session);
  }, [location]); // location이 바뀔 때마다 로그인 상태를 확인합니다.

  return isAuthenticated ? children : <Navigate to="/login" replace />;
};

function App() {
  const [isLight, setIsLight] = useState<boolean>(true);
  const context = useContext(AppContext);
  context.util.getLogoByTheme = () =>
    isLight
      ? {
          large: LogoLightLarge,
          medium: LogoLightMedium,
        }
      : {
          large: LogoDarkLarge,
          medium: LogoDarkMedium,
        };
  context.control.changeTheme = () => {
    setIsLight(!isLight);
  };

  return (
    <ThemeProvider theme={isLight ? lightTheme : darkTheme}>
      <GlobalStyle />
      {/* <ButtonSmall type="button" ghost flexible onClick={changeTheme}>
        Change Theme
      </ButtonSmall> */}
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <AuthenticatedRoute>
                <Route element={<Main />} />
              </AuthenticatedRoute>
            }
          />
          <Route path="/login" element={<Login />} />
          <Route path="/component" element={<Components />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
