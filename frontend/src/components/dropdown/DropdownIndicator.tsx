import { styled } from "styled-components";

export default function DropdownIndicator({
  disabled,
  children,
}: {
  disabled?: boolean;
  children: string;
}) {
  return (
    <StyledButton disabled={disabled}>
      <span>{children}</span>
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
  font: ${({ theme: { font } }) => font.availableMedium16};
  color: ${({ theme: { color } }) => color.neutralTextDefault};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  span {
    width: 60px;
  }
`;
