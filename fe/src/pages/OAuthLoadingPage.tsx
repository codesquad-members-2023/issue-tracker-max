import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { fetchData } from 'apis/api';
import {
  setLocalStorageAccessToken,
  setLocalStorageImage,
  setLocalStorageLoginId,
  setLocalStorageRefreshToken,
  setLocalStorageUserId,
} from 'apis/localStorage';

export const OAuthLoadingPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchData(location.pathname + location.search);
        const { loginId, image, accessToken, refreshToken } = data;
        const userId = JSON.parse(
          JSON.parse(atob(accessToken.split('.')[1])).body,
        ).userId;

        setLocalStorageUserId(userId);
        setLocalStorageLoginId(loginId);
        setLocalStorageImage(image);
        setLocalStorageAccessToken(accessToken);
        setLocalStorageRefreshToken(refreshToken);

        navigate('/issues');
      } catch (error) {
        console.error(error);
      }
    })();
  }, []);

  return <div>깃허브 로그인 중...</div>;
};
