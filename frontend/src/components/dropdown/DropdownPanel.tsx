import { styled } from "styled-components";
import { DropdownOption } from "./DropdownOption";

export function DropdownPanel({
  showProfile = true,
  alignment,
  optionTitle,
  options,
}: {
  showProfile?: boolean;
  alignment: "Left" | "Right";
  optionTitle: string;
  options: {
    name: string;
    profile?: string;
    selected: boolean;
    onClick: () => void;
  }[];
}) {
  return (
    <StyledPanel $alignment={alignment}>
      <div className="dropdown__header">{optionTitle}</div>
      <ul>
        {options.map(({ name, profile, selected, onClick }, index) => (
          <DropdownOption
            key={`dropdown-option-${index}`}
            showProfile={showProfile}
            profile={profile}
            selected={selected}
            onClick={onClick}
          >
            {name}
          </DropdownOption>
        ))}
      </ul>
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
