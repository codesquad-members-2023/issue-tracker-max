import { useContext, useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import GlobalStyle from './style/Global';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Components from './pages/Components';
import Login from './pages/Login';
import Register from './pages/Register';
import Main from './pages/Main';

import LogoDarkLarge from './asset/logo/logo_dark_large.svg';
import LogoDarkMedium from './asset/logo/logo_dark_medium.svg';
import LogoLightLarge from './asset/logo/logo_light_large.svg';
import LogoLightMedium from './asset/logo/logo_light_medium.svg';
import { AppContext } from './main';
import AuthenticatedRoute from './routes/AuthenticatedRoute';

function App() {
  const [isLight, setIsLight] = useState<boolean>(true);
  const { util, control } = useContext(AppContext);
  util.getLogoByTheme = () =>
    isLight
      ? {
          large: LogoLightLarge,
          medium: LogoLightMedium,
        }
      : {
          large: LogoDarkLarge,
          medium: LogoDarkMedium,
        };
  control.changeTheme = () => {
    setIsLight(!isLight);
  };

  return (
    <ThemeProvider theme={isLight ? lightTheme : darkTheme}>
      <GlobalStyle />
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <AuthenticatedRoute>
                <Routes>
                  <Route path="/" element={<Main />} />
                </Routes>
              </AuthenticatedRoute>
            }
          />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/component" element={<Components />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
