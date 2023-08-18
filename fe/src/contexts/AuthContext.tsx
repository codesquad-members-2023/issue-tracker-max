import React, { createContext, useContext, useEffect, useState } from "react";

interface User {
  profile: string;
  // userId: number 유저 아이디도 받아야함
}

interface AuthProviderProps {
  children: React.ReactNode;
}

interface AuthContextProps {
  user: User | null;
  accessToken: string | null;
  login: (token: string, user: User) => void;
  logout: () => void;
  refreshAccessToken: () => Promise<void>;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const storedUser = localStorage.getItem("user");
  const initialUser = storedUser ? JSON.parse(storedUser) : null;
  const [user, setUser] = useState<User | null>(initialUser);
  const [accessToken, setAccessToken] = useState<string | null>(null);

  const login = (token: string, user: User) => {
    setAccessToken(token);
    setUser(user);
    localStorage.setItem("user", JSON.stringify(user));
  };

  const logout = async () => {
    try {
      const response = await fetch("http://43.200.169.143:8080//api/logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        credentials: "include",
      });

      if (!response.ok) {
        throw new Error("Failed to log out");
      }

      setUser(null);
      setAccessToken(null);
      localStorage.removeItem("user");
    } catch (error) {
      console.error("Error during logout:", error);
    }
  };

  const refreshAccessToken = async () => {
    try {
      const response = await fetch(
        "http://43.200.169.143:8080/api/auth/reissue",
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
        },
      );

      const data = await response.json();

      if (data.accessToken) {
        setAccessToken(data.accessToken);
      } else {
        // logout();
      }
    } catch (error) {
      console.error("Error refreshing access token:", error);
      logout();
    }
  };

  useEffect(() => {
    if (accessToken) {
      // console.log("나 로그인 되있어");
    } else {
      // console.log("나 리프레쉬 했어");
      refreshAccessToken();
      // console.log("리프레시후 accessToken", accessToken);
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{ user, accessToken, login, logout, refreshAccessToken }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
