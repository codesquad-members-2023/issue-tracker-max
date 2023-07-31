import { useState } from "react";
import { ThemeProvider } from "styled-components";
import { Button } from "./components/Button";
import { InformationTag } from "./components/InformationTag";
import { TabButton } from "./components/TabButton";
import { TextInput } from "./components/TextInput";
import { DropdownContainer } from "./components/dropdown/DropdownContainer";
import { DropdownIndicator } from "./components/dropdown/DropdownIndicator";
import { DropdownOptionList } from "./components/dropdown/DropdownOptionList";
import { DropdownPanel } from "./components/dropdown/DropdownPanel";
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
      <InformationTagTest />
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
        flexible="Flexible"
        selected
        onClick={onClick}
      >
        코멘트 작성 - 엄청 긴 문장이 들어갈 수도 있습니다.
      </Button>
      <Button
        icon="alertCircle"
        size="M"
        buttonType="Outline"
        selected
        onClick={onClick}
      >
        이슈 작성
      </Button>
      <Button
        icon="alertCircle"
        size="S"
        buttonType="Ghost"
        selected
        onClick={onClick}
      >
        마일스톤(2)
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
      name: "옵션1 - 긴 이름이 들어가는 경우",
      profile: "",
      selected: false,
      onClick: () => {
        console.log("옵션1 선택");
      },
    },
    {
      name: "옵션2",
      profile: "",
      selected: false,
      onClick: () => {
        console.log("옵션2 선택");
      },
    },
    {
      name: "옵션3",
      profile: "",
      selected: false,
      onClick: () => {
        console.log("옵션3 선택");
      },
    },
    {
      name: "옵션4",
      profile: "",
      selected: false,
      onClick: () => {
        console.log("옵션4 선택");
      },
    },
    {
      name: "옵션5",
      profile: "",
      selected: false,
      onClick: () => {
        console.log("옵션5 선택");
      },
    },
  ];
  return (
    <>
      <DropdownContainer>
        <DropdownIndicator value="assignee" />
        <DropdownPanel alignment="Left">
          <DropdownOptionList options={options} />
        </DropdownPanel>
      </DropdownContainer>
      <DropdownContainer>
        <DropdownIndicator value="milestones" />
        <DropdownPanel alignment="Right">
          <DropdownOptionList options={options} />
        </DropdownPanel>
      </DropdownContainer>
      <DropdownContainer>
        <DropdownIndicator value="긴 내용이 들어가는 경우에 대한 테스트" />
        <DropdownPanel alignment="Left">
          <DropdownOptionList options={options} />
        </DropdownPanel>
      </DropdownContainer>
    </>
  );
}

function TabButtonTest() {
  const [tabs, setTabs] = useState([
    { name: "milestone(3)", icon: "label", selected: false },
    { name: "milestone(2)", icon: "milestone", selected: false },
    { name: "milestone(4)", icon: "milestone", selected: false },
    { name: "milestone(5)", icon: "milestone", selected: false },
  ]);
  const [issueStates, setIssueStates] = useState([
    { name: "열린 이슈 - 긴 문장의 경우", icon: "alertCircle", selected: true },
    // { name: "닫힌 이슈", icon: "archive", selected: false },
  ]);

  const onTabClick = (name: string) => {
    setTabs((t) => t.map((tab) => ({ ...tab, selected: tab.name === name })));
  };

  const onIssueStateClick = (name: string) => {
    setIssueStates((t) =>
      t.map((issueState) => ({
        ...issueState,
        selected: issueState.name === name,
      })),
    );
  };

  return (
    <>
      <TabButton onClick={onTabClick}>
        {tabs.map(({ name, icon, selected }, index) => (
          <Button
            key={`tab-${index}`}
            icon={icon}
            size="M"
            buttonType="Ghost"
            flexible="Flexible"
            selected={selected}
          >
            {name}
          </Button>
        ))}
      </TabButton>
      <TabButton onClick={onIssueStateClick}>
        {issueStates.map(({ name, icon, selected }, index) => (
          <Button
            key={`tab-${index}`}
            icon={icon}
            size="M"
            buttonType="Ghost"
            flexible="Flexible"
            selected={selected}
          >
            {name}
          </Button>
        ))}
      </TabButton>
    </>
  );
}

function InformationTagTest() {
  return (
    <>
      <InformationTag
        value="열린 이슈"
        toolTip="Status: Open 긴 이름이 들어가는 경우"
        icon="alertCircle"
        size="M"
        fill="#7F4BFF"
        fontColor="Light"
      />
      <InformationTag
        value="작성자"
        toolTip=" "
        size="S"
        fill="#C5C"
        stroke="Default"
      />
      <InformationTag
        value="documentation"
        toolTip="문서 작업입니다."
        size="S"
        fill="#000FFF"
        fontColor="Light"
      />
    </>
  );
}
