import { useState } from 'react';
import { styled, ThemeProvider } from 'styled-components';
import { lightTheme } from './theme';
import { darkTheme } from './theme';
import GlobalStyle from './style/Global';
import Button from './components/common/button/BaseButton';
import ButtonLarge from './components/common/button/ButtonLarge';
import ButtonSmall from './components/common/button/ButtonSmall';
import ColorCodeInput from './components/common/ColorCodeInput';
import TabButton from './components/common/TabButton';
import InformationTag from './components/common/InformationTag';
import TextInput from './components/common/TextInput';
import ProgressIndicator from './components/common/ProgressIndicator';

function App() {
  const [isLight, setIsLight] = useState<boolean>(true);

  const changeTheme = () => {
    setIsLight(!isLight);
  };

  return (
    <ThemeProvider theme={isLight ? lightTheme : darkTheme}>
      <GlobalStyle />
      <StyledApp>
        <Wrapper>
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
          <ColorCodeInput label="배경색" />
          <TabButton />
          <InformationTag size="medium" iconName="alertCircle">
            Label
          </InformationTag>
          <TextInput
            size="tall"
            labelName="label"
            placeholder="placeholder"
            helpText="5자리 이상 입력해주세요."
            validationFunc={(value) => {
              return value.length >= 5;
            }}
          />
          <TextInput
            size="short"
            labelName="label"
            placeholder="placeholder"
            helpText="Caption"
          />
          <ProgressIndicator openCount={0} closeCount={0} />
        </Wrapper>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: ${({ theme }) => theme.color.neutral.surface.default};
`;

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
`;

export default App;
