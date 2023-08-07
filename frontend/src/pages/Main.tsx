import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

export default function Main() {
  const auth = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    auth.logout();
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
    </>
  );
}
