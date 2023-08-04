import { Theme, css, useTheme } from '@emotion/react';
import { Link } from 'react-router-dom';
import SignButton from './common/SignButton';
import UserInput from './common/UserInput';
import { font, radius } from '../../styles/styles';
import { ReactComponent as LargeLogo } from '/src/assets/logo/largeLogo.svg';
import CheckValidInfo from './common/CheckValidInfo';

export default function SignUp() {
  const theme = useTheme();

  return (
    <form action="/" method="POST" onSubmit={() => {}} css={signInForm(theme)}>
      <Link to={'/sign-in'}>
        <LargeLogo className="logo" />
      </Link>

      <div className="form-container">
        <div className="email-input">
          <UserInput
            type="email"
            placeholder="example@gmail.com"
            label="이메일"
          />
          <CheckValidInfo valid={false}>
            올바른 이메일을 입력해주세요
          </CheckValidInfo>
          <button type="button" className="check-email" onClick={() => {}}>
            중복 체크
          </button>
        </div>
        <UserInput type="userName" placeholder="닉네임" label="닉네임" />
        <CheckValidInfo valid={false}>
          올바른 닉네임을 입력해주세요
        </CheckValidInfo>
        <UserInput
          type="password"
          placeholder="영어, 숫자를 포함한 6~12자"
          label="비밀번호"
        />
        <CheckValidInfo valid={false}>
          올바른 비밀번호를 입력해주세요
        </CheckValidInfo>
        <UserInput
          type="password"
          placeholder="비밀번호를 한번 더 입력해주세요"
          label="비밀번호 확인"
        />
        <CheckValidInfo valid={false}>
          비밀번호를 한번 더 입력해주세요
        </CheckValidInfo>
      </div>
      <SignButton
        type="submit"
        value="회원가입"
        buttonType="signUp"
      ></SignButton>
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
    margin-top: 30px;

    .email-input {
      position: relative;
    }

    .check-email {
      width: 40px;
      height: 40px;
      position: absolute;
      top: 8px;
      right: 0;
      transform: translateX(-8px);
      word-break: keep-all;
      border-radius: ${radius.medium};
      font: ${font.displayMedium12};
      color: ${theme.brand.textDefault};
      background-color: ${theme.brand.surfaceDefault};
    }
  }
`;
