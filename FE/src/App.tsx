import { useDarkMode } from "./hooks/useDarkMode.ts";
import AddIssuePage from "./pages/AddIssuePage.tsx";
import MainPage from "./pages/MainPage.tsx";
import { lightMode, darkMode } from "./styles/DesignSystem.ts";
import { ThemeProvider } from "styled-components";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header/Header.tsx";

function App() {
  const [theme, toggleTheme] = useDarkMode();
  const themeMode = theme === "light" ? lightMode : darkMode;
  return (
    <ThemeProvider theme={themeMode}>
      <BrowserRouter>
        <Header toggleTheme={toggleTheme} />
        <Routes>
          <Route path="" element={<MainPage />}></Route>
          <Route path="/new" element={<AddIssuePage />}></Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
