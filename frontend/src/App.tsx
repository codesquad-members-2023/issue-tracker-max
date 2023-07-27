import { ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import Theme from './constant/Theme';
import React, { useState } from 'react';

const { light, dark } = Theme;

const App: React.FC = () => {
  const [theme, setTheme] = useState<Theme>(light);

  function changeTheme() {
    setTheme((theme) => (theme === light ? dark : light));
  }

  return (
    <ThemeProvider theme={theme === light ? lightTheme : darkTheme}>
      <button onClick={changeTheme}>테마</button>
      <h1>Helloooooo World!</h1>
    </ThemeProvider>
  );
};

export default App;
