import { rest } from "msw";
import { assignees } from "./assigneeHandlers";
import { labels, milestones } from "./handlers";

type Issue = {
  id: number;
  title: string;
  content: string;
  status: "OPENED" | "CLOSED";
  statusModifiedAt: string,
  createdAt: string;
  modifiedAt: string | null,
  assignees: {
    id: number;
    name: string;
    avatarUrl: string | null;
  }[];
  labels: {
    id: number;
    name: string;
    color: "LIGHT" | "DARK";
    background: string;
  }[];
  milestone: {
    id: number;
    name: string;
    issues: {
      openedIssueCount: number;
      closedIssueCount: number;
    };
  } | null;
  writer: {
    id: number;
    name: string;
    avatarUrl: string | null;
  };
  comments: {
    id: number;
    userId: string;
    avatarUrl: string | null;
    content: string;
    createdAt: string;
    modifiedAt: string | null;
    reactions: {
      unicode: string;
      users: number[];
      selected: number | null;
    }[];
  }[];
}

export const issueHandlers = [
  rest.get("/api/issues/:issueId", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(issue));
  }),
  rest.patch("/api/issues/:issueId/title", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { title } = await req.json();

    issue.data.title = title;

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          modifiedIssueId: Number(issueId),
        },
      }),
    );
  }),
  rest.patch("/api/issues/:issueId/status", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { status } = await req.json();

    issue.data = {
      ...issue.data,
      status,
      statusModifiedAt: new Date().toISOString(),
    };

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          modifiedIssueId: Number(issueId),
        },
      }),
    );
  }),
  rest.patch("/api/issues/:issueId/content", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { content } = await req.json();

    issue.data = {
      ...issue.data,
      content,
      modifiedAt: new Date().toISOString(),
    };

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          modifiedIssueId: Number(issueId),
        },
      }),
    );
  }),
  rest.patch("/api/issues/:issueId/assignees", async (req, res, ctx) => {
    const { issueId, assignees: assigneeIds} = await req.json();

    issue.data = {
      ...issue.data,
      assignees: assigneeIds.map((id: number) => {
        const assigneeData = assignees.data.assignees.find((a) => a.id === id)!;
        
        return {
          id: assigneeData.id,
          name: assigneeData.loginId,
          avatarUrl: assigneeData.avatarUrl,
        }
      }),
    };

    return res(ctx.status(200), ctx.json({
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issueId,
      }
    }));
  }),
  rest.patch("/api/issues/:issueId/labels", async (req, res, ctx) => {
    const { issueId, labels: labelIds} = await req.json();

    issue.data = {
      ...issue.data,
      labels: labelIds.map((id: number) => {
        const labelData = labels.data.labels.find((l) => l.id === id)!;
        
        return {
          id: labelData.id,
          name: labelData.name,
          color: labelData.color,
          background: labelData.background,
        }
      }),
    };

    return res(ctx.status(200), ctx.json({
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issueId,
      }
    }));
  }),
  rest.patch("/api/issues/:issueId/milestones", async (req, res, ctx) => {
    const { issueId, milestone: milestoneId } = await req.json();

    issue.data = {
      ...issue.data,
      milestone: milestones.data.milestones.find(m => m.id === milestoneId) ?? null
    };

    return res(ctx.status(200), ctx.json({
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issueId,
      }
    }));
  })
]

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
]

