import { ThemeProvider } from '@emotion/react';
import { darkMode, lightMode } from '@styles/designSystem';
import { useState } from 'react';

export const App: React.FC = () => {
  const [isLightMode, setIsLightMode] = useState(true);
  const theme = isLightMode ? lightMode : darkMode;

  return (
    <ThemeProvider theme={theme}>
      <div>Hello world</div>
    </ThemeProvider>
  );
};
