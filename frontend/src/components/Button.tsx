import { ButtonHTMLAttributes } from "react";
import { styled } from "styled-components";
import { getColorCode } from "../utils/getColorCode";
import { Icon, IconType } from "./icon/Icon";
import { Color, ThemeColorKeys } from "../types/colors";

type ButtonProps = {
  width?: string | number;
  height?: string | number;
  size: "S" | "M" | "L";
  buttonType: "Container" | "Outline" | "Ghost";
  flexible?: "Flexible" | "Fixed";
  icon?: keyof IconType;
  selected?: boolean;
  color?: Color;
  background?: Color;
} & ButtonHTMLAttributes<HTMLButtonElement>;

export function Button({
  width,
  height,
  size,
  buttonType,
  flexible,
  icon,
  selected,
  children,
  color,
  background,
  ...rest
}: ButtonProps) {
  const buttonMap = {
    Container: ContainerButton,
    Outline: OutlineButton,
    Ghost: GhostButton,
  };

  const iconColorMap: {
    [K in typeof buttonType]: ThemeColorKeys;
  } = {
    Container: "brandTextDefault",
    Outline: "brandTextWeak",
    Ghost: selected ? "neutralTextStrong" : "neutralTextDefault",
  };

  const ButtonComponent = buttonMap[buttonType];
  const iconColor = color || iconColorMap[buttonType];

  return (
    <ButtonComponent
      className={selected ? "selected" : ""}
      $width={width}
      $height={height}
      $size={size}
      $flexible={flexible === "Flexible"}
      $selected={selected}
      $color={color}
      $background={background}
      {...rest}
    >
      <div>
        {icon && <Icon name={icon} color={iconColor} />}
        <span>{children}</span>
      </div>
    </ButtonComponent>
  );
}

const StyledButton = styled.button<{
  $width?: string | number;
  $height?: string | number;
  $size: "S" | "M" | "L";
  $flexible?: boolean;
  $color?: string;
  $background?: string;
}>`
  width: ${({ $size, $width, $flexible }) => {
    if ($width) {
      return typeof $width === "number" ? `${$width}px` : $width;
    }
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
  height: ${({ $size, $height }) => {
    if ($height) {
      return typeof $height === "number" ? `${$height}px` : $height;
    }

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

  span {
    padding: 0 8px 0 8px;
  }
`;

const ContainerButton = styled(StyledButton)`
  background-color: ${({ theme, $background }) =>
    $background
      ? getColorCode($background, theme)
      : theme.color.brandSurfaceDefault};
  color: ${({ theme, $color }) => $color || theme.color.brandTextDefault};
`;

const OutlineButton = styled(StyledButton)`
  border: ${({ theme }) =>
    theme.border.default + theme.color.brandBorderDefault};
  color: ${({ theme, $color }) => $color || theme.color.brandTextWeak};
`;

const GhostButton = styled(StyledButton)<{ $selected?: boolean }>`
  width: fit-content;
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
  color: ${({ theme, $selected, $color }) =>
    $color ||
    ($selected
      ? theme.color.neutralTextStrong
      : theme.color.neutralTextDefault)};
`;
