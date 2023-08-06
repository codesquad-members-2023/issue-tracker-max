import { rest } from 'msw';

import { selectList } from './data/selectList';

export const handlers = [
  rest.get('/users/previews', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(selectList.users));
  }),

  rest.get('/labels/previews', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(selectList.labels));
  }),

  rest.get('/milestones/previews', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(selectList.milestones));
  }),
];
