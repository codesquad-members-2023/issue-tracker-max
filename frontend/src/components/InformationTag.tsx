import { DefaultTheme, styled } from "styled-components";
import { Color } from "../types/colors";
import { getColorCode } from "../utils/getColorCode";
import { Icon, IconType } from "./icon/Icon";

export function InformationTag({
  value,
  size,
  toolTip = "",
  icon,
  fill,
  stroke,
  fontColor = "DARK",
}: {
  value: string;
  size: "M" | "S";
  toolTip?: string;
  icon?: keyof IconType;
  fill?: Color;
  stroke?: "Default" | "DefaultActive";
  fontColor?: "LIGHT" | "DARK";
}) {
  const iconColor =
    fontColor === "DARK" ? "neutralTextWeak" : "brandTextDefault";

  return (
    <StyledInformationTag
      data-title={/^\s*$/.test(toolTip) ? undefined : toolTip}
      $size={size}
      $fill={fill}
      $stroke={stroke}
      $darkFont={fontColor === "DARK"}
    >
      {icon && <Icon name={icon} color={iconColor} />}
      <span>{value}</span>
    </StyledInformationTag>
  );
}

const getBorderColor = (
  hexColor: string | undefined,
  theme: DefaultTheme,
): string => {
  if (!hexColor || hexColor.length !== 7 || !hexColor.startsWith("#")) {
    return "transparent";
  }

  hexColor = hexColor.replace("#", "");

  const r = parseInt(hexColor.substring(0, 2), 16);
  const g = parseInt(hexColor.substring(2, 4), 16);
  const b = parseInt(hexColor.substring(4, 6), 16);
  const brightness = Math.round((r * 299 + g * 587 + b * 114) / 1000);

  return brightness > 200 && brightness <= 255
    ? theme.color.neutralBorderDefault
    : "transparent";
};

const StyledInformationTag = styled.div<{
  $size: "M" | "S";
  $fill?: string;
  $stroke?: "Default" | "DefaultActive";
  $darkFont: boolean;
}>`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: fit-content;
  height: ${({ $size }) => ($size === "M" ? "32px" : "24px")};
  padding: ${({ $size }) => ($size === "M" ? "0 16px" : "0 8px")};
  border: 1px solid
    ${({ theme, $stroke, $fill }) =>
      $stroke && theme.color[`neutralBorder${$stroke}`]
        ? theme.color[`neutralBorder${$stroke}`]
        : getBorderColor($fill, theme)};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme, $fill }) =>
    $fill ? getColorCode($fill, theme) : theme.color.neutralSurfaceStrong};
  color: ${({ theme, $darkFont }) =>
    $darkFont ? theme.color.neutralTextWeak : theme.color.brandTextDefault};
  font: ${({ theme }) => theme.font.displayMedium12};

  &[data-title] {
    position: relative;
  }

  &[data-title]::after {
    content: attr(data-title);
    position: absolute;
    left: 50%;
    transform: translate(-50%, 100%);
    padding: 4px 8px;
    border-radius: 4px;
    white-space: nowrap;
    background-color: ${({ theme }) => theme.color.neutralTextStrong};
    color: ${({ theme }) => theme.color.neutralSurfaceStrong};
    z-index: 9999;
    opacity: 0;
    visibility: hidden;
  }

  &[data-title]:hover::after {
    opacity: 1;
    visibility: visible;
    transition: all 0.1s ease 0.5s;
  }

  span {
    padding: 0px 4px;
  }
`;
