import { useState } from "react";
import { ThemeProvider } from "styled-components";
import { Button } from "./components/Button";
import { TabButton } from "./components/TabButton";
import { TextInput } from "./components/TextInput";
import { DropdownContainer } from "./components/dropdown/DropdownContainer";
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
      <TabButtonTest />
      <DropdownTest />
    </ThemeProvider>
  );
}

function ButtonTest({ onClick }: { onClick: () => void }) {
  return (
    <div>
      <Button
        icon="alertCircle"
        size="L"
        buttonType="Container"
        selected
        onClick={onClick}
      >
        모드 변경
      </Button>
      <Button
        icon="alertCircle"
        size="M"
        buttonType="Outline"
        selected
        onClick={onClick}
      >
        모드 변경
      </Button>
      <Button
        icon="alertCircle"
        size="S"
        buttonType="Ghost"
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

function DropdownTest() {
  const options = [
    {
      name: "옵션1",
      profile: "",
      onClick: () => {
        console.log("옵션1 선택");
      },
    },
    {
      name: "옵션2",
      profile: "",
      onClick: () => {
        console.log("옵션2 선택");
      },
    },
    {
      name: "옵션3",
      profile: "",
      onClick: () => {
        console.log("옵션3 선택");
      },
    },
    {
      name: "옵션4",
      profile: "",
      onClick: () => {
        console.log("옵션4 선택");
      },
    },
    {
      name: "옵션5",
      profile: "",
      onClick: () => {
        console.log("옵션5 선택");
      },
    },
  ];
  return (
    <>
      <DropdownContainer name="필터" options={options} alignment="Left" />
      <DropdownContainer name="필터" options={options} alignment="Right" />
    </>
  );
}

function TabButtonTest() {
  const [tabs, setTabs] = useState([{name: "왼쪽"}, {name: "오른쪽"}]);
  const [issueStates, setIssueStates] = useState([{name: "열린 이슈", icon: "alertCircle", selected: true}, {name: "닫힌 이슈", icon: "archive"}]);

  const onTabClick = (name: string) => {
    setTabs(t => t.map(tab => tab.name === name ? {...tab, selected: true} : {...tab, selected: false}));
  }

  const onIssueStateClick = (name: string) => {
    setIssueStates(t => t.map(issueState => issueState.name === name ? {...issueState, selected: true} : {...issueState, selected: false}));
  }

  return (
    <>
      <TabButton tabs={tabs} onClick={onTabClick}/>
      <TabButton tabs={issueStates} onClick={onIssueStateClick}/>
    </>
  );
}
