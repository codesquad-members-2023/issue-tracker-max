import { useState, useContext } from 'react';
import { Navigate } from 'react-router';
import { AppContext } from '../main';

export default function AuthenticatedRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const appContext = useContext(AppContext);
  appContext.util.isLogined = () => isAuthenticated;
  appContext.control.logined = () => setIsAuthenticated(true);
  appContext.control.logouted = () => setIsAuthenticated(false);

  return isAuthenticated ? children : <Navigate to="/login" replace />;
}
