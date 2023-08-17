import { rest } from "msw";
import { assigneeHandlers } from "./assigneeHandlers";
import { commentHandlers } from "./commentHandlers";
import { imageHandlers } from "./imageHandlers";
import { issueHandlers } from "./issueHandlers";

type LabelRequestBody = {
  name: string;
  color: string;
  background: string;
  description: string;
};

type MilestoneRequestBody = {
  name: string;
  deadline: string;
  description: string;
};

type UserRequestBody = {
  loginId: string;
  email: string;
  password: string;
  passwordConfirm: string;
};

type LoginRequestBody = {
  loginId: string;
  password: string;
};

type UsersData = {
  user: {
    id: number;
    loginId: string;
    password: string;
    passwordConfirm: string;
    email: string;
    avatarUrl: string | null;
  };
  jwt: {
    accessToken: string;
    refreshToken: string;
  };
}[];

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
    } else {
      return res(ctx.status(200), ctx.json(milestones));
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
  rest.post("/api/users", (req, res, ctx) => {
    const body: UserRequestBody =
      typeof req.body === "string" ? JSON.parse(req.body) : req.body;
    const user = users.find(({ user }) => {
      {
        return user.loginId === body.loginId || user.email === body.email;
      }
    });
    if (!user) {
      const obj = {
        user: { id: users.length, avatarUrl: null, ...body },
        jwt: {
          accessToken: `${body.loginId}AccessToken`,
          refreshToken: `${body.loginId}RefreshToken`,
        },
      };

      const userRes = {
        code: 201,
        status: "CREATED",
        message: "회원가입에 성공하였습니다.",
        data: {
          id: obj.user.id,
          loginId: obj.user.loginId,
          email: obj.user.email,
        },
      };

      users.push(obj);

      return res(ctx.status(200), ctx.json(userRes));
    } else {
      const errorResponse = {
        code: 409,
        status: "CONFLICT",
        message: "이미 존재하는 아이디 또는 이메일입니다.",
        data: null,
      };

      return res(ctx.status(409), ctx.json(errorResponse));
    }
  }),
  rest.post("/api/login", (req, res, ctx) => {
    const body: LoginRequestBody =
      typeof req.body === "string" ? JSON.parse(req.body) : req.body;

    const user = users.find(({ user }) => {
      {
        return user.loginId === body.loginId && user.password === body.password;
      }
    });

    if (user) {
      const response = {
        code: 200,
        status: "OK",
        message: "로그인에 성공하였습니다.",
        data: {
          user: {
            id: user.user.id,
            loginId: user.user.loginId,
            email: user.user.email,
            avatarUrl: user.user.avatarUrl,
          },
          jwt: {
            ...user.jwt,
          },
        },
      };

      return res(ctx.status(200), ctx.json(response));
    } else {
      const errorResponse = {
        code: 400,
        status: "BAD_REQUEST",
        message: "아이디 또는 비밀번호가 일치하지 않습니다.",
        data: null,
      };

      return res(ctx.status(400), ctx.json(errorResponse));
    }
  }),
  rest.get("/api/issues", (req, res, ctx) => {
    const queryParams: { [key: string]: string[] } = {};

    for (const [key, value] of req.url.searchParams.entries()) {
      if (!queryParams[key]) {
        queryParams[key] = [];
      }
      queryParams[key].push(value);
    }

    const newInput = Object.entries(queryParams)
      .map(([key, values]) => {
        return values.map((value) => `${key}:${value}`).join(" ");
      })
      .join(" ");

    issues.data.input = newInput === "" ? "is:opened" : newInput;

    return res(ctx.status(200), ctx.json(issues));
  }),
  rest.post("/api/issues/status", (req, res, ctx) => {
    const body = JSON.parse(req.body as string) as {
      issues: number[];
      status: "OPENED" | "CLOSED";
    };

    issues.data.issues.forEach((issue) => {
      issue.status = body.status;
    });

    return res(
      ctx.status(200),
      ctx.json({
        code: 200,
        status: "OK",
        message: "OK",
        data: {
          modifiedIssueId: body.issues,
        },
      }),
    );
  }),
  rest.post("/login/oauth/github", (_, res, ctx) => {
    const response = {
      code: 200,
      status: "OK",
      message: "로그인에 성공하였습니다.",
      data: {
        ...users[1],
      },
    };

    return res(ctx.status(200), ctx.json(response));
  }),
  ...issueHandlers,
  ...commentHandlers,
  ...assigneeHandlers,
  ...imageHandlers,
];

