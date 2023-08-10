import { useState, createContext, ReactElement } from 'react';

export type AuthUser = {
  userId: string;
  userName: string;
  profileImg: string;
  accessToken: string;
} | null;

export type AuthContextType = {
  auth: AuthUser | null;
  setAuth: React.Dispatch<React.SetStateAction<AuthUser | null>>;
  login: (authUser: AuthUser) => void;
  logout: () => void;
} | null;

export const AuthContext = createContext<AuthContextType>(null);

export function AuthProvider({ children }: { children: ReactElement }) {
  const [auth, setAuth] = useState<AuthUser>(null);

  const login = (authUser: AuthUser) => {
    setAuth(authUser);
  };

  const logout = () => {
    setAuth(null);
  };

  return (
    <AuthContext.Provider value={{ auth, setAuth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
