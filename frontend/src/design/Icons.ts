import { ReactComponent as Plus } from '../asset/icons/plus.svg';
import { ReactComponent as ReFresh } from '../asset/icons/refresh.svg';
import { ReactComponent as AlertCircle } from '../asset/icons/alert_circle.svg';
import { ReactComponent as ChevronDown } from '../asset/icons/chevron_down.svg';
const Icons = {
  plus: Plus,
  refresh: ReFresh,
  alertCircle: AlertCircle,
  chevronDown: ChevronDown,
  default: Plus,
} as const;

export default Icons;
