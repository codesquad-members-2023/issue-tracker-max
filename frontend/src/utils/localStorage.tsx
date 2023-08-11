import { UserInfoData } from "../page/auth/Auth";

export const setAccessToken = (accessToken: string) => {
  localStorage.setItem("accessToken", accessToken);
};

export const setUserInfo = (userInfo: UserInfoData) => {
  localStorage.setItem("userInfo", JSON.stringify(userInfo));
};

export const getAccessToken = () => {
  return localStorage.getItem("accessToken");
};

export const getUserInfo = () => {
  const userInfoJSON = localStorage.getItem("userInfo");

  return userInfoJSON ? JSON.parse(userInfoJSON) : null;
};
export const clearAuthInfo = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("userInfo");
};
