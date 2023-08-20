import { useDarkMode } from "./hooks/useDarkMode.ts";
import AddIssuePage from "./pages/AddIssuePage.tsx";
import MainPage from "./pages/MainPage.tsx";
import { lightMode, darkMode } from "./styles/DesignSystem.ts";
import { ThemeProvider, styled } from "styled-components";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage.tsx";
import LabelsPage from "./pages/LabelsPage.tsx";
import MilestonesPage from "./pages/MilestonesPage.tsx";
import IssueDetail from "./pages/IssueDetail.tsx";
import JoinPage from "./pages/JoinPage.tsx";
import { OauthPage } from "./pages/OauthPage.tsx";
import { AuthProvider } from "./context/AuthContext.tsx";
import EditProfile from "./pages/EditProfilePage.tsx";

function App() {
  const [theme, toggleTheme] = useDarkMode();
  const themeMode = theme === "light" ? lightMode : darkMode;

  return (
    <ThemeProvider theme={themeMode}>
      <AuthProvider>
        <BrowserRouter>
          <Page>
            <Routes>
              <Route
                path="/issues"
                element={<MainPage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/new"
                element={<AddIssuePage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/labels"
                element={<LabelsPage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/redirect/oauth"
                element={<OauthPage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/milestones/:state"
                element={<MilestonesPage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/issues/:filter"
                element={<MainPage toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/detail/:id"
                element={<IssueDetail toggleTheme={toggleTheme} />}
              ></Route>
              <Route
                path="/profile"
                element={<EditProfile toggleTheme={toggleTheme} />}
              ></Route>
              <Route path="" element={<LoginPage />}></Route>
              <Route path="/join" element={<JoinPage />}></Route>
            </Routes>
          </Page>
        </BrowserRouter>
      </AuthProvider>
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
