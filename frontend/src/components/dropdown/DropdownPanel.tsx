import { useState } from "react";
import { styled } from "styled-components";

export default function DropdownPanel() {
  const [selectedOptionIndex, setSelectedOptionIndex] = useState(-1);

  return (
    <StyledPanel>
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
  selected,
  children,
}: {
  showProfile?: boolean;
  selected: boolean;
  children: string;
}) {
  return (
    <>
      {showProfile && <img src="src/assets/userImageSmall.svg" alt="" />}
      <span>{children}</span>
      <img
        src={`src/assets/check${selected ? "On" : "Off"}Circle.svg`}
        alt={selected ? "선택된 옵션" : "선택되지 않은 옵션"}
      />
    </>
  );
}

const StyledPanel = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1px;
  width: 240px;
  border: ${({ theme: { border, color } }) =>
    border.default + color.neutralBorderDefault};
  border-radius: ${({ theme: { radius } }) => radius.large};

  & .dropdown__header {
    border-radius: ${({ theme: { radius } }) =>
      `${radius.large} ${radius.large} 0px 0px`};
    padding: 8px 16px;
    background-color: ${({ theme: { color } }) => color.neutralSurfaceDefault};
    font: ${({ theme: { font } }) => font.displayMedium12};
    color: ${({ theme: { color } }) => color.neutralTextWeak};
  }

  & .dropdown__option {
    box-sizing: border-box;
    display: flex;
    align-items: center;
    gap: 8px;
    height: 40px;
    padding: 8px 16px;
    background-color: ${({ theme: { color } }) => color.neutralSurfaceStrong};
    font: ${({ theme: { font } }) => font.availableMedium16};
    color: ${({ theme: { color } }) => color.neutralTextDefault};

    &.selected {
      font: ${({ theme: { font } }) => font.selectedBold16};
      color: ${({ theme: { color } }) => color.neutralTextStrong};
    }

    &:hover {
      background-color: ${({ theme: { color } }) => color.neutralSurfaceBold};
    }

    &:last-child {
      border-radius: ${({ theme: { radius } }) =>
        `0px 0px ${radius.large} ${radius.large}`};
    }

    & span {
      flex-grow: 1;
    }
  }
`;
