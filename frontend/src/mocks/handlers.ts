import { rest } from "msw";

type LabelRequestBody = {
  name: string;
  color: string;
  background: string;
  description: string | null;
};

type MilestoneRequestBody = {
  name: string;
  deadline: string;
  description: string;
};

export const handlers = [
  rest.get("/api/labels", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(labels));
  }),
  rest.post("/api/labels", (req, res, ctx) => {
    const body = req.body as LabelRequestBody;

    labels.data.labels.push({ ...body, id: labels.data.labels.length });

    return res(ctx.status(200), ctx.json(labels));
  }),
  rest.put("/api/labels/:id", (req, res, ctx) => {
    const body = req.body as LabelRequestBody;
    const { id } = req.params;

    labels.data.labels = labels.data.labels.map((label) => {
      return label.id === Number(id) ? { ...body, id: Number(id) } : label;
    });

    return res(ctx.status(200), ctx.json(labels));
  }),
  rest.delete("/api/labels/:id", (req, res, ctx) => {
    const { id } = req.params;

    labels.data.labels = labels.data.labels.filter((label) => {
      return label.id !== Number(id);
    });

    return res(ctx.status(200), ctx.json(labels));
  }),
  rest.get("/api/milestones", (req, res, ctx) => {
    const state = req.url.searchParams.get("state");

    if (state === "opened") {
      return res(ctx.status(200), ctx.json(openedMilestones));
    } else if (state === "closed") {
      return res(ctx.status(200), ctx.json(closedMilestones));
    }
  }),
  rest.post("/api/milestones", (req, res, ctx) => {
    const body = req.body as MilestoneRequestBody;

    const obj = {
      id: openedMilestones.data.milestones.length,
      createdAt: "2023-08-07T15:33:37",
      modifiedAt: null,
      issues: {
        openedIssueCount: 0,
        closedIssueCount: 0,
      },
      ...body,
    };
    openedMilestones.data.milestones.push(obj);

    return res(ctx.status(200), ctx.json(labels));
  }),
  rest.put("/api/milestones/:id", (req) => {
    const { id } = req.params;

    const body = req.body as MilestoneRequestBody;
    const obj = {
      id: Number(id),
      createdAt: "2023-08-07T15:33:37",
      modifiedAt: "2023-08-07T15:33:37",
      issues: {
        openedIssueCount: 0,
        closedIssueCount: 0,
      },
      ...body,
    };

    if (Number(id) >= 20) {
      closedMilestones.data.milestones = closedMilestones.data.milestones.map(
        (milestone) => {
          return milestone.id === Number(id) ? obj : milestone;
        },
      );
    } else {
      openedMilestones.data.milestones = openedMilestones.data.milestones.map(
        (milestone) => {
          return milestone.id === Number(id) ? obj : milestone;
        },
      );
    }
  }),
  rest.delete("/api/milestones/:id", (req, res, ctx) => {
    const { id } = req.params;

    if (Number(id) >= 20) {
      closedMilestones.data.milestones =
        closedMilestones.data.milestones.filter((milestone) => {
          return milestone.id !== Number(id);
        });
      return res(ctx.status(200), ctx.json(closedMilestones));
    } else {
      openedMilestones.data.milestones =
        openedMilestones.data.milestones.filter((milestone) => {
          return milestone.id !== Number(id);
        });
      return res(ctx.status(200), ctx.json(openedMilestones));
    }
  }),
  rest.get("/api/issues/:issueId", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(issue));
  }),
  rest.patch("/api/issues/:issueId/title", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { title } = await req.json();

    issue.data.title = title;

    return res(ctx.status(200), ctx.json(issue));
  }),
  rest.patch("/api/issues/:issueId/status", async (req, res, ctx) => {
    const { issueId } = req.params;
    const { status } = await req.json();

    issue.data = {
      ...issue.data,
      status,
      statusModifiedAt: new Date().toISOString(),
    }

    return res(ctx.status(200), ctx.json(issue));
  })
];

