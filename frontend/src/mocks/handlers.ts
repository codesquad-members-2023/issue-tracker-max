import { rest } from 'msw';

export const handlers = [
  rest.post('/api/signup', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        statusCode: 200,
        messages: '요청 성공',
      })
    );
  }),
  rest.post('/api/login', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successLogin));
  }),
  rest.post('/api/reissue/token', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(newAccessToken));
  }),
  rest.post('/api/login/github?code={}', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successGitHubLogin));
  }),
  rest.post('/api/logout', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        statusCode: 200,
        messages: '요청 성공',
      })
    );
  }),
];

const successLogin = {
  statusCode: 200,
  messages: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c',
    refreshToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJyZWZyZXNoIHRva2VuIiwibmFtZSI6IkNvZGVTcXVhZCIsImlhdCI6MTUxNjIzOTAyMn0._VcNtq7pIrC18BZ2OEgkiYosqimrCGzOAwTKPdMR_7g',
    userId: 1,
    userName: 'silvertae',
    userProfileImg: 'https://f1.kina.or.kr/2020/11/jtqgmmu4i3.jpg',
  },
};

const newAccessToken = {
  accessToken:
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkxlbyBLaW0iLCJpYXQiOjE1MTYyMzkwMjJ9.ZfseO7je1qHjBQgT122YZ-OvCMXUQ5NOkVZM8k9P2eU',
};

const successGitHubLogin = {
  statusCode: 200,
  messages: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c',
    refreshToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c',
    userId: 1,
    userName: 'silvertae',
    userProfileImg: 'https://f1.kina.or.kr/2020/11/jtqgmmu4i3.jpg',
  },
};
