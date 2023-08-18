import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { customFetch } from '../../util/customFetch';
import { Theme, css, useTheme } from '@emotion/react';
import { font } from '../../styles/styles';
import { REGEX } from '../../constant/regex';
import SignButton from './common/SignButton';
import InputWithValidation from './common/InputWithValidation';
import { ReactComponent as LargeLogo } from '../../assets/logo/largeLogo.svg';
import { UserContext } from '../Context/UserContext';
import { PostSignInRes } from '../../type/Response.type';

export default function SignIn() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { ...context } = useContext(UserContext);

  // Memo: onSignIn과 onGithubSignIn의 중복 로직 통합 필요
  const onSignIn = async (e: React.FormEvent) => {
    e.preventDefault();

    const response = await customFetch<PostSignInRes>({
      subUrl: 'api/signin',
      method: 'POST',
      body: JSON.stringify({
        loginId: email,
        password: password,
      }),
    });

    if (response.success && response.data) {
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);
      localStorage.setItem('id', response.data.member.id.toString());
      localStorage.setItem('name', response.data.member.name);
      localStorage.setItem('imageUrl', response.data.member.imageUrl);

      context.setUser &&
        context.setUser({
          accessToken: response.data.accessToken,
          refreshToken: response.data.refreshToken,
          member: {
            id: response.data.member.id,
            name: response.data.member.name,
            imageUrl: response.data.member.imageUrl,
          },
        });

      navigate('/');
    }
  };

  const onGithubSignIn = async () => {
    // Notice: 미완성 기능입니다
    const response = await customFetch<PostSignInRes>({
      subUrl: 'api/oauth/github',
      method: 'POST',
    });

    if (response && response.success && response.data) {
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);

      navigate('/');
    }
  };

  const onEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const onPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const validateInputValue = (inputValue: string, regex: RegExp) => {
    return regex.test(inputValue);
  };

  const isValidEmail = validateInputValue(email, REGEX.email);
  const isValidPassword = validateInputValue(password, REGEX.password);
  const isSignInButtonDisabled = !isValidEmail || !isValidPassword;

  return (
    <form
      action="/api/signin"
      method="POST"
      onSubmit={onSignIn}
      css={signInForm(theme)}
    >
      <Link to={'/sign-in'}>
        <LargeLogo className="logo" />
      </Link>
      <div className="form-container">
        <SignButton
          type="button"
          value="GitHub 계정으로 로그인"
          onClick={onGithubSignIn}
          buttonType="github"
          disabled
        />
        <span className="or">or</span>
        <div>
          <InputWithValidation
            type="email"
            value={email}
            onChange={onEmailChange}
            placeholder="example@gmail.com"
            label="이메일"
            valid={isValidEmail}
            text="올바른 이메일을 입력해주세요"
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
        </div>
        <SignButton
          type="submit"
          value="로그인"
          disabled={isSignInButtonDisabled}
          buttonType="signIn"
        />
        <Link to={'/sign-up'}>
          <SignButton type="button" value="회원가입" buttonType="signUp" />
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

    .or {
      font: ${font.displayMedium16};
      color: ${theme.neutral.textWeak};
    }
  }
`;
