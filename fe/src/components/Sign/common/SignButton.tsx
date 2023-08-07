import { Theme, css, useTheme } from '@emotion/react';
import { border, font, opacity, radius } from '../../../styles/styles';

type ButtonType = 'github' | 'signIn' | 'signUp';

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  buttonType: ButtonType;
}

export default function SignButton({
  type,
  value,
  disabled = false,
  buttonType,
}: Props) {
  const theme = useTheme();

  return (
    <button
      type={type}
      disabled={disabled}
      css={[
        button,
        buttonWithType(theme, buttonType),
        disabled && disabledStyle,
      ]}
    >
      {value}
    </button>
  );
}

const button = css`
  width: 320px;
  height: 56px;
  border-radius: ${radius.large};
`;

const buttonWithType = (theme: Theme, buttonType: ButtonType) => {
  const githubButton = css`
    font: ${font.availableMedium20};
    background-color: ${theme.neutral.surfaceDefault};
    color: ${theme.brand.textWeak};
    border: ${border.default} ${theme.brand.borderDefault};
  `;

  const signInButton = css`
    font: ${font.availableMedium20};
    background-color: ${theme.brand.surfaceDefault};
    color: ${theme.brand.textDefault};
  `;

  const signUpButton = css`
    font: ${font.availableMedium16};
    background-color: ${theme.neutral.surfaceDefault};
    color: ${theme.neutral.textDefault};
    border: ${border.default} ${theme.neutral.borderDefault};
  `;

  switch (buttonType) {
    case 'github':
      return githubButton;
    case 'signIn':
      return signInButton;
    case 'signUp':
      return signUpButton;
    default:
      return;
  }
};

const disabledStyle = css`
  opacity: ${opacity.disabled};
  cursor: not-allowed;

  :hover {
    opacity: ${opacity.disabled};
  }
`;
