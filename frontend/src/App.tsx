import { useState } from "react";
import { ThemeProvider, styled } from "styled-components";
import { Header } from "./components/Header";
import { Main } from "./components/main/Main";
import { designSystem } from "./constants/designSystem";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  return (
    <Div>
      <ThemeProvider theme={designSystem[themeMode]}>
        <Header />
        <Main />
      </ThemeProvider>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
