import { useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { ThemeProvider, styled } from "styled-components";
import { Header } from "./components/Header";
import { designSystem } from "./constants/designSystem";
import { Error404 } from "./page/Error404";
import { Label } from "./page/label/Label";
import { Main } from "./page/main/Main";
import { NewIssue } from "./page/NewIssue";
import { Milestone } from "./page/milestone/Milestone";

export default function App() {
  const [themeMode, setThemeMode] = useState<"LIGHT" | "DARK">("LIGHT");

  return (
    <Div>
      <ThemeProvider theme={designSystem[themeMode]}>
        <Router>
          <Header />
          <Routes>
            <Route path="/" element={<Main />} />
            <Route path="/issues/new" element={<NewIssue />} />
            <Route path="/label" element={<Label />} />
            <Route path="/milestone" element={<Milestone />} />
            <Route path="/milestone/:state" element={<Milestone />} />
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
