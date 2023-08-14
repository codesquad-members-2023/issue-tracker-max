import ElementType from '../constant/ElementType';
import LabelProps from './LabelProps';

const { AddButton, Assignee, Label } = ElementType;

type ElementProps<T extends ElementType> = {
  type: T;
} & (T extends typeof AddButton
  ? { text: string }
  : T extends typeof Assignee
  ? { text: string; checked: boolean }
  : T extends typeof Label
  ? { labelProps: LabelProps }
  : { text: string; progress: number });

export default ElementProps;
