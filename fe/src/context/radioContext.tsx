import { createContext } from "react";

type RadioContextType<T> = {
  value: T;
  onChange: (value: T) => void;
};

// 실제 사용 시에는 T를 제네릭으로 받아서 사용하면 됨
export const RadioContext = createContext<RadioContextType<any> | null>(null);
