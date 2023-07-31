import { ReactElement, cloneElement, useState } from "react";
import { styled } from "styled-components";

export function DropdownPanel({
  alignment,
  children,
}: {
  alignment: "Left" | "Right";
  children: ReactElement;
}) {
  const [selectedOptionIndex, setSelectedOptionIndex] = useState(-1);

  const onOptionClick = (index: number, callback: () => void) => {
    callback();
    setSelectedOptionIndex(index);
  };

  return (
    <StyledPanel $alignment={alignment}>
      <div className="dropdown__header">헤더</div>
      {cloneElement(children, { onOptionClick, selectedOptionIndex })}
    </StyledPanel>
  );
}

const StyledPanel = styled.div<{ $alignment: "Left" | "Right" }>`
  position: absolute;
  left: ${({ $alignment }) => ($alignment === "Left" ? "0" : "auto")};
  right: ${({ $alignment }) => ($alignment === "Right" ? "0" : "auto")};
  z-index: 100;

  display: flex;
  flex-direction: column;
  gap: 1px;
  width: 240px;
  border: ${({ theme }) =>
    theme.border.default + theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme }) => theme.color.neutralBorderDefault};

  & .dropdown__header {
    border-radius: ${({ theme }) =>
      `${theme.radius.large} ${theme.radius.large} 0px 0px`};
    padding: 8px 16px;
    background-color: ${({ theme }) => theme.color.neutralSurfaceDefault};
    font: ${({ theme }) => theme.font.displayMedium12};
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;
