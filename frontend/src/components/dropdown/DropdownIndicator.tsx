import { styled } from "styled-components";

type DropdownIndicatorProps = {
  value: string;
  disabled?: boolean;
  onClick?: () => void;
} & React.HTMLAttributes<HTMLButtonElement>;

export function DropdownIndicator({
  value,
  disabled = false,
  onClick,
}: DropdownIndicatorProps) {
  return (
    <StyledButton onClick={onClick} disabled={disabled}>
      <span>{value}</span>
      <img src="src/assets/chevronDown.svg" alt="드롭다운 열기" />
    </StyledButton>
  );
}

const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
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
