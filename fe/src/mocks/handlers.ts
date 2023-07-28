import { rest } from "msw";

const users: Record<string, string> = {};

type AuthRequestBody = {
  loginId: string;
  password: string;
};

export const handlers = [
  rest.post("/api/auth/signup", async (req, res, ctx) => {
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = users[loginId];
    users[loginId] = password;

    if (isExist) {
      return res(
        ctx.status(400),
        ctx.json({
          errorCode: "DUPLICATED_LOGIN_ID",
          message: "중복된 로그인 아이디가 존재합니다.",
        })
      );
    }

    return res(ctx.status(200));
  }),

  rest.post("/api/auth/login", async (req, res, ctx) => {
    const { loginId, password } = await req.json<AuthRequestBody>();
    const isExist = users[loginId];
    const isCorrectPassword = users[loginId] === password;

    if (!isExist) {
      return res(
        ctx.status(404),
        ctx.json({
          errorCode: "USER_NOT_FOUND",
          message: "존재하지 않는 회원입니다.",
        })
      );
    }

    if (!isCorrectPassword) {
      return res(
        ctx.status(400),
        ctx.json({
          errorCode: "FAILED_LOGIN",
          message: "비밀번호가 일치하지 않습니다.",
        })
      );
    }

    // TODO: 만료된 토큰에 대한 응답 처리

    return res(
      ctx.status(200),
      ctx.json({
        token: {
          tokenType: "bearer",
          accessToken: "q13t302hv2ht0",
          expiresIn: 48000000,
        },
        user: {
          username: "admin",
          userProfileSrc:
            "https://avatars.githubusercontent.com/u/48426991?v=4",
        },
      })
    );
  }),
];
