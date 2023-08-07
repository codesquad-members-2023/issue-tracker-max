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

  rest.post('/issues/new', (req, res, ctx) => {
    const bodyData = JSON.parse(req.body as string);

    if (bodyData.content === '') {
      //에러 확인용 조건
      return res(
        ctx.status(400),
        ctx.json({ message: '필요한 필드가 누락되었습니다.' }),
      );
    }

    return res(
      ctx.status(200),
      ctx.json({ message: '이슈가 성공적으로 등록되었습니다.' }),
    );
  }),
];
