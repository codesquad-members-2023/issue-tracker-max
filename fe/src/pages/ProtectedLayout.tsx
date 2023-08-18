import { Navigate, Outlet } from "react-router-dom";

import { useAuth } from "../contexts/AuthContext";

export const ProtectedLayout = () => {
  const { accessToken } = useAuth();

  if (!accessToken) {
    alert("로그인 해주세요");
    return <Navigate to="/" />;
  }
  return accessToken ? <Outlet /> : <Navigate to="/login" />;
};
