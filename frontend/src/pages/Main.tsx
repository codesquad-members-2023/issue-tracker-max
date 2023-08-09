import { useNavigate } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import useAuth from '../hooks/useAuth';
import useRefreshToken from '../hooks/useRefreshToken';

export default function Main() {
  const { auth, logout } = useAuth();
  const navigate = useNavigate();
  const refresh = useRefreshToken();
  const axiosPrivate = useAxiosPrivate();

  const handleLogout = async () => {
    if (!auth) {
      return;
    }

    try {
      const res = await axiosPrivate.post('/api/logout', null, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });

      if (res.status === 200) {
        localStorage.clear();
        logout();
        navigate('/login');
      } else {
        console.error('로그아웃 실패:', res.status);
      }
    } catch (err) {
      console.error('로그아웃 에러:', err);
    }
  };

  return (
    <>
      <h1>main</h1>
      <button onClick={handleLogout}>logout</button>
      <button
        onClick={() => {
          navigate('/addIssue');
        }}>
        새로운 이슈작성
      </button>
      <button onClick={refresh}>refresh</button>
    </>
  );
}
