import AuthPage from "@pages/Auth/AuthPage";
import LoginPage from "@pages/Auth/LoginPage";
import SignupPage from "@pages/Auth/SignupPage";
import IssuesPage from "@pages/MainPage/IssuesPage";
import MainPage from "@pages/MainPage/MainPage";
import GlobalStyle from "@styles/GlobalStyle";
import { darkMode, lightMode } from "@styles/designSystem";
import { useState } from "react";
import {
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ThemeProvider } from "styled-components";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/auth" element={<AuthPage />}>
        <Route index element={<LoginPage />} />
        <Route path="signup" element={<SignupPage />} />
      </Route>

      <Route path="/" element={<MainPage />}>
        <Route path="issues" element={<IssuesPage />} />
      </Route>
    </>
  )
);

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const toggleThemeMode = () => {
    setThemeMode((prev) => {
      return prev === "light" ? "dark" : "light";
    });
  };

  return (
    <ThemeProvider theme={themeMode === "light" ? lightMode : darkMode}>
      <GlobalStyle />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}