const labels = {
  code: 200,
  status: "OK",
  message: "라벨 조회에 성공했습니다.",
  data: {
    openedMilestoneCount: 3,
    labelCount: 5,
    labels: [
      {
        id: 1,
        name: "docs",
        color: "DARK",
        background: "#FEFEFE",
        description: "문서 관련 작업",
      },
      {
        id: 2,
        name: "documentation",
        color: "LIGHT",
        background: "#0025E6",
        description: null,
      },
      {
        id: 3,
        name: "test",
        color: "Light",
        background: "#000000",
        description: null,
      },
      {
        id: 4,
        name: "review",
        color: "Light",
        background: "#000000",
        description: "리뷰 필요",
      },
      {
        id: 5,
        name: "bug",
        color: "LIGHT",
        background: "#FF3B30",
        description: null,
      },
    ],
  },
};

const openedMilestones = {
  code: 200,
  status: "OK",
  message: "마일스톤 조회에 성공했습니다.",
  data: {
    openedMilestoneCount: 3,
    closedMilestoneCount: 1,
    labelCount: 5,
    status: "OPENED",
    milestones: [
      {
        id: 1,
        name: "Sprint#1",
        description: "중복 수정 스프린트 1주차입니다.",
        createdAt: "2023-08-07T15:33:37",
        modifiedAt: "2023-08-08T14:46:14",
        deadline: "2023-07-08",
        issues: {
          openedIssueCount: 22,
          closedIssueCount: 10,
        },
      },
      {
        id: 13,
        name: "Sprint#112",
        description: "스프린트 2주차입니다.",
        createdAt: "2023-08-08T14:39:29",
        modifiedAt: null,
        deadline: "2023-07-18",
        issues: {
          openedIssueCount: 2,
          closedIssueCount: 7,
        },
      },
      {
        id: 15,
        name: "Sprint#113",
        description: "스프린트 3주차입니다.",
        createdAt: "2023-08-08T14:43:51",
        modifiedAt: null,
        deadline: null,
        issues: {
          openedIssueCount: 1,
          closedIssueCount: 0,
        },
      },
    ],
  },
};

const closedMilestones = {
  code: 200,
  status: "OK",
  message: "마일스톤 조회에 성공했습니다.",
  data: {
    openedMilestoneCount: 3,
    closedMilestoneCount: 1,
    labelCount: 5,
    status: "CLOSED",
    milestones: [
      {
        id: 21,
        name: "Sprint#1",
        description: "중복 수정 스프린트 1주차입니다.",
        createdAt: "2023-08-07T15:33:37",
        modifiedAt: "2023-08-08T14:54:32",
        deadline: "2023-07-28",
        issues: {
          openedIssueCount: 0,
          closedIssueCount: 9,
        },
      },
      {
        id: 22,
        name: "Sprint#1",
        description: "중복 수정 스프린트 2주차입니다.",
        createdAt: "2023-08-17T15:33:37",
        modifiedAt: "2023-08-18T14:54:32",
        deadline: "2023-07-28",
        issues: {
          openedIssueCount: 0,
          closedIssueCount: 0,
        },
      },
    ],
  },
};

const issue = {
  code: 200,
  status: "OK",
  message: "OK",
  data: {
    id: 2,
    title: "제목1",
    status: "OPENED",
    statusModifiedAt: "2023-08-09T06:01:37",
    createdAt: "2023-08-09T06:01:37",
    modifiedAt: null,
    content: "이슈 내용1",
    assignees: [
      {
        id: 1,
        name: "wis730",
        avatarUrl: null,
      },
    ],
    labels: [
      {
        id: 1,
        name: "testdd",
        background: "#000000",
        color: "LIGHT",
      },
    ],
    milestone: {
      id: 1,
      name: "Sprint#1",
      issues: {
        openedIssueCount: 1,
        closedIssueCount: 0,
      },
    },
    writer: {
      id: 1,
      name: "wis730",
      avatarUrl: null,
    },
    comments: [
      {
        id: 1,
        userId: "wis730",
        avatarUrl: null,
        content: "이 댓글을 읽은 당신은 앞으로 모든 일이 신기하게 잘풀립니다!",
        createdAt: "2023-08-09T15:21:09",
        modifiedAt: null,
      },
    ],
  },
};
