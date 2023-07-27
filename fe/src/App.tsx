import { ThemeProvider } from '@emotion/react';
import { darkMode, lightMode } from '@styles/designSystem';
import { useState } from 'react';

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
      <div>Hello world</div>
    </ThemeProvider>
  );
};
