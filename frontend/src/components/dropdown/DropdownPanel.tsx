import { css, styled } from "styled-components";
import { DropdownOption } from "./DropdownOption";

export function DropdownPanel({
  showProfile = true,
  alignment,
  optionTitle,
  options,
}: {
  showProfile?: boolean;
  alignment: "Left" | "Right" | "Center";
  optionTitle: string;
  options: {
    name: string;
    profile?: string;
    background?: string;
    color?: "Light" | "Dark" | "Center";
    selected: boolean;
    onClick: () => void;
  }[];
}) {
  return (
    <StyledPanel $alignment={alignment}>
      <div className="dropdown__header">{optionTitle}</div>
      <ul>
        {options.map(
          ({ name, profile, background, color, selected, onClick }, index) => (
            <DropdownOption
              key={`dropdown-option-${index}`}
              showProfile={showProfile}
              profile={profile}
              background={background}
              color={color}
              selected={selected}
              onClick={onClick}
            >
              {name}
            </DropdownOption>
          ),
        )}
      </ul>
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
