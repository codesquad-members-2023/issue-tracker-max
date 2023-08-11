import { Theme, css, useTheme } from '@emotion/react';
import { border, font, radius } from '../../../styles/styles';

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  valid: boolean;
}

export default function UserInput({
  type,
  value,
  onChange,
  valid,
  placeholder,
  label,
}: Props) {
  const theme = useTheme();

  return (
    <div css={input(theme, value, valid)}>
      <input
        type={type}
        className="input"
        placeholder={placeholder}
        value={value}
        onChange={onChange}
      />
      {value && <label className="label">{label}</label>}
    </div>
  );
}

const input = (
  theme: Theme,
  inputValue: string | number | readonly string[] | undefined,
  valid: boolean
) => css`
  position: relative;

  .input {
    width: 320px;
    height: 56px;
    border: none;
    outline: ${valid
      ? 'none'
      : `${border.default} ${theme.danger.borderDefault}`};
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
      outline: ${valid
        ? `${border.default} ${theme.neutral.borderDefaultActive}`
        : `${border.default} ${theme.danger.borderDefault}`};
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
