import { useState, createContext, ReactElement } from 'react';

export type AuthUser = {
  userId: number;
  userName: string;
  profileImg: string;
  accessToken: string;
} | null;

export type AuthContextType = {
  auth: AuthUser | null;
  isAuthenticated: boolean;
  setAuth: React.Dispatch<React.SetStateAction<AuthUser | null>>;
  login: (authUser: AuthUser) => void;
  logout: () => void;
} | null;

export const AuthContext = createContext<AuthContextType>(null);

export function AuthProvider({ children }: { children: ReactElement }) {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    localStorage.getItem('userId') ? true : false
  );
  const [auth, setAuth] = useState<AuthUser>(
    localStorage.getItem('userId')
      ? {
          userId: JSON.parse(localStorage.getItem('userId')!) as number,
          userName: localStorage.getItem('userName') as string,
          profileImg: localStorage.getItem('profileImg') as string,
          accessToken: localStorage.getItem('accessToken') as string,
        }
      : null
  );

  const login = (authUser: AuthUser) => {
    setIsAuthenticated(true);
    setAuth(authUser);
  };

  const logout = () => {
    setIsAuthenticated(false);
    setAuth(null);
  };

  return (
    <AuthContext.Provider
      value={{ auth, isAuthenticated, setAuth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
