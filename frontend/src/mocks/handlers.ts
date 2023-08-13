import { rest } from 'msw';

export const handlers = [
  rest.post('/api/signup', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        statusCode: 200,
        message: '요청 성공',
      })
    );
  }),
  rest.post('/api/login', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(successLogin));
  }),
  rest.post('/api/reissue/token', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(newAccessToken));
  }),
  rest.get('/api/login/github', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (code) {
      return res(ctx.status(200), ctx.json(successGitHubLogin));
    } else {
      return res(ctx.status(400), ctx.json({ message: '잘못된 코드 값' }));
    }
  }),
  rest.post('/api/logout', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        statusCode: 200,
        message: '요청 성공',
      })
    );
  }),
  rest.get('/api/labels', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(labelData));
  }),
];

const successLogin = {
  statusCode: 200,
  message: {
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
  statusCode: 200,
  message: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkxlbyBLaW0iLCJpYXQiOjE1MTYyMzkwMjJ9.ZfseO7je1qHjBQgT122YZ-OvCMXUQ5NOkVZM8k9P2eU',
  },
};

const successGitHubLogin = {
  statusCode: 200,
  message: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c',
    refreshToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c',
    userId: 1,
    userName: 'silvertae',
    userProfileImg: 'https://f1.kina.or.kr/2020/11/jtqgmmu4i3.jpg',
  },
};

const labelData = {
  statusCode: 200,
  message: {
    milestoneCount: 2,
    labels: [
      {
        id: 1,
        textColor: 'AAA333',
        backgroundColor: 'FFF3FF',
        name: 'feat',
        description: '기능 추가',
      },
      {
        id: 2,
        textColor: 'FEFEFE',
        backgroundColor: '333333',
        name: 'fix',
        description: '버그 수정',
      },
      {
        id: 3,
        textColor: 'AAA333',
        backgroundColor: 'FFF3FF',
        name: 'refactor',
        description: '리팩터링',
      },
    ],
  },
};
