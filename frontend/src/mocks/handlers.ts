import { rest } from "msw";

export const handlers = [
  rest.get("/api/labels", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(labels));
  }),
];

const labels = {
  openedMilestoneCount: 1,
  labelCount: 3,
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
};
