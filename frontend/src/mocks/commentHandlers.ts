import { rest } from "msw";
import { issue } from "./issueHandlers";

export const commentHandlers = [
  rest.post("/api/comments", async (req, res, ctx) => {
    const { content, userId } = await req.json();

    const comment = {
      id: issue.data.comments.length + 1,
      userId,
      avatarUrl: "https://pbs.twimg.com/media/EUplmpsU0AcR9jc.jpg",
      content,
      createdAt: new Date().toISOString(),
      modifiedAt: new Date().toISOString(),
      reactions: [],
    };

    issue.data = {
      ...issue.data,
      comments: [...issue.data.comments, comment],
    };

    return res(
      ctx.status(200),
      ctx.json({
        code: 201,
        status: "CREATED",
        message: "댓글 등록에 성공했습니다.",
        data: {
          savedCommentId: comment.id,
        },
      }),
    );
  }),
  rest.patch("/api/comments/:commentId", async (req, res, ctx) => {
    const { commentId } = req.params;
    const { content } = await req.json();

    issue.data = {
      ...issue.data,
      comments: issue.data.comments.map((comment) => {
        return comment.id === Number(commentId)
          ? { ...comment, content, modifiedAt: new Date().toISOString() }
          : comment;
      }),
    };

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          modifiedCommentId: Number(commentId),
        },
      }),
    );
  }),
  rest.delete("/api/comments/:commentId", async (req, res, ctx) => {
    const { commentId } = req.params;

    issue.data = {
      ...issue.data,
      comments: issue.data.comments.filter(
        (comment) => comment.id !== Number(commentId),
      ),
    };

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          deletedCommentId: Number(commentId),
        },
      }),
    );
  })
]