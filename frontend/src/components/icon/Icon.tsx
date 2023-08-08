import { DefaultTheme, styled, useTheme } from "styled-components";
import { designSystem } from "../../constants/designSystem";
import { fillTypeComponents, iconComponents } from "./SvgIcons";

const DefaultIconColor = "neutralTextDefault";

const getColor = (color: ThemeColorKeys | string, theme: DefaultTheme) => {
  if (/^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/.test(color)) {
    return color;
  }
  return theme.color[color as ThemeColorKeys];
};

type DarkThemeColorKeys = keyof typeof designSystem.DARK.color;
type LightThemeColorKeys = keyof typeof designSystem.LIGHT.color;
type HexColorCode = `#${string}`;
export type ThemeColorKeys = DarkThemeColorKeys | LightThemeColorKeys;
export type IconColor = ThemeColorKeys | HexColorCode;
export type IconType = {
  [K in keyof typeof iconComponents]: React.FC<React.SVGProps<SVGSVGElement>>;
};

export function Icon({
  name,
  color = DefaultIconColor,
}: {
  name: keyof IconType;
  color?: IconColor;
}) {
  const theme = useTheme();

  const IconComponent = iconComponents[name];

  if (!IconComponent) {
    return null;
  }

  const isFillType = fillTypeComponents.includes(IconComponent);
  const iconColor = getColor(color, theme);

  return (
    <IconWrapper>
      <IconComponent
        fill={isFillType ? iconColor : "none"}
        stroke={isFillType ? "none" : iconColor}
      />
    </IconWrapper>
  );
}

const IconWrapper = styled.div`
  display: flex;
  align-items: center;
`;
