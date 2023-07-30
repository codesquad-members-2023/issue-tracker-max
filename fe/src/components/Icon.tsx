import { theme } from 'styles/Theme.tsx';
import { useThemeMode } from 'contexts/ThemeModeContext';

import * as icons from './SvgIcons';

import {
  DEFAULT_SIZE,
  DEFAULT_STROKE,
  DEFAULT_FILL,
  sizes,
} from 'constants/icons';

type LightThemeColorKeys = keyof typeof theme.light.color;
type DarkThemeColorKeys = keyof typeof theme.dark.color;
type ThemeColorKeys = LightThemeColorKeys | DarkThemeColorKeys;

type IconType = {
  [K in keyof typeof icons]: React.FC<React.SVGProps<SVGSVGElement>>;
};

type IconProps = {
  icon: keyof IconType;
  fill?: ThemeColorKeys | 'none';
  stroke?: ThemeColorKeys;
  size?: 'S' | 'M' | 'L';
};

const getThemeColor = (
  mode: 'light' | 'dark',
  colorKey: ThemeColorKeys | 'none',
) => {
  return colorKey === 'none' ? colorKey : theme[mode].color[colorKey];
};

export const Icon: React.FC<IconProps> = ({
  icon,
  fill = DEFAULT_FILL,
  stroke = DEFAULT_STROKE,
  size = DEFAULT_SIZE,
}) => {
  const { mode } = useThemeMode();
  const SvgIcon = icons[icon] as React.FC<React.SVGProps<SVGSVGElement>>;
  const strokeColor = getThemeColor(mode, stroke);
  const fillColor = getThemeColor(mode, fill);
  const iconSize = sizes[size];

  return (
    <SvgIcon
      fill={fillColor}
      stroke={strokeColor}
      width={iconSize}
      height={iconSize}
    />
  );
};
