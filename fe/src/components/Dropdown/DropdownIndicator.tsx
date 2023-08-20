import chevronDown from "@assets/icon/chevronDown.svg";
import DropdownPanel from "@components/Dropdown/DropdownPanel";
import { useState } from "react";
import { styled } from "styled-components";
import { DropdownItemType, DropdownName, DropdownPanelVariant } from "./types";

export default function DropdownIndicator({
  displayName,
  dropdownPanelVariant,
  dropdownName,
  dropdownList,
  dropdownPanelPosition,
  dropdownOption = "single",
  valueType = "id",
  outsideClickHandler,
}: {
  displayName: string;
  dropdownPanelVariant: DropdownPanelVariant;
  dropdownName: DropdownName;
  dropdownList: DropdownItemType[];
  dropdownPanelPosition: "left" | "right";
  dropdownOption?: "multiple" | "single";
  valueType?: "id" | "content" | "name";
  outsideClickHandler?: () => void;
}) {
  const [isOpen, setIsOpen] = useState(false);

  const onDropdownClick = () => {
    setIsOpen((prev) => !prev);
  };

  const onOutsideClick = (e: MouseEvent) => {
    if (
      !(e.target as HTMLElement).closest(`#dropdown-indicator-${dropdownName}`)
    ) {
      setIsOpen(false);
      outsideClickHandler?.();
    }
  };

  return (
    <StyledDropdownIndicator id={`dropdown-indicator-${dropdownName}`}>
      <Button type="button" $isOpen={isOpen} onClick={onDropdownClick}>
        <span>{`${displayName} ${
          dropdownPanelVariant === "modify" ? "수정" : ""
        }`}</span>
        <img src={chevronDown} alt={`Filter by ${dropdownName}`} />
      </Button>

      {isOpen && (
        <DropdownPanel
          {...{
            variant: dropdownPanelVariant,
            dropdownOption,
            dropdownName,
            dropdownList,
            position: dropdownPanelPosition,
            valueType,
            onOutsideClick,
          }}
        />
      )}
    </StyledDropdownIndicator>
  );
}

const StyledDropdownIndicator = styled.div`
  min-width: 80px;
  height: 32px;
  position: relative;
`;

const Button = styled.button<{ $isOpen: boolean }>`
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  background: none;
  border: none;
  cursor: pointer;

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  span {
    color: ${({ theme: { neutral } }) => neutral.text.default};
    font: ${({ theme: { font } }) => font.availableMD16};
  }

  img {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
    transform: ${({ $isOpen }) => ($isOpen ? "rotate(180deg)" : "")};
  }
`;
