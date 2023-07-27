import { styled } from "styled-components";

export default function DropdownIndicator({
  children,
  disabled,
  onClick,
}: {
  children: string;
  disabled?: boolean;
  onClick?: () => void;
}) {
  return (
    <StyledButton {...{ disabled, onClick }}>
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
