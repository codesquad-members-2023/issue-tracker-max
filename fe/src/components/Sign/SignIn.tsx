import { css } from '@emotion/react';
import SignButton from './common/SignButton';
import { font } from '../../styles/font';
import { color } from '../../styles/color';
import UserInput from './common/UserInput';
import { Link } from 'react-router-dom';
import Logo from '../../assets/Logo/Logo';

export default function SignIn() {
  return (
    <form action="/" method="POST" onSubmit={() => {}} css={signInForm}>
      <Logo theme="light" size="large"></Logo>
      <div
        css={css`
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          gap: 1rem;
          margin-top: 30px;
        `}
      >
        <SignButton variant="github"></SignButton>
        <span
          css={{ font: font.displayMedium16, color: color.neutral.textWeak }}
        >
          or
        </span>
        <div>
          <UserInput variant="email"></UserInput>
          <UserInput variant="password"></UserInput>
        </div>
        <SignButton variant="signIn" disabled></SignButton>
        <Link to={'/sign-up'}>
          <SignButton variant="signUp"></SignButton>
        </Link>
      </div>
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
