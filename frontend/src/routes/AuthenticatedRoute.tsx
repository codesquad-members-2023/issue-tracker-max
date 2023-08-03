import { useEffect, useState } from 'react';
import { Navigate, useLocation } from 'react-router';

export default function AuthenticatedRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const session = localStorage.getItem('session');
    setIsAuthenticated(!!session);
  }, [location]); // location이 바뀔 때마다 로그인 상태를 확인합니다.

  return isAuthenticated ? children : <Navigate to="/login" replace />;
}
