import { css } from '@emotion/react';
import { border, radius } from '../../../styles/object';
import { color } from '../../../styles/color';
import { font } from '../../../styles/font';
import { useState } from 'react';
import CheckValidInfo from './CheckValidInfo';

type InputVariant = 'email' | 'userName' | 'password' | 'doubleCheck';

type Props = {
  variant: InputVariant;
  value?: string;
};

export default function UserInput({ variant, value }: Props) {
  const [inputValue, setInputValue] = useState(value);
  const withLabelStyle = inputValue
    ? css`
        padding: 16px 16px 0;
      `
    : css`
        padding: 0 16px;
      `;

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  return (
    <div
      css={css`
        position: relative;
      `}
    >
      <input
        type={variant}
        css={[userInput, withLabelStyle]}
        placeholder={
          variant === 'email'
            ? 'example@gmail.com'
            : variant === 'userName'
            ? '닉네임'
            : variant === 'password'
            ? '영어, 숫자를 포함한 6~12자'
            : '비밀번호를 한번 더 입력하세요'
        }
        value={inputValue}
        onChange={onChange}
      />
      <CheckValidInfo variant={variant} valid={false}></CheckValidInfo>
      {inputValue && (
        <label css={inputLabel}>
          {variant === 'email'
            ? '이메일'
            : variant === 'userName'
            ? '닉네임'
            : variant === 'password'
            ? '비밀번호'
            : '비밀번호 확인'}
        </label>
      )}
    </div>
  );
}

const userInput = css`
  width: 320px;
  height: 56px;
  border: none;
  border-radius: ${radius.large};
  background-color: ${color.neutral.surfaceBold};
  color: ${color.neutral.textDefault};
  font: ${font.displayMedium16};

  ::placeholder {
    color: ${color.neutral.textWeak};
  }

  :hover,
  :focus {
    outline: ${border.default} ${color.neutral.borderDefaultActive};
  }

  :focus {
    background-color: ${color.neutral.surfaceStrong};
  }
`;

const inputLabel = css`
  position: absolute;
  top: 12px;
  left: 16px;
  font: ${font.displayMedium12};
  color: ${color.neutral.textWeak};
`;
