import { useState } from "react";
import { styled } from "styled-components";
import DropdownPanel from "../DropdownPanel/DropdownPanel";

type Props = {
  type?: "filter" | "assignees" | "labels" | "milestones" | "authors" | "none";
  icon?: string;
  label: string;
  padding?: string;
  width?: string;
  height?: string;
  dropdownTop?: string;
  dropdownLeft?: string;
};

export default function DropdownIndicator({
  type = "filter",
  icon = "chevronDown",
  label,
  padding = "4px 0px",
  width = "80px",
  height = "32px",
  dropdownTop = "0px",
  dropdownLeft = "0px",
}: Props) {
  const [openDropdown, setOpenDropdown] = useState<boolean>(false);

  const openDropdownPanel = () => {
    setOpenDropdown(true);
  };

  const closeDropdownPanel = () => {
    setOpenDropdown(false);
  };

  return (
    <Container>
      <IndicatorButton
        $padding={padding}
        $width={width}
        $height={height}
        onClick={openDropdownPanel}
      >
        <IndicatorLabel>{label}</IndicatorLabel>
        <IndicatorIcon src={`/icons/${icon}.svg`} />
      </IndicatorButton>
      {openDropdown && type === "filter" && (
        <DropdownPanel
          top={dropdownTop}
          left={dropdownLeft}
          closeDropdown={closeDropdownPanel}
        />
      )}
      {openDropdown && type === "assignees" && (
        <DropdownPanel
          type={"assignees"}
          top={"40px"}
          left={"0px"}
          closeDropdown={closeDropdownPanel}
        />
      )}
      {openDropdown && type === "labels" && (
        <DropdownPanel
          type={"labels"}
          top={"40px"}
          left={"112px"}
          closeDropdown={closeDropdownPanel}
        />
      )}
      {openDropdown && type === "milestones" && (
        <DropdownPanel
          type={"milestones"}
          top={"40px"}
          left={"180px"}
          closeDropdown={closeDropdownPanel}
        />
      )}
      {openDropdown && type === "authors" && (
        <DropdownPanel
          type={"authors"}
          top={"40px"}
          left={"200px"}
          closeDropdown={closeDropdownPanel}
        />
      )}
    </Container>
  );
}

const IndicatorButton = styled.button<{
  $padding: string;
  $width: string;
  $height: string;
}>`
  width: ${({ $width }) => $width};
  height: ${({ $height }) => $height};
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: ${({ $padding }) => $padding && $padding};
  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }
  &:active {
    opacity: ${({ theme }) => theme.opacity.press};
  }
  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
  }
`;

const Container = styled.div``;

const IndicatorLabel = styled.span`
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  text-align: left;
`;

const IndicatorIcon = styled.img`
  width: 16px;
  height: 16px;
  filter: ${({ theme }) => theme.filter.neutral.text.default};
`;
