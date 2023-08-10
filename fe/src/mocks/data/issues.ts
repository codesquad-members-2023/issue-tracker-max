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
        assigneeImages: [
          '',
          '',
          'https://avatars.githubusercontent.com/u/57666791?v=4',
          'https://avatars.githubusercontent.com/u/86706366?s=80&v=4',
          'https://avatars.githubusercontent.com/u/57559288?s=80&u=22fcaa63715a65dfa747506fffe592b0acbb2846&v=4',
          'https://avatars.githubusercontent.com/u/117690393?s=80&u=ba9f18d1ab53f87cbe07a308e103d26d6bcbf221&v=4',
          'https://avatars.githubusercontent.com/u/70848762?s=80&v=4',
          'https://avatars.githubusercontent.com/u/97204689?s=80&u=34888415e252f727b1d3a849e7f1387a20ce3696&v=4',
        ],
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
        createdAt: '2023-08-08T02:53:00',
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

  assigneeFiltered: {
    labelCount: 10,
    milestoneCount: 20,
    openIssueCount: 10,
    closedIssueCount: 30,
    issues: [
      {
        id: 4,
        title: '보노가 쓴 이슈임',
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
      {
        id: 5,
        title: '보노가 쓴 이슈 123123123',
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
};
