import { fetcher } from "./fetcher";

export const postSignup = async (loginId: string, password: string) => {
  await fetcher.post("/auth/signup", { loginId, password });
};

export const postLogin = async (loginId: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId, password });
};
