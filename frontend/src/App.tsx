import { useContext, useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import GlobalStyle from './style/Global';
import { Routes, Route } from 'react-router-dom';
import Components from './pages/Components';
import Login from './pages/Login';
import Register from './pages/Register';
import Callback from './pages/GitHubCallback';
// import Main from './pages/Main';
import Issues from './pages/Issues';
import AddIssue from './pages/AddIssue';
import IssueDetail from './pages/IssueDetail';
import Options from './pages/Options';

import LogoDarkLarge from './asset/logo/logo_dark_large.svg';
import LogoDarkMedium from './asset/logo/logo_dark_medium.svg';
import LogoLightLarge from './asset/logo/logo_light_large.svg';
import LogoLightMedium from './asset/logo/logo_light_medium.svg';
import { AppContext } from './main';
import RequireAuth from './routes/RequireAuth';

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
      <Routes>
        {/* public routes */}
        <Route path="/Options" element={<Options />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/component" element={<Components />} />
        <Route path="/callback" element={<Callback />} />
        {/* <Route path="/issues" element={<Issues />} /> */}
        <Route path="/addIssue" element={<AddIssue />} />
        <Route path="/issueDetail" element={<IssueDetail />} />

        {/* protected routes */}
        <Route element={<RequireAuth />}>
          <Route path="/" element={<Issues />} />
        </Route>
      </Routes>
    </ThemeProvider>
  );
}

export default App;
