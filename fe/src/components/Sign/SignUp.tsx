import { css } from '@emotion/react';
import SignButton from './common/SignButton';
import UserInput from './common/UserInput';
import { opacity, radius } from '../../styles/object';
import { color } from '../../styles/color';
import { font } from '../../styles/font';
import Logo from '../../assets/Logo/Logo';

export default function SignUp() {
  return (
    <form action="/" method="POST" onSubmit={() => {}} css={signInForm}>
      <Logo theme="light" size="large"></Logo>

      <div
        css={css`
          margin-top: 30px;
        `}
      >
        <div
          css={css`
            position: relative;
          `}
        >
          <UserInput variant="email" />
          <button type="button" css={checkEmailButton} onClick={() => {}}>
            중복 체크
          </button>
        </div>
        <UserInput variant="userName" />
        <UserInput variant="password" />
        <UserInput variant="doubleCheck" />
      </div>
      <SignButton variant="signUp"></SignButton>
    </form>
  );
}

const signInForm = css`
  width: 400px;
  height: 100vh;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
`;

const checkEmailButton = css`
  width: 48px;
  height: 48px;
  position: absolute;
  top: 4px;
  right: 0;
  transform: translateX(-10%);
  border-radius: ${radius.medium};
  font: ${font.availableMedium12};
  color: ${color.brand.textDefault};
  background-color: ${color.brand.surfaceDefault};

  &:hover {
    opacity: ${opacity.hover};
  }
`;
