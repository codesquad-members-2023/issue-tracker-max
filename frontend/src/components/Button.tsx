import { ButtonHTMLAttributes } from "react";
import { styled } from "styled-components";

type ButtonProps = {
  size: "S" | "M" | "L";
  buttonType: "Container" | "Outline" | "Ghost";
  flexible?: "Flexible" | "Fixed";
  icon?: string;
  selected?: boolean;
};

export function Button({
  size,
  buttonType,
  flexible,
  icon,
  selected,
  children,
  ...props
}: ButtonProps & ButtonHTMLAttributes<HTMLButtonElement>) {
  const buttonMap = {
    Container: ConatinerButton,
    Outline: OutlineButton,
    Ghost: GhostButton,
  };

  const ButtonComponent = buttonMap[buttonType];

  return (
    <ButtonComponent
      $size={size}
      $flexible={flexible === "Flexible"}
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
  $size: "S" | "M" | "L";
  $flexible?: boolean;
}>`
  width: ${({ $size, $flexible }) => {
    if ($flexible) {
      return "fit-content";
    }
    switch ($size) {
      case "L":
        return "240px";
      case "M":
        return "184px";
      case "S":
        return "128px";
      default:
        return "";
    }
  }};
  height: ${({ $size }) => {
    switch ($size) {
      case "L":
        return "56px";
      case "M":
        return "48px";
      case "S":
        return "40px";
      default:
        return "";
    }
  }};
  padding: ${({ $flexible }) => ($flexible ? "0 24px 0 24px" : "")};
  border-radius: ${({ theme, $size }) =>
    $size === "L" ? theme.radius.large : theme.radius.medium};
  font: ${({ theme, $size }) => {
    switch ($size) {
      case "L":
        return theme.font.availableMedium20;
      case "M":
        return theme.font.availableMedium16;
      case "S":
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
      case "L":
        return $selected
          ? theme.font.selectedBold20
          : theme.font.availableMedium20;
      case "M":
        return $selected
          ? theme.font.selectedBold16
          : theme.font.availableMedium16;
      case "S":
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
