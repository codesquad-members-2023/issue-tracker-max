import { useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { ThemeProvider, styled } from "styled-components";
import { Header } from "./components/Header";
import { designSystem } from "./constants/designSystem";
import { Error404 } from "./page/Error404";
import { Main } from "./page/main/Main";
import { NewIssue } from "./page/NewIssue";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  return (
    <Div>
      <ThemeProvider theme={designSystem[themeMode]}>
        <Router>
          <Header />
          <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/issues/new" element={<NewIssue />} />
            <Route path="*" element={<Error404 />} />
          </Routes>
        </Router>
      </ThemeProvider>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
