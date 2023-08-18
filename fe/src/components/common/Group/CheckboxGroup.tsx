import { CheckboxContext } from "context/checkboxContext";
import { Fieldset } from "./Fieldset";

export default function CheckboxGroup<T>({
  children,
  values,
  onChange,
}: {
  children: React.ReactNode;
  values: Set<T>;
  onChange: (values: Set<T>) => void;
}) {
  const isChecked = (value: T) => values.has(value);
  const toggleCheck = ({ checked, value }: { checked: boolean; value: T }) => {
    if (checked) {
      onChange(values.add(value));
    } else {
      values.delete(value);
      onChange(values);
    }
  };

  return (
    <Fieldset>
      <CheckboxContext.Provider value={{ isChecked, toggleCheck }}>
        {children}
      </CheckboxContext.Provider>
    </Fieldset>
  );
}
