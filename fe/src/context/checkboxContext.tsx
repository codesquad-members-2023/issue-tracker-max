import { createContext } from "react";

type CheckboxContextType<T> = {
  isChecked: (value: T) => boolean;
  toggleCheck: ({ checked, value }: { checked: boolean; value: T }) => void;
};

// 실제 사용 시에는 T를 제네릭으로 받아서 사용하면 됨
export const CheckboxContext = createContext<CheckboxContextType<any> | null>(
  null
);
