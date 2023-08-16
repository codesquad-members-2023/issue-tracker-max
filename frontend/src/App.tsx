import { useState } from "react";
import {
  Navigate,
  Route,
  BrowserRouter as Router,
  Routes,
} from "react-router-dom";
import { ThemeProvider, styled } from "styled-components";
import { Header } from "./components/Header";
import { designSystem } from "./constants/designSystem";
import { Error404 } from "./page/Error404";
import { Auth } from "./page/auth/Auth";
import { OauthLoading } from "./page/auth/OauthLoading";
import { IssueDetail } from "./page/issueDetail/IssueDetail";
import { Label } from "./page/label/Label";
import { Main } from "./page/main/Main";
import { Milestone } from "./page/milestone/Milestone";
import { NewIssue } from "./page/newIssue/NewIssue";
import { getAccessToken } from "./utils/localStorage";

export default function App() {
  const [themeMode, setThemeMode] = useState<"LIGHT" | "DARK">("LIGHT");

  const changeThemeMode = () => {
    setThemeMode((prev) => (prev === "LIGHT" ? "DARK" : "LIGHT"));
  };

  return (
    <ThemeProvider theme={designSystem[themeMode]}>
      <Div>
        <Router>
          <Routes>
            <Route path="/auth" element={<AuthRoute />} />
            <Route path="/redirect/oauth" element={<OauthLoading />} />
            <Route
              path="*"
              element={<MainRoutes changeThemeMode={changeThemeMode} />}
            />
          </Routes>
        </Router>
      </Div>
    </ThemeProvider>
  );
}

function MainRoutes({ changeThemeMode }: { changeThemeMode: () => void }) {
  return (
    <PrivateRoute>
      <Header changeThemeMode={changeThemeMode} />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/issues/new" element={<NewIssue />} />
        <Route path="/issues/:issueId" element={<IssueDetail />} />
        <Route path="/label" element={<Label />} />
        <Route path="/milestone" element={<Milestone />} />
        <Route path="/milestone/:state" element={<Milestone />} />
        <Route path="*" element={<Error404 />} />
      </Routes>
    </PrivateRoute>
  );
}

function PrivateRoute({ children }: { children: React.ReactNode }) {
  const accessToken = getAccessToken();

  return accessToken ? children : <Navigate to="/auth" />;
}

function AuthRoute() {
  const accessToken = getAccessToken();

  return accessToken ? <Navigate to="/" /> : <Auth />;
}

const Div = styled.div`
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: ${({ theme }) => theme.color.neutralSurfaceDefault};
`;
