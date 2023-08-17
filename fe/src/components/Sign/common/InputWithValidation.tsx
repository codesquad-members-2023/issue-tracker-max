import { css } from '@emotion/react';
import CheckValidInfo from './CheckValidInfo';
import UserInput from './UserInput';

type Props = {
  type: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
  label: string;
  valid: boolean;
  text: string;
};

export default function InputWithValidation({
  type,
  value,
  onChange,
  placeholder,
  label,
  valid,
  text,
}: Props) {
  const isValid = valid || value.length === 0;

  return (
    <div css={input}>
      <UserInput
        type={type}
        value={value}
        onChange={onChange}
        valid={isValid}
        placeholder={placeholder}
        label={label}
      />
      <CheckValidInfo valid={isValid} message={text} />
    </div>
  );
}

const input = css`
  width: 320px;
`;
