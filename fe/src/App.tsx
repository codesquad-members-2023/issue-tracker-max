import { Global, ThemeProvider } from '@emotion/react';
import { darkMode, lightMode } from '@styles/designSystem';
import { globalStyle } from '@styles/globalStyle';
import { useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { AppRoutes } from 'routes';

export const App: React.FC = () => {
  const [userId, setUserId] = useState();
  const [accessToken, setAccessToken] = useState();
  const [currentTheme, setCurrentTheme] = useState(true);
  const theme = currentTheme ? lightMode : darkMode;

  const resetUserId = () => {};
  const resetAccessToken = () => {};
  const toggleCurrentTheme = () => {};

  return (
    <ThemeProvider theme={theme}>
      <Global styles={globalStyle} />
      <div
        css={{
          width: '100vw',
          height: '100vh',
          backgroundColor: theme.neutral.surface.default,
        }}
      >
        <div css={{ width: '1280px', margin: 'auto' }}>
          <BrowserRouter>
            <AppRoutes />
          </BrowserRouter>
        </div>
      </div>
    </ThemeProvider>
  );
};
