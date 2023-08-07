import { Theme, css, useTheme } from '@emotion/react';
import SignButton from './common/SignButton';
import UserInput from './common/UserInput';
import { Link } from 'react-router-dom';
import { ReactComponent as LargeLogo } from '/src/assets/logo/largeLogo.svg';
import { font } from '../../styles/styles';
import CheckValidInfo from './common/CheckValidInfo';

export default function SignIn() {
  const theme = useTheme();

  return (
    <form action="/" method="POST" onSubmit={() => {}} css={signInForm(theme)}>
      <Link to={'/sign-in'}>
        <LargeLogo className="logo" />
      </Link>
      <div className="form-container">
        <SignButton
          type="button"
          value="GitHub 계정으로 로그인"
          buttonType="github"
        ></SignButton>
        <span className="or">or</span>
        <div>
          <UserInput
            type="email"
            placeholder="example@gmail.com"
            label="이메일"
          />
          <CheckValidInfo valid={false}>
            올바른 이메일을 입력해주세요
          </CheckValidInfo>
          <UserInput
            type="password"
            placeholder="영어, 숫자를 포함한 6~12자"
            label="비밀번호"
          />
          <CheckValidInfo valid={false}>
            올바른 비밀번호를 입력해주세요
          </CheckValidInfo>
        </div>
        <SignButton
          type="submit"
          value="로그인"
          disabled
          buttonType="signIn"
        ></SignButton>
        <Link to={'/sign-up'}>
          <SignButton
            type="button"
            value="회원가입"
            buttonType="signUp"
          ></SignButton>
        </Link>
      </div>
    </form>
  );
}

const signInForm = (theme: Theme) => css`
  width: 400px;
  height: 100vh;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;

  .logo path {
    fill: ${theme.neutral.textStrong};
    stroke: none;
  }

  .form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
    margin-top: 30px;
  }

  .or {
    font: ${font.displayMedium16};
    color: ${theme.neutral.textWeak};
  }
`;
