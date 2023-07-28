import { styled } from "styled-components";

export function DropdownIndicator({
  value,
  disabled = false,
  onClick,
}: {
  value: string;
  disabled?: boolean;
  onClick?: () => void;
} & React.HTMLAttributes<HTMLButtonElement>) {
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
  width: 80px;
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

  span {
    width: 60px;
  }
`;
