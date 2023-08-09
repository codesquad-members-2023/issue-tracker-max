import { rest } from 'msw';

import { selectList } from './data/selectList';
import { issues } from './data/issues';

let currentId = 1;

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

  rest.post('/issues/new', (_, res, ctx) => {
    currentId += 1;
    return res(
      ctx.status(200),
      ctx.json({ id: currentId, message: '이슈가 성공적으로 등록되었습니다.' }),
    );
  }),

  rest.get('/issues', (req, res, ctx) => {
    if (req.url.searchParams.get('query') === 'status:closed') {
      return res(ctx.status(200), ctx.json(issues.closed));
    }

    if (
      !req.url.searchParams.get('query') ||
      req.url.searchParams.get('query') === 'status:open'
    ) {
      return res(ctx.status(200), ctx.json(issues.open));
    }

    return res(ctx.status(404));
  }),
];
