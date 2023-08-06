import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';
import { useState } from 'react';

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

export default function UserInput({ type, value, placeholder, label }: Props) {
  const theme = useTheme();
  const [inputValue, setInputValue] = useState(value);

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  return (
    <div css={input(theme, inputValue)}>
      <input
        type={type}
        className="input"
        placeholder={placeholder}
        value={inputValue}
        onChange={onChange}
      />
      {inputValue && <label className="label">{label}</label>}
    </div>
  );
}

const input = (
  theme: Theme,
  inputValue: string | number | readonly string[] | undefined
) => css`
  position: relative;

  .input {
    width: 320px;
    height: 56px;
    border: none;
    border-radius: ${radius.large};
    font: ${font.displayMedium16};
    background-color: ${theme.neutral.surfaceBold};
    color: ${theme.neutral.textDefault};
    padding: ${inputValue ? '16px 16px 0' : '0 16px'};
    box-sizing: border-box;

    ::placeholder {
      color: ${theme.neutral.textWeak};
    }

    :hover,
    :focus {
      outline: ${border.default} ${theme.neutral.borderDefaultActive};
    }

    :focus {
      background-color: ${theme.neutral.surfaceStrong};
    }
  }

  .label {
    position: absolute;
    top: 12px;
    left: 16px;
    font: ${font.displayMedium12};
    color: ${theme.neutral.textWeak};
  }
`;
