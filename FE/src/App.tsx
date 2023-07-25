import { useDarkMode } from "./hooks/useDarkMode.ts";
import { lightMode, darkMode } from "./styles/DesignSystem.ts";
import { ThemeProvider } from "styled-components";

function App() {
  const [theme, toggleTheme] = useDarkMode();
  const themeMode = theme === "light" ? lightMode : darkMode;

  return <ThemeProvider theme={themeMode}></ThemeProvider>;
}

export default App;
