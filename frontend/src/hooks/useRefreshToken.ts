import axios from '../api/axios';
import useAuth from './useAuth';
import { AuthUser } from '../context/AuthProvider';

const useRefreshToken = () => {
  const { setAuth } = useAuth();

  const refresh = async () => {
    const response = await axios.post(
      '/api/reissue/token',
      JSON.stringify({ refreshToken: localStorage.getItem('refreshToken') }),
      {
        withCredentials: true,
      }
    );
    setAuth((prev) => {
      // 이전 상태가 null일 경우
      if (!prev) {
        return null;
      }

      const newAuth: AuthUser = {
        user: prev.user,
        pwd: prev.pwd,
        accessToken: response.data.accessToken,
      };
      return newAuth;
    });
    return response.data.accessToken;
  };
  return refresh;
};

export default useRefreshToken;
