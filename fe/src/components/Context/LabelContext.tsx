import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useState,
} from 'react';

type LabelProviderProps = {
  children: ReactNode;
};

export const LabelContext = createContext<{
  title: string;
  description: string;
  backgroundColor: string;
  textColor: string;
  setTitle: Dispatch<SetStateAction<string>>;
  setDescription: Dispatch<SetStateAction<string>>;
  setBackgroundColor: Dispatch<SetStateAction<string>>;
  onSelectTextColor: (color: string) => void;
} | null>(null);

export function LabelProvider({ children }: LabelProviderProps) {
  const [title, setTitle] = useState('');
  const [textColor, setTextColor] = useState('white');
  const [backgroundColor, setBackgroundColor] = useState('#000000');
  const [description, setDescription] = useState('');

  const onSelectTextColor = (color: string) => {
    switch (color) {
      case 'white':
        setTextColor('white');
        break;
      case 'black':
        setTextColor('black');
        break;
      default:
        break;
    }
  };

  return (
    <LabelContext.Provider
      value={{
        title,
        description,
        backgroundColor,
        textColor,
        setTitle,
        setDescription,
        setBackgroundColor,
        onSelectTextColor,
      }}
    >
      {children}
    </LabelContext.Provider>
  );
}
