import { css } from '@emotion/react';
import { color } from '../../../styles/color';
import { border, opacity, radius } from '../../../styles/object';
import { font } from '../../../styles/font';

type ButtonVariant = 'github' | 'signIn' | 'signUp';

type Props = {
  variant: ButtonVariant;
  disabled?: boolean;
};

export default function SignButton({ variant, disabled = false }: Props) {
  return (
    <button
      type={variant === 'signIn' ? 'submit' : 'button'}
      disabled={disabled}
      css={[common, variants[variant], disabled && disabledStyle]}
    >
      {variant === 'github'
        ? 'GitHub 계정으로 로그인'
        : variant === 'signIn'
        ? '이메일로 로그인'
        : '회원가입'}
    </button>
  );
}

const common = css`
  width: 320px;
  height: 56px;
  border-radius: ${radius.large};
`;

const variants = {
  github: css`
    font: ${font.availableMedium20};
    background-color: ${color.neutral.surfaceDefault};
    color: ${color.brand.textWeak};
    border: ${border.default} ${color.brand.borderDefault};
  `,
  signIn: css`
    font: ${font.availableMedium20};
    background-color: ${color.brand.surfaceDefault};
    color: ${color.brand.textDefault};
  `,
  signUp: css`
    font: ${font.availableMedium16};
    background-color: ${color.neutral.surfaceDefault};
    color: ${color.neutral.textDefault};
    border: ${border.default} ${color.neutral.borderDefault};
  `,
};

const disabledStyle = css`
  opacity: ${opacity.disabled};
  cursor: not-allowed;

  :hover {
    opacity: ${opacity.disabled};
  }
`;
