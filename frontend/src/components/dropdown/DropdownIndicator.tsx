import {styled, useTheme} from "styled-components";
import {Icon} from "../Icon";

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
  const theme = useTheme();
  const iconColor = theme.color.neutralTextDefault;

  return (
      <StyledButton onClick={onClick} disabled={disabled}>
        <span>{value}</span>
        <Icon name="chevronDown" fill={iconColor} stroke={iconColor}/>
      </StyledButton>
  );
}

const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  height: 100%;
  border-radius: 20px;
  font: ${({theme}) => theme.font.availableMedium16};
  color: ${({theme}) => theme.color.neutralTextDefault};

  &:hover {
    opacity: ${({theme}) => theme.opacity.hover};
  }

  &:active {
    opacity: ${({theme}) => theme.opacity.press};
  }

  &:disabled {
    opacity: ${({theme}) => theme.opacity.disabled};
  }
`;
