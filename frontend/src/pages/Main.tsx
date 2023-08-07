import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import useRefreshToken from '../hooks/useRefreshToken';

export default function Main() {
  const auth = useAuth();
  const navigate = useNavigate();
  const refresh = useRefreshToken();

  const handleLogout = () => {
    auth.logout();
    localStorage.clear();
    navigate('/login');
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
