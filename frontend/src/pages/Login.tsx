import { useContext } from 'react';
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
  const { login } = useAuth();
  const { util } = useContext(AppContext);
  const logo = (util.getLogoByTheme() as ContextLogo).large;
  const navigate = useNavigate();
  const location = useLocation();
  // 로그인 화면 넘어오기 전 페이지를 기억해서 로그인 성공 시 그 페이지로 이동
  const from = location.state?.from?.pathname || '/';

  const handleLogin = async (id: string, password: string) => {
    try {
      const res = await axios.post(
        '/api/login',
        JSON.stringify({ id, password }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        }
      );

      if (res.status === 200) {
        localStorage.setItem('accessToken', res.data.messages.accessToken);
        localStorage.setItem('refreshToken', res.data.messages.refreshToken);
        login({
          user: id,
          pwd: password,
          accessToken: res.data.messages.accessToken,
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
    const { id, password } = Object.fromEntries(formData) as {
      id: string;
      password: string;
    };
    handleLogin(id, password);
  };

  return (
    <Container>
      <h1 className="blind">로그인 페이지</h1>
      <Logo>
        <img src={logo} alt="" />
        <figcaption className="blind">이슈트래커</figcaption>
      </Logo>
      <GitHubOAuthButton type="button" outline>
        GitHub 계정으로 로그인
      </GitHubOAuthButton>
      <span>or</span>
      <LoginForm onSubmit={handleSubmit}>
        <TextInput
          id="id"
          name="id"
          size="tall"
          labelName="아이디"
          placeholder="아이디"
        />
        <TextInput
          id="password"
          name="password"
          size="tall"
          labelName="비밀번호"
          placeholder="비밀번호"
        />
        <LoginButton type="submit">아이디로 로그인</LoginButton>
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
    margin-bottom: 16px;
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
