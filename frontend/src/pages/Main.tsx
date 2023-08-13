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
      const res = await axiosPrivate.post(
        '/api/logout',
        JSON.stringify({ id: auth.userId, password: auth.pwd }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        }
      );

      if (res.status === 200) {
        localStorage.clear();
        logout();
      }
      navigate('/login');
    } catch (err) {
      console.error(err);
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
