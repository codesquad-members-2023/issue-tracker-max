import { useState } from 'react';
import { styled, ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import GlobalStyle from './style/Global';
import Button from './components/common/button/BaseButton';
import ButtonLarge from './components/common/button/ButtonLarge';
import ButtonSmall from './components/common/button/ButtonSmall';
import ColorCodeInput from './components/common/ColorCodeInput';

function App() {
  const [isLight, setIsLight] = useState<boolean>(true);

  const changeTheme = () => {
    setIsLight(!isLight);
  };

  return (
    <ThemeProvider theme={isLight ? lightTheme : darkTheme}>
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

      <ColorCodeInput label="배경색" />
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  background: ${({ theme }) => theme.color.neutral.surface.default};
`;

export default App;
