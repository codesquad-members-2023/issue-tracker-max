import { styled, useTheme } from "styled-components";
import { getColorCode } from "../../utils/getColorCode";
import { fillTypeComponents, iconComponents } from "./SvgIcons";
import { Color } from "../../types/colors";

const DefaultIconColor = "neutralTextDefault";

export type IconType = {
  [K in keyof typeof iconComponents]: React.FC<React.SVGProps<SVGSVGElement>>;
};

export function Icon({
  name,
  color = DefaultIconColor,
  pointer = true,
  onClick,
}: {
  name: keyof IconType;
  color?: Color;
  pointer?: boolean;
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
    <IconWrapper $pointer={pointer} onClick={onClick}>
      <IconComponent
        fill={isFillType ? iconColor : "none"}
        stroke={isFillType ? "none" : iconColor}
      />
    </IconWrapper>
  );
}

const IconWrapper = styled.div<{ $pointer: boolean }>`
  display: flex;
  align-items: center;

  &:hover {
    cursor: ${({ $pointer }) => ($pointer ? "pointer" : "default")};
    opacity: ${({ theme }) => theme.opacity.hover};
  }
`;
