import { useEffect } from 'react';
import { styled, keyframes } from 'styled-components';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import useAuth from '../hooks/useAuth';

export default function Callback() {
  const { login } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const getAccessToken = async () => {
      const codeParam = new URLSearchParams(window.location.search).get('code');

      if (!codeParam) {
        navigate('/login');
        return;
      }

      try {
        const res = await axios.get(`/api/login/github?code=${codeParam}`, {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        });

        if (res.status === 200) {
          localStorage.setItem('accessToken', res.data.message.accessToken);
          localStorage.setItem('refreshToken', res.data.message.refreshToken);
          login({
            userId: res.data.message.userId,
            userName: res.data.message.userName,
            profileImg: res.data.message.userProfileImg,
            accessToken: res.data.message.accessToken,
          });
          navigate('/');
        }
      } catch (err) {
        console.error(err);
        navigate('/login');
      }
    };

    getAccessToken();
  }, []);

  return (
    <Container>
      <Loader />
    </Container>
  );
}

const spinner = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

const Container = styled.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Loader = styled.div`
  width: 50px;
  height: 50px;
  border: 10px solid #f3f3f3;
  border-top: 10px solid #383636;
  border-radius: 50%;
  animation: ${spinner} 1.5s linear infinite;
`;
