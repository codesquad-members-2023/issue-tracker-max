import { ThemeProvider, styled, css } from "styled-components";
import { lightTheme } from "./theme";
import { darkTheme } from "./theme";
import Theme from "./constants/Theme";
import { useState } from "react";

const { light, dark } = Theme;

function App() {
  const [theme, setTheme] = useState<Theme>(light);

  const toggleTheme = () => {
    if (theme === light) {
      setTheme(dark);
    } else {
      setTheme(light);
    }
  };

  return (
    <ThemeProvider theme={theme === light ? lightTheme : darkTheme}>
      <StyledApp>
        <button onClick={toggleTheme}>테마</button>
        <h1>Helloooooo World!</h1>
      </StyledApp>
    </ThemeProvider>
  );
}

const StyledApp = styled.div`
  h1 {
    ${({ theme }) => theme.font.display.bold[32]};
  }
  color: ${({ theme }) => theme.color.neutral.text.strong};
`;

export default App;
