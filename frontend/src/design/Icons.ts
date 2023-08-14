import { ReactComponent as Plus } from '../asset/icons/plus.svg';
import { ReactComponent as ReFresh } from '../asset/icons/refresh.svg';
import { ReactComponent as AlertCircle } from '../asset/icons/alert_circle.svg';
import { ReactComponent as ChevronDown } from '../asset/icons/chevron_down.svg';
import { ReactComponent as UserImageSmall } from '../asset/icons/user_image_small.svg';
import { ReactComponent as userImageLarge } from '../asset/icons/userImageLarge.svg';
import { ReactComponent as CheckOnCircle } from '../asset/icons/check_on_circle.svg';
import { ReactComponent as CheckOffCircle } from '../asset/icons/check_off_circle.svg';
import { ReactComponent as PaperClip } from '../asset/icons/paperclip.svg';
import { ReactComponent as Edit } from '../asset/icons/edit.svg';
import { ReactComponent as Smile } from '../asset/icons/smile.svg';
import { ReactComponent as Search } from '../asset/icons/search.svg';
import { ReactComponent as Label } from '../asset/icons/label.svg';
import { ReactComponent as MileStone } from '../asset/icons/milestone.svg';
import { ReactComponent as CheckBoxInitial } from '../asset/icons/check_box/initial.svg';
import { ReactComponent as CheckBoxDisable } from '../asset/icons/check_box/disable.svg';
import { ReactComponent as CheckBoxActive } from '../asset/icons/check_box/active.svg';
import { ReactComponent as Archive } from '../asset/icons/archive.svg';
import { ReactComponent as Trash } from '../asset/icons/trash.svg';
import { ReactComponent as Calender } from '../asset/icons/calender.svg';
import CheckBox from '../constant/CheckBox';

const { initial, disable, active } = CheckBox;

const Icons = {
  plus: Plus,
  refresh: ReFresh,
  alertCircle: AlertCircle,
  chevronDown: ChevronDown,
  userImageSmall: UserImageSmall,
  userImageLarge: userImageLarge,
  checkOnCircle: CheckOnCircle,
  checkOffCircle: CheckOffCircle,
  paperClip: PaperClip,
  label: Label,
  milestone: MileStone,
  edit: Edit,
  smile: Smile,
  search: Search,
  default: Plus,
  archive: Archive,
  trash: Trash,
  calender: Calender,
  checkBox: {
    [initial]: CheckBoxInitial,
    [disable]: CheckBoxDisable,
    [active]: CheckBoxActive,
  },
} as const;

export default Icons;
