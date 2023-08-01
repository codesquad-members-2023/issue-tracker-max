import { useTheme } from '@emotion/react';

type Props = {
  value: string;
  label?: string;
  inputType?: React.HTMLInputTypeAttribute;
  placeholder?: string;
  disabled?: boolean;
  onChange: (value: string) => void;
};

export const Input: React.FC<Props> = ({
  value,
  label,
  inputType,
  placeholder,
  disabled,
  onChange,
}) => {
  const theme = useTheme() as any;

  return (
    <input
      id={label}
      type={inputType}
      value={value}
      min={inputType === 'date' ? '1000-01-01' : undefined}
      max={inputType === 'date' ? '9999-12-31' : undefined}
      placeholder={placeholder}
      disabled={disabled}
      onChange={({ target }) => onChange(target.value)}
      css={{
        width: '100%',
        border: '0',
        backgroundColor: 'transparent',
        outline: 'none',
        caretColor: theme.palette.blue,
        font: theme.fonts.displayMedium16,
        color: theme.neutral.text.strong,
      }}
    ></input>
  );
};
