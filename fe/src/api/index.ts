import axios from "axios";

const instance = axios.create({
  baseURL: "/api",
  headers: { "Content-Type": "application/json" },
});

export const postSignup = async (loginId: string, password: string) => {
  await instance.post("/auth/signup", { loginId, password });
};

export const postLogin = async (loginId: string, password: string) => {
  return await instance.post("/auth/login", { loginId, password });
};
