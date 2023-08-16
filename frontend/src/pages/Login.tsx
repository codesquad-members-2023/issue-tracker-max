import { useState, useContext } from 'react';
import { styled } from 'styled-components';
import axios from '../api/axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { AppContext } from '../main';
import useAuth from '../hooks/useAuth';
import ContextLogo from '../types/ContextLogo';
import TextInput from '../components/common/TextInput';
import ButtonLarge from '../components/common/button/ButtonLarge';
import Button from '../components/common/button/BaseButton';

export default function Login() {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  const { login } = useAuth();
  const { util } = useContext(AppContext);
  const logo = (util.getLogoByTheme() as ContextLogo).large;
  const navigate = useNavigate();
  const location = useLocation();
  // 로그인 화면 넘어오기 전 페이지를 기억해서 로그인 성공 시 그 페이지로 이동
  const from = location.state?.from?.pathname || '/';
  const GITHUB_LOGIN_URL = `https://github.com/login/oauth/authorize?client_id=${
    import.meta.env.VITE_CLIENT_ID
  }`;

  const validateUserEmail = (value: string) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(value);
  };

  const validateUserPassword = (value: string) => {
    const passwordRegex = /^[a-zA-Z0-9!@#$%^&*|'"~;:₩\\?]{6,12}$/;
    return passwordRegex.test(value);
  };

  const isValidate =
    validateUserEmail(userId) && validateUserPassword(password);

  const handleLogin = async (userId: string, password: string) => {
    try {
      const res = await axios.post(
        '/api/login',
        JSON.stringify({ email: userId, password }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        }
      );

      if (res.status === 200) {
        localStorage.setItem('accessToken', res.data.message.accessToken);
        localStorage.setItem('refreshToken', res.data.message.refreshToken);
        login({
          userId: res.data.message.userId,
          userName: res.data.message.userName,
          profileImg: res.data.message.userProfileImg,
          accessToken: res.data.message.accessToken,
        });
      }

      navigate(from, { replace: true });
    } catch (err) {
      console.error(err);
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData(form);
    const { userId, password } = Object.fromEntries(formData) as {
      userId: string;
      password: string;
    };
    handleLogin(userId, password);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name === 'userId') {
      setUserId(value);
    } else if (name === 'password') {
      setPassword(value);
    }
  };

  return (
    <Container>
      <h1 className="blind">로그인 페이지</h1>
      <Logo>
        <img src={logo} alt="" />
        <figcaption className="blind">이슈트래커</figcaption>
      </Logo>
      <GitHubOAuthButton
        type="button"
        outline
        onClick={() => {
          window.location.assign(GITHUB_LOGIN_URL);
        }}>
        GitHub 계정으로 로그인
      </GitHubOAuthButton>
      <span>or</span>
      <LoginForm onSubmit={handleSubmit}>
        <TextInput
          id="userId"
          name="userId"
          sizeType="tall"
          labelName="아이디"
          value={userId}
          onChange={handleChange}
          placeholder="아이디"
          helpText="아이디는 이메일 형식으로 입력해주세요."
          hasError={userId !== '' && !validateUserEmail(userId)}
        />
        <TextInput
          id="password"
          name="password"
          sizeType="tall"
          type="password"
          value={password}
          onChange={handleChange}
          labelName="비밀번호"
          placeholder="비밀번호"
          helpText="비밀번호는 6자 이상 12자 이하로 입력해주세요."
          hasError={password !== '' && !validateUserPassword(password)}
        />
        <LoginButton type="submit" disabled={isValidate ? false : true}>
          아이디로 로그인
        </LoginButton>
      </LoginForm>
      <RegisterButton type="button" ghost onClick={() => navigate('/register')}>
        회원가입
      </RegisterButton>
    </Container>
  );
}

const Container = styled.article`
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;

  & > span {
    margin-bottom: 18px;
    color: ${({ theme }) => theme.color.neutral.text.weak};
    ${({ theme }) => theme.font.display.medium[16]};
  }

  fieldset {
    margin-bottom: 24px;
  }
`;

const Logo = styled.figure`
  margin-bottom: 64px;
`;

const GitHubOAuthButton = styled(ButtonLarge)`
  width: 320px;
  margin-bottom: 14px;
`;

const LoginForm = styled.form`
  width: 320px;
`;

const LoginButton = styled(ButtonLarge)`
  width: 100%;
  margin-bottom: 16px;
`;

const RegisterButton = styled(Button)``;
