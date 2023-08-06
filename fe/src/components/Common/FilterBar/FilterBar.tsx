import styled from "styled-components";
import React, { useState } from "react";
import { DropdownIndicator } from "components/Dropdown/DropdownIndicator";
import { Icon } from "components/Common/Icon/Icon";


/* Indicator 수정해야함~~ */
interface FilterBarProps {
  onIndicatorClick: (id: string) => void;
}

export const FilterBar: React.FC<FilterBarProps> = ({ onIndicatorClick }) => {
  const [isFocused, setIsFocused] = useState(false);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setIsFocused(false);
  };

  return (
    <FilterBarLayout $isFocused={isFocused}>
      <IndicatorBox $isFocused={isFocused}>
        <DropdownIndicator
          title="필터"
          onLabelClick={onIndicatorClick}
          id="1"
        />
      </IndicatorBox>
      <InputArea $isFocused={isFocused}>
        <Icon icon="Search" stroke="nuetralTextDefault"></Icon>
        <InputBox onFocus={handleFocus} onBlur={handleBlur} />
      </InputArea>

    </FilterBarLayout>
  );
};

const FilterBarLayout = styled.div<{ $isFocused: boolean }>`
  width: 560px;
  display: flex;
  align-items: center;
  height: 40px;
  border: 1px solid
    ${({ theme: { color }, $isFocused }) =>
      $isFocused
        ? color.nuetralBorderDefaultActive
        : color.nuetralBorderDefault};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  overflow: hidden;
  transition: all 0.3s;
`;

const IndicatorBox = styled.div<{ $isFocused: boolean }>`
  font: ${({ theme: { font } }) => font.availableM16};
  padding: 0px 24px;
  cursor: pointer;
  height: 100%;
  border-right: ${({ theme: { color } }) => color.nuetralBorderDefault};
  display: flex;
  align-items: center;
  background-color: ${({ theme: { color }, $isFocused }) =>
    $isFocused ? color.nuetralSurfaceStrong : color.nuetralSurfaceDefault};
  border-right: 1px solid
    ${({ theme: { color } }) => color.nuetralBorderDefault};
`;

const InputArea = styled.div<{ $isFocused: boolean }>`
  display: flex;
  align-items: center;
  width: 100%;
  background-color: ${({ theme: { color }, $isFocused }) =>
    $isFocused ? color.nuetralSurfaceStrong : color.nuetralSurfaceBold};
  height: 100%;
  svg {
    margin: 0 8px 0 24px;
  }
`;

const InputBox = styled.input`
  width: 100%;
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  border: none;
  outline: none;
  background-color: transparent;
`;
