import { ThemeProvider } from "styled-components";
import { designSystem } from "./constants/designSystem";
import DropdownIndicator from "./components/dropdown/DropdownIndicator";
import { useState } from "react";
import DropdownPanel from "./components/dropdown/DropdownPanel";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  return (
    <ThemeProvider theme={designSystem[themeMode]}>
      <div>hello world</div>
      <DropdownIndicator>Button</DropdownIndicator>
      <DropdownIndicator disabled>Button</DropdownIndicator>
      <DropdownPanel />
    </ThemeProvider>
  );
}
