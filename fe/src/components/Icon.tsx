import { theme } from 'styles/Theme.tsx';
import { useThemeMode } from 'contexts/ThemeModeContext';

import {
  AlertCircleIcon,
  ArchiveIcon,
  CalendarIcon,
  CheckBoxActiveIcon,
  CheckBoxDisableIcon,
  CheckBoxInitialIcon,
  CheckOffCircleIcon,
  CheckOnCircleIcon,
  ChevronDownIcon,
  EditIcon,
  LabelIcon,
  MilestoneIcon,
  PaperclipIcon,
  PlusIcon,
  RefreshCcwIcon,
  SearchIcon,
  SmileIcon,
  TrashIcon,
  UserImageLargeIcon,
  UserImageSmallIcon,
  XSquareIcon,
  DarkIcon,
  LightIcon,
} from './SvgIcons';

type LightColorKeys = keyof typeof theme.light.color;
type DarkColorKeys = keyof typeof theme.dark.color;
type ColorKeys = LightColorKeys | DarkColorKeys;

type IconProps = {
  icon: keyof typeof icons;
  fill?: ColorKeys | 'none';
  stroke?: ColorKeys;
  size?: 'S' | 'M' | 'L';
};

const DEFAULT_SIZE = 'M';
const DEFAULT_STROKE = 'nuetralTextDefault';
const DEFAULT_FILL = 'none';

const icons = {
  alertCircle: AlertCircleIcon,
  archive: ArchiveIcon,
  calendar: CalendarIcon,
  checkBoxActive: CheckBoxActiveIcon,
  checkBoxDisable: CheckBoxDisableIcon,
  checkBoxInitial: CheckBoxInitialIcon,
  checkOffCircle: CheckOffCircleIcon,
  checkOnCircle: CheckOnCircleIcon,
  chevronDown: ChevronDownIcon,
  edit: EditIcon,
  label: LabelIcon,
  milestone: MilestoneIcon,
  paperclip: PaperclipIcon,
  plus: PlusIcon,
  refreshCcw: RefreshCcwIcon,
  search: SearchIcon,
  smile: SmileIcon,
  trash: TrashIcon,
  userImageLargeIcon: UserImageLargeIcon,
  userImageSmallIcon: UserImageSmallIcon,
  xSquare: XSquareIcon,
  dark: DarkIcon,
  light: LightIcon,
};

const sizes = {
  S: '16',
  M: '16',
  L: '24',
};

function getThemeColor(mode: 'light' | 'dark', colorKey: ColorKeys | 'none') {
  return colorKey === 'none' ? colorKey : theme[mode].color[colorKey];
}

export const Icon: React.FC<IconProps> = ({
  icon,
  fill = DEFAULT_FILL,
  stroke = DEFAULT_STROKE,
  size = DEFAULT_SIZE,
}) => {
  const { mode } = useThemeMode();
  const SvgIcon = icons[icon];
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