const issue: {
  code: number;
  status: string;
  message: string;
  data: Issue;
} = {
  code: 200,
  status: "OK",
  message: "OK",
  data: {
    id: 2,
    title: "제목1",
    status: "OPENED",
    statusModifiedAt: "2023-08-09T06:01:37",
    createdAt: "2023-08-09T06:01:37",
    modifiedAt: "2023-08-10T06:01:37",
    content: `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Ultricies mi quis hendrerit dolor magna eget. Purus ut faucibus pulvinar elementum integer. Suspendisse ultrices gravida dictum fusce ut placerat orci. Auctor elit sed vulputate mi sit. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus. Nunc sed blandit libero volutpat. Elementum eu facilisis sed odio morbi quis commodo. Sed augue lacus viverra vitae congue eu consequat ac felis.

      Eu volutpat odio facilisis mauris sit amet massa vitae. Ut eu sem integer vitae. Id leo in vitae turpis massa sed elementum tempus. Arcu non sodales neque sodales ut etiam. Arcu non odio euismod lacinia at quis risus sed. Eget mauris pharetra et ultrices neque ornare. Neque vitae tempus quam pellentesque nec nam. Nulla at volutpat diam ut venenatis tellus in metus. Faucibus in ornare quam viverra orci sagittis. Leo in vitae turpis massa sed elementum tempus. Augue mauris augue neque gravida in fermentum et sollicitudin ac. Lobortis elementum nibh tellus molestie nunc non blandit massa. Suspendisse in est ante in nibh mauris cursus. Sed lectus vestibulum mattis ullamcorper velit sed. Lectus proin nibh nisl condimentum id venenatis a condimentum vitae. At lectus urna duis convallis convallis tellus id. Scelerisque eleifend donec pretium vulputate. Est pellentesque elit ullamcorper dignissim cras tincidunt. Leo a diam sollicitudin tempor id eu nisl nunc mi.
      
      Magna sit amet purus gravida. Duis convallis convallis tellus id interdum velit laoreet id. Interdum consectetur libero id faucibus nisl tincidunt. Hac habitasse platea dictumst quisque sagittis purus sit amet. Tempus urna et pharetra pharetra massa. Egestas erat imperdiet sed euismod nisi porta lorem. Mauris pellentesque pulvinar pellentesque habitant morbi. Nibh ipsum consequat nisl vel pretium lectus quam. Purus sit amet luctus venenatis lectus magna fringilla urna porttitor. Vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Imperdiet nulla malesuada pellentesque elit eget gravida cum sociis. Convallis posuere morbi leo urna molestie at elementum. Accumsan tortor posuere ac ut consequat semper viverra nam libero. Non tellus orci ac auctor augue mauris augue. Vulputate ut pharetra sit amet aliquam id. Id interdum velit laoreet id. Ut venenatis tellus in metus vulputate. Egestas sed tempus urna et pharetra pharetra massa massa ultricies. Tristique senectus et netus et malesuada fames ac turpis. A iaculis at erat pellentesque adipiscing commodo elit at.`,
    assignees: [
      {
        id: 1,
        name: "hong1234",
        avatarUrl: null,
      },
    ],
    labels: [
      {
        id: 2,
        name: "documentation",
        color: "LIGHT",
        background: "#0025E6",
      },
    ],
    milestone: {
      id: 1,
      name: "Sprint#1",
      issues: {
        openedIssueCount: 22,
        closedIssueCount: 10,
      },
    },
    writer: {
      id: 1,
      name: "wis730",
      avatarUrl:
        "https://i.namu.wiki/i/2KCzxN-etRYFHRQVHH9-ROMgsOvyU3X4y7A2wuqNV5apHBn8APhnhPuapmrXewTuRcVtgos2UIAdHOCDkKVOFQ.webp",
    },
    comments: [
      {
        id: 1,
        userId: "wis730",
        avatarUrl:
          "https://i.namu.wiki/i/2KCzxN-etRYFHRQVHH9-ROMgsOvyU3X4y7A2wuqNV5apHBn8APhnhPuapmrXewTuRcVtgos2UIAdHOCDkKVOFQ.webp",
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
            users: [1],
            selected: 3,
          },
        ],
      },
      {
        id: 2,
        userId: "hjsong123",
        avatarUrl: "https://pbs.twimg.com/media/EUplmpsU0AcR9jc.jpg",
        content: "웃어보세요! 오늘 하루가 달라질 거에요!",
        createdAt: "2023-08-10T10:21:09",
        modifiedAt: "2023-08-10T13:21:09",
        reactions: [],
      },
    ],
  },
};