export const issues = {
  open: {
    labelCount: 5,
    milestoneCount: 2,
    openIssueCount: 1,
    closedIssueCount: 3,
    issues: [
      {
        id: 1,
        title: '이슈 목록 조회 기능 구현',
        authorLoginId: 'bono1234',
        assigneeImages: ['기본 이미지 url', '기본 이미지 url'],
        labels: [
          {
            id: 1,
            name: 'feat',
            textColor: 'light',
            backgroundColor: '#FF0000',
          },
          {
            id: 3,
            name: 'fix',
            textColor: 'dark',
            backgroundColor: '#800000',
          },
        ],
        milestone: {
          id: 1,
          name: '마일스톤1',
        },
        createdAt: '2023-07-25T02:53:00',
        status: 'open',
      },
    ],
  },

  closed: {
    labelCount: 5,
    milestoneCount: 2,
    openIssueCount: 1,
    closedIssueCount: 3,
    issues: [
      {
        id: 4,
        title: '이것은 닫힌 이슈',
        authorLoginId: 'bono1234',
        assigneeImages: ['기본 이미지 url', '기본 이미지 url'],
        labels: [
          {
            id: 1,
            name: 'feat',
            textColor: 'light',
            backgroundColor: '#FF0000',
          },
          {
            id: 3,
            name: 'fix',
            textColor: 'dark',
            backgroundColor: '#800000',
          },
        ],
        milestone: {
          id: 1,
          name: '마일스톤1',
        },
        createdAt: '2023-07-25T02:53:00',
        status: 'closed',
      },
      {
        id: 5,
        title: '이것도 닫힌 이슈',
        authorLoginId: 'bono1234',
        assigneeImages: ['기본 이미지 url', '기본 이미지 url'],
        labels: [
          {
            id: 1,
            name: 'feat',
            textColor: 'light',
            backgroundColor: '#FF0000',
          },
          {
            id: 3,
            name: 'fix',
            textColor: 'dark',
            backgroundColor: '#800000',
          },
        ],
        milestone: {
          id: 1,
          name: '마일스톤1',
        },
        createdAt: '2023-07-25T02:53:00',
        status: 'closed',
      },
    ],
  },
};
