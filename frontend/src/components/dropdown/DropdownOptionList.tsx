import { styled } from "styled-components";

export function DropdownOptionList({
  showProfile = true,
  options,
  onOptionClick,
  selectedOptionIndex,
}: {
  showProfile?: boolean;
  options: {
    name: string;
    profile?: string;
    selected: boolean;
    onClick: () => void;
  }[];
  onOptionClick?: (index: number, callback: () => void) => void;
  selectedOptionIndex?: number;
}) {
  return (
    <StyledDropdownOptionList>
      {options.map(({ name, profile, onClick }, index) => (
        <li
          className={`dropdown__option ${
            selectedOptionIndex === index && "selected"
          }`}
          key={index}
          onClick={() => onOptionClick?.(index, onClick)}
        >
          <DropdownOption
            showProfile={showProfile}
            profile={profile}
            selected={selectedOptionIndex === index}
          >
            {name}
          </DropdownOption>
        </li>
      ))}
    </StyledDropdownOptionList>
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
        <img src={profile ? profile : "src/assets/userImageSmall.svg"} alt="" />
      )}
      <span title={children}>{children}</span>
      <img
        src={`src/assets/check${selected ? "On" : "Off"}Circle.svg`}
        alt={selected ? "선택된 옵션" : "선택되지 않은 옵션"}
      />
    </>
  );
}

const StyledDropdownOptionList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1px;
  width: 240px;

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
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
    }
  }
`;
