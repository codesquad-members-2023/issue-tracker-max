import { rest } from "msw";
import { assignees } from "./assigneeHandlers";
import { comments } from "./commentHandlers";
import { labels, milestones, users } from "./handlers";

type Issue = {
  id: number;
  title: string;
  content: string;
  status: "OPENED" | "CLOSED";
  statusModifiedAt: string;
  createdAt: string;
  modifiedAt: string | null;
  reactions: {
    unicode: string;
    users: string[];
    selectedUserReactionId: number;
  }[];
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
      users: string[];
      selected: number | null;
    }[];
  }[];
};

export const issueHandlers = [
  rest.get("/api/issues/:issueId", (req, res, ctx) => {
    const { issueId } = req.params;

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.comments = comments
      .filter((c) => c.issueId === Number(issueId))
      .map((c) => ({
        id: c.id,
        userId: c.userId,
        avatarUrl: c.avatarUrl,
        content: c.content,
        createdAt: c.createdAt,
        modifiedAt: c.modifiedAt,
        reactions: c.reactions,
      }));

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: issue,
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.post("/api/issues", async (req, res, ctx) => {
    const authorizationToken = req.headers.get("authorization")!.split(" ")[1];
    const {
      title,
      content,
      milestone: milestoneId,
      labels: labelIds,
      assignees: assigneeIds,
    } = await req.json();

    const user = users.find(
      ({ jwt }) => jwt.accessToken === authorizationToken,
    );

    if (!user) {
      const unauthorizedError = {
        code: 401,
        status: "UNAUTHORIZED",
        message: "인증에 실패하였습니다.",
        data: null,
      };

      return res(ctx.status(401), ctx.json(unauthorizedError));
    }

    const newIssue: Issue = {
      id: Date.now(),
      title,
      content,
      status: "OPENED",
      statusModifiedAt: new Date().toISOString(),
      createdAt: new Date().toISOString(),
      modifiedAt: null,
      reactions: [],
      assignees: assigneeIds.map((id: number) => {
        const assigneeData = assignees.data.assignees.find((a) => a.id === id)!;

        return {
          id: assigneeData.id,
          name: assigneeData.loginId,
          avatarUrl: assigneeData.avatarUrl,
        };
      }),
      labels: labelIds.map((id: number) => {
        const labelData = labels.data.labels.find((l) => l.id === id)!;

        return {
          id: labelData.id,
          name: labelData.name,
          color: labelData.color,
          background: labelData.background,
        };
      }),
      milestone:
        milestones.data.milestones.find((m) => m.id === milestoneId) ?? null,
      writer: {
        id: user.user.id,
        name: user.user.loginId,
        avatarUrl: user.user.avatarUrl,
      },
      comments: [],
    };

    issues.push(newIssue);

    return res(
      ctx.status(201),
      ctx.json({
        code: 201,
        status: "CREATED",
        message: "이슈 등록에 성공했습니다.",
        data: {
          savedIssueId: newIssue.id,
        },
      }),
    );
  }),
  rest.patch("/api/issues/:issueId/title", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { title } = await req.json();

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.title = title;

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: {
        modifiedIssueId: issue.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/issues/status", async (req, res, ctx) => {
    const { issues:issueIds, status } = await req.json();

    const targetIssues = issues.filter((i) => issueIds.includes(i.id))
    targetIssues.forEach((i) => {
      i.status = status;
      i.statusModifiedAt = new Date().toISOString();
    });

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: {
        modifiedIssueId: targetIssues.map(i => i.id),
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/issues/:issueId/content", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { content } = await req.json();

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.content = content;
    issue.modifiedAt = new Date().toISOString();

    const response = {
      code: 200,
      status: "OK",
      message: "OK",
      data: {
        modifiedIssueId: issue.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/issues/:issueId/assignees", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { assignees: assigneeIds } = await req.json();

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.assignees = assigneeIds.map((id: number) => {
      const assigneeData = assignees.data.assignees.find((a) => a.id === id)!;

      return {
        id: assigneeData.id,
        name: assigneeData.loginId,
        avatarUrl: assigneeData.avatarUrl,
      };
    });

    const response = {
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issue.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/issues/:issueId/labels", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { labels: labelIds } = await req.json();

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.labels = labelIds.map((id: number) => {
      const labelData = labels.data.labels.find((l) => l.id === id)!;

      return {
        id: labelData.id,
        name: labelData.name,
        color: labelData.color,
        background: labelData.background,
      };
    });

    const response = {
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issue.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.patch("/api/issues/:issueId/milestones", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { milestone: milestoneId } = await req.json();

    const issue = issues.find((i) => i.id === Number(issueId));

    if (!issue) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    issue.milestone =
      milestones.data.milestones.find((m) => m.id === milestoneId) ?? null;

    const response = {
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        modifiedIssueId: issue.id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  rest.delete("/api/issues/:issueId", async (req, res, ctx) => {
    const { issueId } = req.params;

    const issueIndex = issues.findIndex((i) => i.id === Number(issueId));

    if (issueIndex === -1) {
      const notFoundError = {
        code: 404,
        status: "Not Found",
        message: "이슈를 찾을 수 없습니다.",
        data: null,
      };

      return res(ctx.status(404), ctx.json(notFoundError));
    }

    const deletedIssue = issues.splice(issueIndex, 1);

    const response = {
      code: 200,
      status: "OK",
      messages: "OK",
      data: {
        deletedIssueId: deletedIssue[0].id,
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
];

export const issues: Issue[] = [
  {
    id: 2,
    title: "제목1",
    status: "OPENED",
    statusModifiedAt: "2023-08-09T06:01:37",
    createdAt: "2023-08-09T06:01:37",
    modifiedAt: "2023-08-10T06:01:37",
    content: `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Ultricies mi quis hendrerit dolor magna eget. Purus ut faucibus pulvinar elementum integer. Suspendisse ultrices gravida dictum fusce ut placerat orci. Auctor elit sed vulputate mi sit. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus. Nunc sed blandit libero volutpat. Elementum eu facilisis sed odio morbi quis commodo. Sed augue lacus viverra vitae congue eu consequat ac felis.

      Eu volutpat odio facilisis mauris sit amet massa vitae. Ut eu sem integer vitae. Id leo in vitae turpis massa sed elementum tempus. Arcu non sodales neque sodales ut etiam. Arcu non odio euismod lacinia at quis risus sed. Eget mauris pharetra et ultrices neque ornare. Neque vitae tempus quam pellentesque nec nam. Nulla at volutpat diam ut venenatis tellus in metus. Faucibus in ornare quam viverra orci sagittis. Leo in vitae turpis massa sed elementum tempus. Augue mauris augue neque gravida in fermentum et sollicitudin ac. Lobortis elementum nibh tellus molestie nunc non blandit massa. Suspendisse in est ante in nibh mauris cursus. Sed lectus vestibulum mattis ullamcorper velit sed. Lectus proin nibh nisl condimentum id venenatis a condimentum vitae. At lectus urna duis convallis convallis tellus id. Scelerisque eleifend donec pretium vulputate. Est pellentesque elit ullamcorper dignissim cras tincidunt. Leo a diam sollicitudin tempor id eu nisl nunc mi.
      
      Magna sit amet purus gravida. Duis convallis convallis tellus id interdum velit laoreet id. Interdum consectetur libero id faucibus nisl tincidunt. Hac habitasse platea dictumst quisque sagittis purus sit amet. Tempus urna et pharetra pharetra massa. Egestas erat imperdiet sed euismod nisi porta lorem. Mauris pellentesque pulvinar pellentesque habitant morbi. Nibh ipsum consequat nisl vel pretium lectus quam. Purus sit amet luctus venenatis lectus magna fringilla urna porttitor. Vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Imperdiet nulla malesuada pellentesque elit eget gravida cum sociis. Convallis posuere morbi leo urna molestie at elementum. Accumsan tortor posuere ac ut consequat semper viverra nam libero. Non tellus orci ac auctor augue mauris augue. Vulputate ut pharetra sit amet aliquam id. Id interdum velit laoreet id. Ut venenatis tellus in metus vulputate. Egestas sed tempus urna et pharetra pharetra massa massa ultricies. Tristique senectus et netus et malesuada fames ac turpis. A iaculis at erat pellentesque adipiscing commodo elit at.`,
    reactions: [
      {
        unicode: "&#128077",
        users: ["wis730", "wisdom"],
        selectedUserReactionId: 1,
      },
      {
        unicode: "&#128078",
        users: [],
        selectedUserReactionId: 0,
      },
    ],
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
      name: "hong1234",
      avatarUrl:
        "https://i.namu.wiki/i/2KCzxN-etRYFHRQVHH9-ROMgsOvyU3X4y7A2wuqNV5apHBn8APhnhPuapmrXewTuRcVtgos2UIAdHOCDkKVOFQ.webp",
    },
    comments: [],
  },
  {
    id: 5,
    title: "테스트",
    status: "OPENED",
    statusModifiedAt: "2023-08-09T06:01:37",
    createdAt: "2023-08-09T06:01:37",
    modifiedAt: "2023-08-10T06:01:37",
    content: `테스트`,
    reactions: [
      {
        unicode: "&#128077",
        users: ["wis730", "wisdom"],
        selectedUserReactionId: 1,
      },
      {
        unicode: "&#128078",
        users: [],
        selectedUserReactionId: 0,
      },
    ],
    assignees: [],
    labels: [],
    milestone: null,
    writer: {
      id: 2,
      name: "lee1234",
      avatarUrl: "https://pbs.twimg.com/media/EUplmpsU0AcR9jc.jpg",
    },
    comments: [],
  },
];
