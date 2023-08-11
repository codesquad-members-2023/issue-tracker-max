import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Theme, css, useTheme } from '@emotion/react';
import { font, radius } from '../../styles/styles';
import SignButton from './common/SignButton';
import InputWithValidation from './common/InputWithValidation';
import { REGEX } from '../../constant/regex';
import { ReactComponent as LargeLogo } from '/src/assets/logo/largeLogo.svg';
import { customFetch } from '../../util/customFetch';

type Response = {
  success: boolean;
  data: null;
};

export default function SignUp() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [isOverlap, setIsOverlap] = useState<boolean>(true); // Memo: API 나오면 false로 바꿔야함
  const [email, setEmail] = useState('');
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [doubleCheck, setDoubleCheck] = useState('');

  const onSignUp = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const signUpData = {
      loginId: email,
      password: password,
      nickName: userName,
    };

    const subUrl = 'api/signup';
    const method = 'POST';
    const body = signUpData;

    const response = await customFetch<Response>({
      subUrl,
      method,
      body,
    });

    if (response && response.success) {
      navigate('/sign-in');
    }
  };

  const onCheckEmail = async () => {
    const subUrl = 'api/signup/check-member-email';
    const body = { loginId: email };

    const response = await customFetch<Response>({
      subUrl,
      body,
    });

    if (response && response.success) {
      setIsOverlap(true);
    }
  };

  const onEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const onUserNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(e.target.value);
  };

  const onPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onDoubleCheckChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDoubleCheck(e.target.value);
  };

  const validateInputValue = (inputValue: string, regex: RegExp) => {
    return regex.test(inputValue);
  };

  const isValidEmail = validateInputValue(email, REGEX.email) && isOverlap;
  const isValidUserName = validateInputValue(userName, REGEX.userName);
  const isValidPassword = validateInputValue(password, REGEX.password);
  const isValidDoubleCheck = password === doubleCheck;
  const isSignInButtonDisabled =
    !isValidEmail ||
    !isValidUserName ||
    !isValidPassword ||
    !isValidDoubleCheck;

  return (
    <form
      action="http://3.38.61.146/api/signup"
      method="POST"
      onSubmit={onSignUp}
      css={signInForm(theme)}
    >
      <Link to={'/sign-in'}>
        <LargeLogo className="logo" />
      </Link>

      <div className="form-container">
        <div className="email-input">
          <InputWithValidation
            type="email"
            value={email}
            onChange={onEmailChange}
            placeholder="example@gmail.com"
            label="이메일"
            valid={isValidEmail}
            text="올바른 이메일을 입력해주세요"
          />
          <button type="button" className="check-email" onClick={onCheckEmail}>
            중복 체크
          </button>
        </div>
        <InputWithValidation
          type="userName"
          value={userName}
          onChange={onUserNameChange}
          placeholder="닉네임"
          label="닉네임"
          valid={isValidUserName}
          text="올바른 닉네임을 입력해주세요"
        />
        <InputWithValidation
          type="password"
          value={password}
          onChange={onPasswordChange}
          placeholder="영어, 숫자를 포함한 6~16자"
          label="비밀번호"
          valid={isValidPassword}
          text="올바른 비밀번호를 입력해주세요"
        />
        <InputWithValidation
          type="password"
          value={doubleCheck}
          onChange={onDoubleCheckChange}
          placeholder="비밀번호를 한번 더 입력해주세요"
          label="비밀번호 확인"
          valid={isValidDoubleCheck}
          text="비밀번호가 다릅니다"
        />
      </div>
      <SignButton
        type="submit"
        value="회원가입"
        disabled={isSignInButtonDisabled}
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
      transform: translateX(-20%);
      word-break: keep-all;
      border-radius: ${radius.medium};
      font: ${font.displayMedium12};
      color: ${theme.brand.textDefault};
      background-color: ${theme.brand.surfaceDefault};
    }
  }
`;
