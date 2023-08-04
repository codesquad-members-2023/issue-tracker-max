import { styled } from "styled-components";
import DropdownPanel from "../DropdownPanel/DropdownPanel";
import { useEffect, useState } from "react";
import { AssigneesProps } from "../../type";

type Props = {
  icon?: string;
  label: string;
  padding?: string;
  width?: string;
  height?: string;
  hasDropdown?: boolean;
};

export default function DropdownIndicator({
  icon = "chevronDown",
  label,
  padding = "4px 0px",
  width = "80px",
  height = "32px",
  hasDropdown = false,
}: Props) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [assigneesData, setAssigneesData] = useState<AssigneesProps>();

  const openDropdown = () => {
    setIsOpen(true);
  };

  const closeDropdown = () => {
    setIsOpen(false);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          "http://3.34.141.196/api/issues/assignees",
        );
        const data = await response.json();
        setAssigneesData(data);
      } catch (error) {
        console.log("error");
      }
    };

    fetchData();
  }, []);

  return (
    <IndicatorButton
      $padding={padding}
      $width={width}
      $height={height}
      onClick={openDropdown}
    >
      <IndicatorLabel>{label}</IndicatorLabel>
      <IndicatorIcon src={`/icons/${icon}.svg`} />
      {hasDropdown && isOpen && (
        <DropdownPanel
          title={label}
          assigneesList={assigneesData!}
          closeDropdown={closeDropdown}
        />
      )}
    </IndicatorButton>
  );
}

const IndicatorButton = styled.button<{
  $padding: string;
  $width: string;
  $height: string;
}>`
  position: relative;
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
