import { useState, createContext, ReactElement } from 'react';

type AuthUser = { user: string; pwd: string; accessToken: string };

export type AuthContextValue = {
  auth: AuthUser | null;
  login: (authUser: AuthUser) => void;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: ReactElement }) {
  const [auth, setAuth] = useState<AuthUser | null>(null);

  const login = (authUser: AuthUser) => {
    setAuth(authUser);
  };

  const logout = () => {
    setAuth(null);
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
