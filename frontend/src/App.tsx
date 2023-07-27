import { useState } from "react";
import { ThemeProvider } from "styled-components";
import DropdownContainer from "./components/dropdown/DropdownContainer";
import { designSystem } from "./constants/designSystem";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  return (
    <ThemeProvider theme={designSystem[themeMode]}>
      <div>hello world</div>
      <DropdownContainer />
    </ThemeProvider>
  );
}
