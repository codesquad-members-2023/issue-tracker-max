import styled from "styled-components";
import React, { useState } from "react";
import { Icon } from "components/Common/Icon/Icon";
import { Button } from "components/Common/Button/Button";

/* Indicator 수정해야함~~ */
interface FilterBarProps {
  filterTitle: string;
  onFilterClick: (event: React.MouseEvent<HTMLDivElement>) => void;
}

export const FilterBar: React.FC<FilterBarProps> = ({
  filterTitle,
  onFilterClick,
}) => {
  const [isFocused, setIsFocused] = useState(false);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setIsFocused(false);
  };

  return (
    <>
      <FilterBarLayout $isFocused={isFocused}>
        <IndicatorBox $isFocused={isFocused} onClick={onFilterClick}>
          <Button variant="ghost" size="M">
            <p>{filterTitle}</p>
            <Icon icon="ChevronDown" />
          </Button>
        </IndicatorBox>

        <InputArea $isFocused={isFocused}>
          <Icon icon="Search" stroke="nuetralTextDefault"></Icon>
          <InputBox onFocus={handleFocus} onBlur={handleBlur} />
        </InputArea>
      </FilterBarLayout>
    </>
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
  transition: all 0.3s;
  > div {
    display: flex;
    align-items: center;
  }
`;

const IndicatorBox = styled.div<{ $isFocused: boolean }>`
  font: ${({ theme: { font } }) => font.availableM16};
  cursor: pointer;
  position: relative;
  height: 100%;
  width: 128px;
  border-right: ${({ theme: { color } }) => color.nuetralBorderDefault};
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${({ theme: { color }, $isFocused }) =>
    $isFocused ? color.nuetralSurfaceStrong : color.nuetralSurfaceDefault};
  border-right: 1px solid
    ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top-left-radius: ${({ theme: { radius } }) => radius.medium};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.medium};
  padding: 0 24px;
  button {
    justify-content: space-between;
    width: 80px;
    height: 38px;
  }
`;

const InputArea = styled.div<{ $isFocused: boolean }>`
  display: flex;
  align-items: center;
  width: 100%;
  background-color: ${({ theme: { color }, $isFocused }) =>
    $isFocused ? color.nuetralSurfaceStrong : color.nuetralSurfaceBold};
  height: 100%;
  border-top-right-radius: ${({ theme: { radius } }) => radius.medium};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.medium};
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
