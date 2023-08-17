import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { getAccessToken, setAccessToken } from "../utils/localStorage";

export const useTokenRefresh = () => {
  const navigate = useNavigate();

  const tokenRefresh = useCallback(
    async (retryRequest?: () => Promise<void>) => {
      const res = await fetch("/api/auth/refresh/token", {
        method: "POST",
        credentials: "include",
        headers: {
          Authorization: `Bearer ${getAccessToken()}`,
        },
      });

      const { code, data } = await res.json();

      switch (code) {
        case 200: {
          setAccessToken(data.jwt.accessToken);
          retryRequest && (await retryRequest());

          break;
        }
        case 400: {
          navigate("/auth");
        }
      }
    },
    [navigate],
  );

  return tokenRefresh;
};
