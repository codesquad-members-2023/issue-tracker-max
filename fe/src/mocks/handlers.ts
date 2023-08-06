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

  rest.post('/file-upload', (_, res, ctx) => {
    return res(
      ctx.delay(500), // 200ms 딜레이 추가
      ctx.status(200),
      ctx.json({ message: 'File uploaded successfully!' }),
    );
  }),
];
