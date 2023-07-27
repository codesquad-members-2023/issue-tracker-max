import { ButtonHTMLAttributes } from "react";
import { styled } from "styled-components";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  size: "small" | "medium" | "large";
  buttonType: "container" | "outline" | "ghost";
  flexible?: "flexible" | "fixed";
  icon?: string;
  selected?: boolean;
}

export default function Button({
  size,
  buttonType,
  flexible,
  icon,
  selected,
  children,
  ...props
}: ButtonProps) {
  const buttonMap = {
    container: ConatinerButton,
    outline: OutlineButton,
    ghost: GhostButton,
  };

  const ButtonComponent = buttonMap[buttonType];

  return (
    <ButtonComponent {...{ $size: size, $flexible: flexible, $selected: selected }} {...props}>
      <div>
        {icon && <img src={`/src/assets/${icon}.svg`} alt={icon} />}
        <span>{children}</span>
      </div>
    </ButtonComponent>
  );
}

const StyledButton = styled.button<{
  $size: "small" | "medium" | "large";
  $flexible?: "flexible" | "fixed";
}>`
  width: ${({ $size, $flexible }) =>
    $flexible === "flexible"
      ? "fit-content"
      : $size === "large"
      ? "240px"
      : $size === "medium"
      ? "184px"
      : "128px"};
  height: ${({ $size }) =>
    $size === "large" ? "56px" : $size === "medium" ? "48px" : "40px"};
  padding: ${({ $flexible }) =>
    $flexible === "flexible" ? "0 24px 0 24px" : ""};
  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "large" ? radius.large : radius.medium};
  font: ${({ theme: { font }, $size }) => {
    const fontSize = $size === "large" ? 20 : $size === "medium" ? 16 : 12;

    return font[`availableMedium${fontSize}`];
  }};
  opacity: ${({ theme: { opacity } }) => opacity.default};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  div {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  img {
    padding: 4px;
  }

  span {
    padding: 0 8px 0 8px;
  }
`;

const ConatinerButton = styled(StyledButton)`
  background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
  color: ${({ theme: { color } }) => color.brandTextDefault};

  img {
    filter: ${({ theme: { iconFilter } }) => iconFilter.brandTextDefault};
  }
`;

const OutlineButton = styled(StyledButton)`
  border: ${({ theme: { border, color } }) =>
    border.default + color.brandBorderDefault};
  color: ${({ theme: { color } }) => color.brandTextWeak};

  img {
    filter: ${({ theme: { iconFilter } }) => iconFilter.brandTextWeak};
  }
`;

const GhostButton = styled(StyledButton)<{ $selected?: boolean }>`
  font: ${({ theme: { font }, $size, $selected }) => {
    const fontSize = $size === "large" ? 20 : $size === "medium" ? 16 : 12;
    const fontType = $selected ? "selectedBold" : "availableMedium";

    return font[`${fontType}${fontSize}`];
  }};
  color: ${({ theme: { color }, $selected }) =>
    $selected ? color.neutralTextStrong : color.neutralTextDefault};

  img {
    filter: ${({ theme: { iconFilter }, $selected }) =>
      $selected ? iconFilter.neutralTextStrong : iconFilter.neutralTextDefault};
  }
`;
