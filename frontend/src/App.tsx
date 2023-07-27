import { useState } from "react";
import { ThemeProvider } from "styled-components";
import Button from "./components/Button";
import { designSystem } from "./constants/designSystem";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const onClick = () => {
    setThemeMode(themeMode === "light" ? "dark" : "light");
  };

  return (
    <ThemeProvider theme={designSystem[themeMode]}>
      <div>hello world</div>
      <Button
        icon="alertCircle"
        size="medium"
        buttonType="container"
        selected
        onClick={onClick}
      >
        모드 변경
      </Button>
      <Button
        icon="alertCircle"
        size="medium"
        buttonType="outline"
        selected
        onClick={onClick}
      >
        모드 변경
      </Button>
      <Button
        icon="alertCircle"
        size="medium"
        buttonType="ghost"
        selected
        onClick={onClick}
      >
        모드 변경
      </Button>
    </ThemeProvider>
  );
}
