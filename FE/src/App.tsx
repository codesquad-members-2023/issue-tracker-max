import { useDarkMode } from "./hooks/useDarkMode.ts";
import AddIssuePage from "./pages/AddIssuePage.tsx";
import MainPage from "./pages/MainPage.tsx";
import { lightMode, darkMode } from "./styles/DesignSystem.ts";
import { ThemeProvider, styled } from "styled-components";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage.tsx";
import Header from "./components/Header/Header.tsx";
import LabelsPage from "./pages/LabelsPage.tsx";
import MilestonesPage from "./pages/MilestonesPage.tsx";
import IssueDetail from "./pages/IssueDetail.tsx";

function App() {
  const [theme, toggleTheme] = useDarkMode();
  const themeMode = theme === "light" ? lightMode : darkMode;

  return (
    <ThemeProvider theme={themeMode}>
      <BrowserRouter>
        <Page>
          <Header toggleTheme={toggleTheme} />
          <Routes>
            <Route path="/issues" element={<MainPage />}></Route>
            <Route path="" element={<LoginPage />}></Route>
            <Route path="/new" element={<AddIssuePage />}></Route>
            <Route path="/labels" element={<LabelsPage />}></Route>
            <Route
              path="/milestones/:state"
              element={<MilestonesPage />}
            ></Route>
            <Route path="/issues/:filter" element={<MainPage />}></Route>
            <Route path="/detail/:id" element={<IssueDetail />}></Route>
          </Routes>
        </Page>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;

const Page = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 100vw;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;
