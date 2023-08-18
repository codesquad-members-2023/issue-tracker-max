import { RadioContext } from "context/radioContext";
import { ReactNode } from "react";
import { Fieldset } from "./Fieldset";

export default function RadioGroup<T>({
  children,
  value,
  onChange,
}: {
  children: ReactNode;
  value: T;
  onChange: (value: T) => void;
}) {
  return (
    <Fieldset>
      <RadioContext.Provider value={{ value, onChange }}>
        {children}
      </RadioContext.Provider>
    </Fieldset>
  );
}
