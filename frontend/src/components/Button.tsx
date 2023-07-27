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
    <ButtonComponent
      $size={size}
      $flexible={flexible}
      $selected={selected}
      {...props}
    >
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
  width: ${({ $size, $flexible }) => {
    if ($flexible === "flexible") {
      return "fit-content";
    }
    switch ($size) {
      case "large":
        return "240px";
      case "medium":
        return "184px";
      case "small":
        return "128px";
      default:
        return "";
    }
  }};
  height: ${({ $size }) => {
    switch ($size) {
      case "large":
        return "56px";
      case "medium":
        return "48px";
      case "small":
        return "40px";
      default:
        return "";
    }
  }};
  padding: ${({ $flexible }) =>
    $flexible === "flexible" ? "0 24px 0 24px" : ""};
  border-radius: ${({ theme, $size }) =>
    $size === "large" ? theme.radius.large : theme.radius.medium};
  font: ${({ theme, $size }) => {
    switch ($size) {
      case "large":
        return theme.font.availableMedium20;
      case "medium":
        return theme.font.availableMedium16;
      case "small":
        return theme.font.availableMedium12;
      default:
        return "";
    }
  }};
  opacity: ${({ theme }) => theme.opacity.default};

  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }

  &:active {
    opacity: ${({ theme }) => theme.opacity.press};
  }

  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
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
  background-color: ${({ theme }) => theme.color.brandSurfaceDefault};
  color: ${({ theme }) => theme.color.brandTextDefault};

  img {
    filter: ${({ theme }) => theme.iconFilter.brandTextDefault};
  }
`;

const OutlineButton = styled(StyledButton)`
  border: ${({ theme }) =>
    theme.border.default + theme.color.brandBorderDefault};
  color: ${({ theme }) => theme.color.brandTextWeak};

  img {
    filter: ${({ theme }) => theme.iconFilter.brandTextWeak};
  }
`;

const GhostButton = styled(StyledButton)<{ $selected?: boolean }>`
  font: ${({ theme, $size, $selected }) => {
    switch ($size) {
      case "large":
        return $selected
          ? theme.font.selectedBold20
          : theme.font.availableMedium20;
      case "medium":
        return $selected
          ? theme.font.selectedBold16
          : theme.font.availableMedium16;
      case "small":
        return $selected
          ? theme.font.selectedBold12
          : theme.font.availableMedium12;
      default:
        return "";
    }
  }};
  color: ${({ theme, $selected }) =>
    $selected ? theme.color.neutralTextStrong : theme.color.neutralTextDefault};

  img {
    filter: ${({ theme, $selected }) =>
      $selected
        ? theme.iconFilter.neutralTextStrong
        : theme.iconFilter.neutralTextDefault};
  }
`;
