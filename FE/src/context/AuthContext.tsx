import { createContext, useContext, useState, ReactNode } from "react";
import { UserProfile } from "../type";

interface AuthContextType {
  profile: UserProfile | undefined;
  updateProfile(data: UserProfile): void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [profile, setProfile] = useState<UserProfile>();

  const updateProfile = (data: UserProfile) => {
    setProfile(data);
  };

  return (
    <AuthContext.Provider value={{ profile, updateProfile }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
