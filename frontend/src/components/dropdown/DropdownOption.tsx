import { styled, useTheme } from "styled-components";
import { Icon } from "../Icon";

export function DropdownOption({
  showProfile = true,
  profile,
  background,
  color = "Light",
  selected,
  children,
  onClick,
}: {
  showProfile?: boolean;
  profile?: string;
  background?: string;
  color?: "Light" | "Dark" | "Center";
  selected: boolean;
  children: string;
  onClick: () => void;
}) {
  const theme = useTheme();

  return (
    <StyledDropdownOptionList
      className={selected ? "selected" : ""}
      onClick={onClick}
    >
      {showProfile && profile ? (
        <img style={{ width: "20px" }} src={profile} alt="프로필 이미지" />
      ) : (
        <Icon
          name="userImageSmall"
          fill={background ?? theme.color.neutralSurfaceBold}
        />
      )}
      <span title={children}>{children}</span>
      <Icon name={`check${selected ? "On" : "Off"}Circle`} />
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

  &:last-child {
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
