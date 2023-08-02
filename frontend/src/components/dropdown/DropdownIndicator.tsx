import { css, styled, useTheme } from "styled-components";
import { Icon } from "../Icon";

type DropdownIndicatorProps = {
  value: string;
  type?: "Default" | "Long";
  disabled?: boolean;
  onClick?: () => void;
} & React.HTMLAttributes<HTMLButtonElement>;

export function DropdownIndicator({
  value,
  type = "Default",
  disabled = false,
  onClick,
}: DropdownIndicatorProps) {
  const theme = useTheme();
  const iconColor = theme.color.neutralTextDefault;

  return (
    <StyledButton onClick={onClick} disabled={disabled} $type={type}>
      <span>{value}</span>
      <Icon
        name={type === "Default" ? "chevronDown" : "plus"}
        fill={iconColor}
        stroke={iconColor}
      />
    </StyledButton>
  );
}

const StyledButton = styled.button<{ $type: "Default" | "Long" }>`
  ${({ $type }) =>
    $type === "Long" &&
    css`
      width: 100%;
      justify-content: space-between;
    `}
  display: flex;
  align-items: center;
  gap: 8px;
  height: 32px;
  border-radius: 20px;
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.color.neutralTextDefault};

  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }

  &:active {
    opacity: ${({ theme }) => theme.opacity.press};
  }

  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
  }
`;
