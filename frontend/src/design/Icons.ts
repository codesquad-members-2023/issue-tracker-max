import { ReactComponent as Plus } from '../asset/icons/plus.svg';
import { ReactComponent as ReFresh } from '../asset/icons/refresh.svg';
import { ReactComponent as AlertCircle } from '../asset/icons/alert_circle.svg';
const Icons = {
  plus: Plus,
  refresh: ReFresh,
  alertCircle: AlertCircle,
  default: Plus,
} as const;

export default Icons;
