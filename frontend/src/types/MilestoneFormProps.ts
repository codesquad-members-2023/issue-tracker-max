import FormType from '../constant/FormType';
import MilestoneProps from './MilestoneProps';

type MilestoneFormProps<T extends FormType> = {
  cancel: () => void;
} & (T extends FormType.edit ? MilestoneProps : object);

export default MilestoneFormProps;
