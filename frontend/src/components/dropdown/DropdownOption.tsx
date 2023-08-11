import { styled } from "styled-components";
import { Icon, IconColor } from "../icon/Icon";

export function DropdownOption({
  showProfile = true,
  profile,
  background,
  selected,
  children,
  onClick,
}: {
  showProfile?: boolean;
  profile?: string;
  background?: IconColor;
  selected: boolean;
  children: string;
  onClick: () => void;
}) {
  return (
    <StyledDropdownOptionList
      className={selected ? "selected" : ""}
      onClick={onClick}
    >
      {showProfile && profile ? (
        <img style={{ width: "20px" }} src={profile} alt="프로필 이미지" />
      ) : (
        <Icon
          name="UserImageSmall"
          color={background ?? "neutralSurfaceBold"}
        />
      )}
      <span title={children}>{children}</span>
      <Icon name={selected ? "CheckOnCircle" : "CheckOffCircle"} />
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
