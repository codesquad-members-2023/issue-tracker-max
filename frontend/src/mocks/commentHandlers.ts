import { rest } from "msw";
import { users } from "./handlers";
import { issues } from "./issueHandlers";

type Comment = {
  id: number;
  issueId: number;
  userId: string;
  avatarUrl: string | null;
  content: string;
  createdAt: string;
  modifiedAt: string | null;
  reactions: {
    unicode: string;
    users: string[];
    selected: number | null;
  }[];
};

export const commentHandlers = [
  rest.post("/api/comments", async (req, res, ctx) => {
    const authorizationToken = req.headers.get("authorization")!.split(" ")[1];
    const { issueId, content } = await req.json();

    const user = users.find(({ jwt }) => jwt.accessToken === authorizationToken);

    if (!user) {
      const unauthorizedError = {
        code: 401,
        status: "UNAUTHORIZED",
        message: "인증에 실패하였습니다.",
        data: null,
      };

      return res(ctx.status(401), ctx.json(unauthorizedError));
    }

    const issue = issues.find((i) => i.id === issueId);

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    const comment = {
      id: Date.now(),
      issueId,
      userId: user.user.loginId,
      avatarUrl: user.user.avatarUrl,
      content,
      createdAt: new Date().toISOString(),
      modifiedAt: new Date().toISOString(),
      reactions: [],
    };

    comments.push(comment);

    const response = {
      code: 201,
      status: "CREATED",
      message: "댓글 등록에 성공했습니다.",
      data: {
        savedCommentId: comment.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/comments/:commentId", async (req, res, ctx) => {
    const { commentId } = req.params;
    const { content } = await req.json();

    const comment = comments.find(
      (comment) => comment.id === Number(commentId),
    );

    if (!comment) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "댓글을 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    comment.content = content;
    comment.modifiedAt = new Date().toISOString();

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: {
        modifiedCommentId: comment.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.delete("/api/comments/:commentId", async (req, res, ctx) => {
    const { commentId } = req.params;

    const comment = comments.findIndex(
      (comment) => comment.id === Number(commentId),
    );

    if (comment === -1) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "댓글을 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    const deletedComment = comments.splice(comment, 1);

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: {
        deletedCommentId: deletedComment[0].id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
];

export const comments: Comment[] = [
  {
    id: 3,
    issueId: 2,
    userId: "wis730",
    avatarUrl:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3j1RUWi4sAJkgFEdEFjtgvTOnd9kIpNZMp-GFc8zi&s",
    content: "이 댓글을 읽은 당신은 앞으로 모든 일이 신기하게 잘풀립니다!",
    createdAt: "2023-08-09T15:21:09",
    modifiedAt: "2023-08-09T20:21:09",
    reactions: [
      {
        unicode: "&#128077",
        users: [],
        selected: null,
      },
      {
        unicode: "&#128078",
        users: ["wis730"],
        selected: 3,
      },
    ],
  },
  {
    id: 5,
    issueId: 2,
    userId: "hjsong123",
    avatarUrl: "https://pbs.twimg.com/media/EUplmpsU0AcR9jc.jpg",
    content: "웃어보세요! 오늘 하루가 달라질 거에요!",
    createdAt: "2023-08-10T10:21:09",
    modifiedAt: "2023-08-10T13:21:09",
    reactions: [],
  },
];
