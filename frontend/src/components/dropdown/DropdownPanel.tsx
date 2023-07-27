import { useState } from "react";
import { styled } from "styled-components";

export default function DropdownPanel({
  alignment,
}: {
  alignment: "left" | "right";
}) {
  const [selectedOptionIndex, setSelectedOptionIndex] = useState(-1);

  return (
    <StyledPanel $alignment={alignment}>
      <div className="dropdown__header">헤더</div>
      <ul className="dropdown__option-container">
        {["option1", "option2", "option3"].map((option, index) => (
          <li
            className={`dropdown__option ${
              selectedOptionIndex === index && "selected"
            }`}
            key={index}
            onClick={() => setSelectedOptionIndex(index)}
          >
            <DropdownOption selected={selectedOptionIndex === index}>
              {option}
            </DropdownOption>
          </li>
        ))}
      </ul>
    </StyledPanel>
  );
}

function DropdownOption({
  showProfile = true,
  profile,
  selected,
  children,
}: {
  showProfile?: boolean;
  profile?: string;
  selected: boolean;
  children: string;
}) {
  return (
    <>
      {showProfile && (
        <img src={profile ?? "src/assets/userImageSmall.svg"} alt="" />
      )}
      <span>{children}</span>
      <img
        src={`src/assets/check${selected ? "On" : "Off"}Circle.svg`}
        alt={selected ? "선택된 옵션" : "선택되지 않은 옵션"}
      />
    </>
  );
}

const StyledPanel = styled.div<{ $alignment: "left" | "right" }>`
  position: absolute;
  left: ${({ $alignment }) => ($alignment === "left" ? "0" : "auto")};
  right: ${({ $alignment }) => ($alignment === "right" ? "0" : "auto")};
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

  & .dropdown__option {
    box-sizing: border-box;
    display: flex;
    align-items: center;
    gap: 8px;
    height: 40px;
    padding: 8px 16px;
    background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};
    font: ${({ theme }) => theme.font.availableMedium16};
    color: ${({ theme }) => theme.color.neutralTextDefault};

    &.selected {
      font: ${({ theme }) => theme.font.selectedBold16};
      color: ${({ theme }) => theme.color.neutralTextStrong};
    }

    &:hover {
      background-color: ${({ theme }) => theme.color.neutralSurfaceBold};
      cursor: pointer;
    }

    &:last-child {
      border-radius: ${({ theme }) =>
        `0px 0px ${theme.radius.large} ${theme.radius.large}`};
    }

    & span {
      flex-grow: 1;
    }
  }
`;
