import { useContext } from 'react';
import { AuthContext, AuthContextValue } from '../context/AuthProvider';

export const useAuth = (): AuthContextValue => {
  const authContext = useContext(AuthContext);
  if (!authContext) {
    throw new Error('useAuth must be used within an AuthProvider.');
  }
  return authContext;
};
