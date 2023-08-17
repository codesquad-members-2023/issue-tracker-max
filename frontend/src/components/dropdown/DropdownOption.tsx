import { styled } from "styled-components";
import { Color } from "../../types/colors";
import { Avatar } from "../Avatar";
import { Icon } from "../icon/Icon";

type DropdownOptionProps = {
  id: number;
  iconType: "None" | "Profile" | "Palette";
  profile?: string;
  background?: Color;
  selected: boolean;
  children: string;
  onClick: () => void;
};

export function DropdownOption({
  id,
  iconType,
  profile,
  background,
  selected,
  children,
  onClick,
}: DropdownOptionProps) {
  const infoIcon = () => {
    switch (iconType) {
      case "Profile":
        return <Avatar size="S" src={profile} userId={children} />;
      case "Palette":
        return (
          <Icon
            name="UserImageSmall"
            color={background ?? "neutralSurfaceBold"}
          />
        );
      default:
        return null;
    }
  };

  return (
    <StyledDropdownOptionList
      className={selected ? "selected" : ""}
      onClick={onClick}
    >
      {id !== 0 && infoIcon()}
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
