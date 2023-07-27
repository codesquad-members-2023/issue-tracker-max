import { useState } from "react";
import { ThemeProvider } from "styled-components";
import Button from "./components/Button";
import { TextInput } from "./components/TextInput";
import { designSystem } from "./constants/designSystem";

export default function App() {
  const [themeMode, setThemeMode] = useState<"light" | "dark">("light");

  const onClick = () => {
    setThemeMode(themeMode === "light" ? "dark" : "light");
  };

  return (
    <ThemeProvider theme={designSystem[themeMode]}>
      <TextInputTest />
      <ButtonTest onClick={onClick} />
    </ThemeProvider>
  );
}

function ButtonTest({ onClick }: { onClick: () => void }) {
  return (
    <div>
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
    </div>
  );
}

function TextInputTest() {
  return (
    <div>
      <div>Enable/Active</div>
      <TextInput
        size="L"
        label="아이디"
        caption="영문, 숫자 조합 6자 이상 12자 이하"
        value="Enable/Active"
      />
      <div>Disabled</div>
      <TextInput
        size="L"
        label="아이디"
        caption="영문, 숫자 조합 6자 이상 12자 이하"
        disabled={true}
        value="Disabled"
      />
      <div>Error</div>
      <TextInput
        size="L"
        label="아이디"
        caption="영문, 숫자 조합 6자 이상 12자 이하"
        validator={(value) => /^[a-zA-Z0-9]{6,12}$/.test(value)}
        value="Error"
      />
    </div>
  );
}
