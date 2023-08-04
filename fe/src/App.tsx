import { Global, ThemeProvider } from '@emotion/react';
import { darkMode, lightMode } from '@styles/designSystem';
import { globalStyle } from '@styles/globalStyle';
import { useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { AppRoutes } from 'routes';

export const App: React.FC = () => {
  const [userId, setUserId] = useState();
  const [accessToken, setAccessToken] = useState();
  const [currentTheme, setCurrentTheme] = useState<ThemeType>('light');
  const theme = currentTheme === 'light' ? lightMode : darkMode;

  const resetUserId = () => {};
  const resetAccessToken = () => {};
  const toggleTheme = () => {
    setCurrentTheme((prevTheme) => (prevTheme === 'light' ? 'dark' : 'light'));
  };

  return (
    <ThemeProvider theme={theme}>
      <Global styles={globalStyle} />

      <div css={{ width: '1280px', margin: 'auto' }}>
        <BrowserRouter>
          <AppRoutes {...{ currentTheme, toggleTheme }} />
        </BrowserRouter>
      </div>
    </ThemeProvider>
  );
};
