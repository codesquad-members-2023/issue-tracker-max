import { useContext } from 'react';
import { styled } from 'styled-components';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom';
import { AppContext } from '../main';
import ContextLogo from '../types/ContextLogo';
import TextInput from '../components/common/TextInput';
import ButtonLarge from '../components/common/button/ButtonLarge';
import Button from '../components/common/button/BaseButton';

export default function Register() {
  const appContext = useContext(AppContext);
  const logo = (appContext.util.getLogoByTheme() as ContextLogo)
    .large as string;
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData(form);
    const { userId, password, userName } = Object.fromEntries(formData) as {
      userId: string;
      password: string;
      userName: string;
    };
    handleSignUp(userId, password, userName);
  };

  const handleSignUp = async (id: string, password: string, name: string) => {
    try {
      const res = await axios.post(
        '/api/signup',
        JSON.stringify({ email: id, password: password, name: name }),
        {
          headers: {
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        }
      );

      if (res.status === 200) {
        navigate('/login', { replace: true });
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Container>
      <h1 className="blind">회원가입 페이지</h1>
      <Logo>
        <img src={logo} alt="" />
        <figcaption className="blind">이슈트래커</figcaption>
      </Logo>
      <RegisterForm onSubmit={handleSubmit}>
        <TextInput
          id="id"
          name="userId"
          size="tall"
          labelName="아이디"
          placeholder="아이디"
        />
        <TextInput
          id="password"
          name="password"
          size="tall"
          type="password"
          labelName="비밀번호"
          placeholder="비밀번호"
        />
        <TextInput
          id="name"
          name="userName"
          size="tall"
          labelName="닉네임"
          placeholder="닉네임"
        />
        <RegisterButton type="submit">회원가입</RegisterButton>
      </RegisterForm>
      <LoginLink type="button" ghost onClick={() => navigate('/login')}>
        로그인으로 이동하기
      </LoginLink>
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

const RegisterForm = styled.form`
  width: 320px;
`;

const RegisterButton = styled(ButtonLarge)`
  width: 100%;
  margin-bottom: 16px;
`;

const LoginLink = styled(Button)``;
