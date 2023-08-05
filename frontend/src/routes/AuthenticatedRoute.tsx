import { useState, useContext } from 'react';
import { Navigate } from 'react-router';
import { AppContext } from '../main';

export default function AuthenticatedRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const { util, control } = useContext(AppContext);
  util.isLogin = () => isAuthenticated;
  control.loginCheck = () => setIsAuthenticated(true);
  control.logoutCheck = () => setIsAuthenticated(false);

  return isAuthenticated ? children : <Navigate to="/login" replace />;
}
