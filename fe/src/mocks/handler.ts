import { rest } from 'msw';

import { subFilters } from './data/subFilters';

const handlers = [
  // rest.get('/api', (_, res, ctx) => {
  //   return res(ctx.status(200), ctx.json());
  // }),

  rest.get('/api/filters', (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(subFilters));
  }),

  // rest.delete('/api/history', (_, res, ctx) => {
  //   // 모든 데이터를 삭제한 후의 상태를 반환
  //   actionHistory.message = [];
  //   return res(ctx.status(200), ctx.json(actionHistory));
  // }),

  // rest.delete('/api/task/:taskId', (req, res, ctx) => {
  //   const { taskId } = req.params;
  //   return res(
  //     ctx.status(200),
  //     ctx.json({
  //       statusCode: 200,
  //       message: `카드${taskId} 삭제 성공`,
  //     }),
  //   );
  // }),

  // rest.patch('/api/task/:taskId', (_, res, ctx) => {
  //   // const { taskId } = req.params;
  //   // const testBody = req.body;
  //   // console.log(testBody);

  //   return res(
  //     ctx.json({
  //       statusCode: 200,
  //       message: '카드 수정 성공',
  //     }),
  //   );
  // }),

  // rest.post('/api/task', (req, res, ctx) => {
  //   const newTask: any = req.body;
  //   const taskId = Math.floor(Math.random() * 1000) + 1;

  //   return res(
  //     ctx.json({
  //       statusCode: 200,
  //       message: {
  //         taskId: taskId,
  //         processId: newTask.processId,
  //         title: newTask.title,
  //         contents: newTask.contents,
  //         platform: newTask.platform,
  //       },
  //     }),
  //     ctx.status(200),
  //   );
  // }),

  // rest.patch('/api/process/:processId', (req, res, ctx) => {
  //   const { processId } = req.params;

  //   return res(
  //     ctx.json({
  //       statusCode: 200,
  //       message: `컬럼 ${processId} 업데이트 성공`,
  //     }),
  //     ctx.status(200),
  //   );
  // }),

  // rest.post('/api/process', (req, res, ctx) => {
  //   if (
  //     typeof req.body === 'object' &&
  //     req.body !== null &&
  //     'processName' in req.body
  //   ) {
  //     const newProcess: any = req.body.processName;

  //     return res(
  //       ctx.json({
  //         statusCode: 200,
  //         message: `컬럼 ${newProcess} 생성 성공`,
  //       }),
  //       ctx.status(200),
  //     );
  //   } else {
  //     return res(ctx.status(400), ctx.json({ error: 'Invalid request body' }));
  //   }
  // }),

  // rest.delete('/api/process/:processId', (req, res, ctx) => {
  //   const { processId } = req.params;

  //   return res(
  //     ctx.json({
  //       statusCode: 200,
  //       message: `컬럼 ${processId} 삭제 성공`,
  //     }),
  //     ctx.status(200),
  //   );
  // }),
];

export default handlers;
