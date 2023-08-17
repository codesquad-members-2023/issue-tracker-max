import { rest } from "msw";

export const assigneeHandlers = [
  rest.get("/api/assignees", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(assignees));
  })
];

export const assignees = {
  code: 200,
  status: "OK",
  message: "OK",
  data: {
    assignees: [
      {
        id: 1,
        loginId: "hong1234",
        avatarUrl: null,
        selected: false,
      },
      {
        id: 2,
        loginId: "lee1234",
        avatarUrl: null,
        selected: false,
      },
    ],
  },
};
