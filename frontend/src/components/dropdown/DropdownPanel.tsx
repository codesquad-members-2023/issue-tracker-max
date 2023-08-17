import { css, styled } from "styled-components";
import { Color } from "../../types/colors";
import { Icon } from "../icon/Icon";
import { DropdownOption } from "./DropdownOption";

type DropdownPanelProps = {
  alignment: "Left" | "Right" | "Center";
  iconType: "None" | "Profile" | "Palette";
  optionTitle: string;
  options: {
    id: number;
    name: string;
    profile?: string;
    background?: Color;
    selected: boolean;
    onClick: () => void;
  }[];
  onOptionClick?: () => void;
}

export function DropdownPanel({
  alignment,
  iconType,
  optionTitle,
  options,
  onOptionClick,
}: DropdownPanelProps) {
  const renderOptions = () => {
    if (options.length === 0) {
      return (
        <EmptyOption>
          <Icon name="AlertCircle" color="neutralTextDefault" />
          No Options
        </EmptyOption>
      );
    }
    return options.map(
      ({ id, name, profile, background, selected, onClick }, index) => (
        <DropdownOption
          key={`dropdown-option-${index}`}
          id={id}
          iconType={iconType}
          profile={profile}
          background={background}
          selected={selected}
          onClick={() => {
            onClick();
            onOptionClick?.();
          }}
        >
          {name}
        </DropdownOption>
      ),
    );
  };

  return (
    <StyledPanel $alignment={alignment}>
      <div className="dropdown__header">{optionTitle}</div>
      <ul>{renderOptions()}</ul>
    </StyledPanel>
  );
}

const StyledPanel = styled.div<{ $alignment: "Left" | "Right" | "Center" }>`
  position: absolute;
  z-index: 100;
  ${({ $alignment }) => {
    switch ($alignment) {
      case "Left":
        return css`
          left: 0;
        `;
      case "Right":
        return css`
          right: 0;
        `;
      case "Center":
        return css`
          left: 50%;
          transform: translateX(-50%);
        `;
      default:
        return css``;
    }
  }}

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

  & ul {
    display: flex;
    flex-direction: column;
    gap: 1px;
    border-radius: 0 0
      ${({ theme }) => `${theme.radius.large} ${theme.radius.large}`};
    background-color: ${({ theme }) => theme.color.neutralBorderDefault};
  }
`;

const EmptyOption = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border-radius: ${({ theme }) =>
    `0px 0px ${theme.radius.large} ${theme.radius.large}`};
  font: ${({ theme }) => theme.font.availableMedium16};
  font-style: italic;
  background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};
  color: ${({ theme }) => theme.color.neutralTextDefault};
`;
