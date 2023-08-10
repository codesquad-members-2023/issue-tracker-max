import { styled, useTheme } from "styled-components";
import { designSystem } from "../../constants/designSystem";
import { getColorCode } from "../../utils/getColorCode";
import { fillTypeComponents, iconComponents } from "./SvgIcons";

const DefaultIconColor = "neutralTextDefault";

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
  onClick
}: {
  name: keyof IconType;
  color?: IconColor;
  onClick?: () => void;
}) {
  const theme = useTheme();

  const IconComponent = iconComponents[name];

  if (!IconComponent) {
    return null;
  }

  const isFillType = fillTypeComponents.includes(IconComponent);
  const iconColor = getColorCode(color, theme);

  return (
    <IconWrapper onClick={onClick}>
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

  &:hover {
    cursor: pointer;
    opacity: ${({theme}) => theme.opacity.hover};
  }
`;
