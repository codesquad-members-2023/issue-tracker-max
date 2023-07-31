import { styled } from "styled-components";

export function DropdownOption({
  showProfile = true,
  profile,
  selected,
  children,
  onClick,
}: {
  showProfile?: boolean;
  profile?: string;
  selected: boolean;
  children: string;
  onClick: () => void;
}) {
  return (
    <StyledDropdownOptionList
      className={selected ? "selected" : ""}
      onClick={onClick}
    >
      {showProfile && (
        <img src={profile ? profile : "src/assets/userImageSmall.svg"} alt="" />
      )}
      <span title={children}>{children}</span>
      <img
        src={`src/assets/check${selected ? "On" : "Off"}Circle.svg`}
        alt={selected ? "선택된 옵션" : "선택되지 않은 옵션"}
      />
    </StyledDropdownOptionList>
  );
}

const StyledDropdownOptionList = styled.li`
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

  &:last-child div {
    border-radius: ${({ theme }) =>
      `0px 0px ${theme.radius.large} ${theme.radius.large}`};
  }

  &:hover {
    background-color: ${({ theme }) => theme.color.neutralSurfaceBold};
    cursor: pointer;
  }

  & span {
    flex-grow: 1;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
`;
