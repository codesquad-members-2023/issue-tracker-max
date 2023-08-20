import { useState } from "react";
import { styled } from "styled-components";
import { Color } from "../../types/colors";
import { DropdownIndicator } from "./DropdownIndicator";
import { DropdownPanel } from "./DropdownPanel";

type DropdownContainerProps = {
  name: string;
  optionTitle: string;
  options: {
    id: number;
    name: string;
    profile?: string;
    background?: Color;
    selected: boolean;
    onClick: () => void;
  }[];
  iconType?: "None" | "Profile" | "Palette";
  type?: "Default" | "Long";
  alignment: "Left" | "Right" | "Center";
  disabled?: boolean;
  autoClose?: boolean;
  onDimClick?: () => void;
};

export function DropdownContainer({
  name,
  optionTitle,
  options,
  type = "Default",
  iconType = "None",
  alignment,
  disabled = false,
  autoClose = false,
  onDimClick,
}: DropdownContainerProps) {
  const [isPanelOpened, setIsPanelOpened] = useState(false);

  const openPanel = () => {
    setIsPanelOpened(true);
  };

  const closePanel = () => {
    onDimClick?.();
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
            iconType={iconType}
            alignment={alignment}
            options={options}
            onOptionClick={autoClose ? closePanel : undefined}
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
