import React, { useState } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import Theme from './constant/Theme';
import Button from './components/Button';
import GlobalStyle from './style/Global';

const { light, dark } = Theme;

function App() {
  const [theme, setTheme] = useState<Theme>(light);

  const changeTheme = () => {
    setTheme((theme) => (theme === light ? dark : light));
  };

  return (
    <ThemeProvider theme={theme === light ? lightTheme : darkTheme}>
      <GlobalStyle />
      <Button
        $Flexible="Fixed"
        $Type="Ghost"
        $ElementPattern="Icon+Text"
        $State="Selected/Active"
        $Size="L"
        iconName="plus"
        text="BUTTON"></Button>
      <button onClick={changeTheme}>테마</button>
      <h1>Helloooooo World!</h1>
    </ThemeProvider>
  );
}

export default App;
