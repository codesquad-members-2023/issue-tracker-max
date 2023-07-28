import { useDarkMode } from "./hooks/useDarkMode.ts";
import MainPage from "./pages/MainPage.tsx";
import { lightMode, darkMode } from "./styles/DesignSystem.ts";
import { ThemeProvider } from "styled-components";

function App() {
  const [theme, toggleTheme] = useDarkMode();
  const themeMode = theme === "light" ? lightMode : darkMode;
  return (
    <ThemeProvider theme={themeMode}>
      <MainPage toggleTheme={toggleTheme} />
    </ThemeProvider>
  );
}

export default App;
