import { useState } from "react";
import { styled } from "styled-components";
import { DropdownIndicator } from "./DropdownIndicator";
import { DropdownPanel } from "./DropdownPanel";

export function DropdownContainer({
  name,
  optionTitle,
  options,
  showProfile = true,
  type = "Default",
  alignment,
  disabled = false,
}: {
  name: string;
  optionTitle: string;
  options: {
    name: string;
    profile?: string;
    background?: string;
    color?: "Light" | "Dark";
    selected: boolean;
    onClick: () => void;
  }[];
  showProfile?: boolean;
  type?: "Default" | "Long";
  alignment: "Left" | "Right" | "Center";
  disabled?: boolean;
}) {
  const [isPanelOpened, setIsPanelOpened] = useState(false);

  const openPanel = () => {
    setIsPanelOpened(true);
  };

  const closePanel = () => {
    setIsPanelOpened(false);
  };

  return (
    <StyledContainer $type={type}>
      <DropdownIndicator
        value={name}
        onClick={openPanel}
        disabled={disabled}
        type={type}
      />
      {isPanelOpened && (
        <>
          <div className="dropdown__dim" onClick={closePanel}></div>
          <DropdownPanel
            optionTitle={optionTitle}
            showProfile={showProfile}
            alignment={alignment}
            options={options}
          />
        </>
      )}
    </StyledContainer>
  );
}

const StyledContainer = styled.div<{ $type: "Default" | "Long" }>`
  width: ${({ $type }) => ($type === "Default" ? "fit-content" : "100%")};
  position: relative;

  & .dropdown__dim {
    position: fixed;
    content: "";
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 50;
  }
`;
