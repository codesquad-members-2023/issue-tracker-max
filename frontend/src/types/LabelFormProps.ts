import FormType from '../constant/FormType';
import LabelProps from './LabelProps';

type LabelFormProps<T extends FormType> = {
  cancel: () => void;
} & (T extends typeof FormType.edit ? LabelProps : undefined);

export default LabelFormProps;
