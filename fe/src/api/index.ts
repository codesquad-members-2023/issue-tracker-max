import { fetcher, fetcherWithBearer } from "./fetcher";

export const postSignup = async (loginId: string, password: string) => {
  return await fetcher.post("/auth/signup", { loginId, password });
};

export const postLogin = async (loginId: string, password: string) => {
  return await fetcher.post("/auth/login", { loginId, password });
};

export const getIssues = async () => {
  return await fetcherWithBearer.get("/issues");
};
