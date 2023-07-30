import React, { useState } from 'react';
import { styled, ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import Theme from './constant/Theme';
import GlobalStyle from './style/Global';
// import Button from './components/Button';
import Button from './components/BaseButton';
import ButtonLarge from './components/ButtonLarge';
import ButtonSmall from './components/ButtonSmall';

const { light, dark } = Theme;

function App() {
  const [theme, setTheme] = useState<Theme>(light);

  const changeTheme = () => {
    setTheme((theme) => (theme === light ? dark : light));
  };

  return (
    <ThemeProvider theme={theme === light ? lightTheme : darkTheme}>
      <GlobalStyle />
      <StyledApp>
        <ButtonLarge type="submit" iconName="plus">
          BUTTON
        </ButtonLarge>
        <Button type="submit" outline iconName="plus">
          BUTTON
        </Button>
        <Button type="submit" ghost flexible iconName="plus">
          This is Flexible BUTTON
        </Button>
        <ButtonSmall type="submit" ghost iconName="plus">
          BUTTON
        </ButtonSmall>
        <ButtonSmall type="button" ghost flexible onClick={changeTheme}>
          Change Theme
        </ButtonSmall>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  background: ${({ theme }) => theme.color.neutral.surface.default};
`;

export default App;