export const labels = {
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
        description: "",
      },
      {
        id: 3,
        name: "test",
        color: "Light",
        background: "#000000",
        description: "",
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
        description: "",
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

export const milestones = {
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

export const users: UsersData = [
  {
    user: {
      id: 0,
      loginId: "test123",
      password: "12341234",
      passwordConfirm: "12341234",
      email: "test123@test.com",
      avatarUrl: "https://avatars.githubusercontent.com/u/41321198?v=4",
    },
    jwt: {
      accessToken: "test123AccessToken",
      refreshToken: "test123RefreshToken",
    },
  },
  {
    user: {
      id: 0,
      loginId: "test1234",
      password: "12341234",
      passwordConfirm: "12341234",
      email: "test1234@test.com",
      avatarUrl: "https://avatars.githubusercontent.com/u/105152276?v=4",
    },
    jwt: {
      accessToken: "test1234AccessToken",
      refreshToken: "test1234RefreshToken",
    },
  },
];

const issues = {
  code: 200,
  status: "OK",
  message: "OK",
  data: {
    input: "is:opened",
    openedIssueCount: 2,
    closedIssueCount: 1,
    labelCount: 5,
    milestoneCount: 3,
    issues: [
      {
        id: 1,
        title: "test issue 1",
        status: "OPENED",
        createdAt: "2023-08-07T10:42:27",
        modifiedAt: "2023-08-10T00:15:20",
        statusModifiedAt: "2023-08-10T00:15:20",
        assignees: [
          {
            id: 1,
            name: "yeon",
            avatarUrl: "",
          },
          {
            id: 2,
            name: "jay",
            avatarUrl: "https://avatars.githubusercontent.com/u/41321198?v=4",
          },
        ],
        labels: [
          {
            id: 1,
            name: "bug",
            color: "#FFFFFF",
            background: "#000000",
          },
          {
            id: 2,
            name: "docs",
            color: "#FFFFFF",
            background: "#000000",
          },
        ],
        milestones: {
          id: 1,
          name: "week 1",
        },
        author: {
          id: 1,
          name: "yeon",
        },
        commentCount: 3,
      },
      {
        id: 2,
        title: "test issue222222222222",
        status: "OPENED",
        createdAt: "2023-08-07T10:42:27",
        modifiedAt: "2023-08-10T00:15:20",
        statusModifiedAt: "2023-08-10T00:15:20",
        assignees: [
          {
            id: 1,
            name: "yeon",
            avatarUrl: "",
          },
          {
            id: 2,
            name: "jay",
            avatarUrl: "https://avatars.githubusercontent.com/u/41321198?v=4",
          },
          {
            id: 3,
            name: "jello",
            avatarUrl: "",
          },
        ],
        labels: [
          {
            id: 1,
            name: "bug",
            color: "#FFFFFF",
            background: "#000000",
          },
          {
            id: 2,
            name: "docs",
            color: "#FFFFFF",
            background: "#000000",
          },
        ],
        milestones: {
          id: 1,
          name: "week 1",
        },
        author: {
          id: 1,
          name: "yeon",
        },
        commentCount: 3,
      },
    ],
    singleFilters: [
      {
        id: 1,
        name: "열린 이슈",
        conditions: "is:opened",
        selected: false,
      },
      {
        id: 2,
        name: "내가 작성한 이슈",
        conditions: "is:opened author:@me",
        selected: false,
      },
      {
        id: 3,
        name: "나에게 할당된 이슈",
        conditions: "is:opened assignee:@me",
        selected: false,
      },
      {
        id: 4,
        name: "내가 댓글을 남긴 이슈",
        conditions: "is:opened mentions:@me",
        selected: false,
      },
      {
        id: 5,
        name: "닫힌 이슈",
        conditions: "is:closed",
        selected: false,
      },
    ],
    multiFilters: {
      assignees: {
        multipleSelect: false,
        options: [
          {
            id: 0,
            name: "담당자가 없는 이슈",
            avatarUrl: null,
            selected: false,
          },
          {
            id: 1,
            name: "yeon",
            avatarUrl: "url path",
            selected: false,
          },
        ],
      },

      labels: {
        multipleSelect: true,
        options: [
          {
            id: 0,
            name: "레이블이 없는 이슈",
            selected: false,
          },
          {
            id: 1,
            name: "bug",
            color: "#FFFFFF",
            background: "#000000",
            selected: false,
          },
          {
            id: 2,
            name: "docs",
            color: "#FFFFFF",
            background: "#000000",
            selected: false,
          },
          {
            id: 3,
            name: "feature",
            color: "#FFFFFF",
            background: "#000000",
            selected: false,
          },
        ],
      },
      milestones: {
        multipleSelect: false,
        options: [
          {
            id: 0,
            name: "마일스톤이 없는 이슈",
            selected: false,
          },
          {
            id: 1,
            name: "week 1",
            selected: false,
          },
          {
            id: 2,
            name: "week 2",
            selected: false,
          },
        ],
      },
      authors: {
        multipleSelect: false,
        options: [
          {
            id: 1,
            name: "yeon",
            avatarUrl: "url path",
            selected: false,
          },
          {
            id: 2,
            name: "jay",
            avatarUrl: "url path",
            selected: false,
          },
          {
            id: 3,
            name: "sully",
            avatarUrl: "url path",
            selected: false,
          },
          {
            id: 4,
            name: "hong1234",
            avatarUrl: null,
            selected: false,
          },
        ],
      },
    },
  },
};
