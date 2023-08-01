import { ReactComponent as Plus } from '../asset/icons/plus.svg';
import { ReactComponent as ReFresh } from '../asset/icons/refresh.svg';
import { ReactComponent as AlertCircle } from '../asset/icons/alert_circle.svg';
import { ReactComponent as ChevronDown } from '../asset/icons/chevron_down.svg';
import { ReactComponent as UserImageSmall } from '../asset/icons/user_image_small.svg';
import { ReactComponent as CheckOnCircle } from '../asset/icons/check_on_circle.svg';
import { ReactComponent as CheckOffCircle } from '../asset/icons/check_off_circle.svg';
const Icons = {
  plus: Plus,
  refresh: ReFresh,
  alertCircle: AlertCircle,
  chevronDown: ChevronDown,
  userImageSmall: UserImageSmall,
  checkOnCircle: CheckOnCircle,
  checkOffCircle: CheckOffCircle,
  default: Plus,
} as const;

export default Icons;
