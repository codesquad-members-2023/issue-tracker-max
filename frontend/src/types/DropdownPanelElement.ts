import { Milestone } from '.';
import DropdownType from '../constant/DropdownType';
import Option from '../constant/Option';
import LabelProps from './LabelProps';

type DropdownPanelElement<T extends DropdownType> = {
  type: T;
  text: string;
  option: Option;
} & (T extends DropdownType.assignee
  ? { imageUrl: string }
  : T extends DropdownType.label
  ? LabelProps
  : T extends DropdownType.milestone
  ? Milestone
  : T extends DropdownType.author
  ? { imageUrl: string }
  : object);

export default DropdownPanelElement;
