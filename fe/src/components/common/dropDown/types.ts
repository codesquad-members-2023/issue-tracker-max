/* 더미데이터 */
export const issueFilterList = [
  { filterId: 1, name: '열린 이슈' },
  { filterId: 2, name: '내가 작성한 이슈' },
  { filterId: 3, name: '나에게 할당된 이슈' },
  { filterId: 4, name: '내가 댓글을 남긴 이슈' },
  { filterId: 5, name: '닫힌 이슈' },
];

export const issueStateList = [
  { issueStateId: 1, name: '열린 이슈' },
  { issueStateId: 2, name: '닫힌 이슈' },
];

export const contributors = [
  {
    userId: 0,
    loginId: 'none',
    image: '',
  },
  {
    userId: 1,
    loginId: 'bono1234',
    image:
      'https://item.kakaocdn.net/do/6554be44bf80540c7afa12cf4d9b9b7e9f5287469802eca457586a25a096fd31',
  },
  {
    userId: 2,
    loginId: 'ayaan1234',
    image:
      'https://item.kakaocdn.net/do/6554be44bf80540c7afa12cf4d9b9b7e9f5287469802eca457586a25a096fd31',
  },
  {
    userId: 3,
    loginId: 'gunoc',
    image:
      'https://item.kakaocdn.net/do/6554be44bf80540c7afa12cf4d9b9b7e9f5287469802eca457586a25a096fd31',
  },
];

export const labels = [
  {
    labelId: 0,
    name: 'none',
    textColor: '',
    backgroundColor: '',
  },
  {
    labelId: 1,
    name: 'feat',
    textColor: 'light',
    backgroundColor: '#FF0000',
  },
  {
    labelId: 2,
    name: 'fix',
    textColor: 'light',
    backgroundColor: '#800000',
  },
  {
    labelId: 3,
    name: 'refactor',
    textColor: 'dark',
    backgroundColor: '#fff6a6',
  },
  {
    labelId: 4,
    name: 'style',
    textColor: 'dark',
    backgroundColor: '#cbf5c1',
  },
  {
    labelId: 5,
    name: 'document',
    textColor: 'dark',
    backgroundColor: '#e5f55b',
  },
  {
    labelId: 6,
    name: 'style',
    textColor: 'dark',
    backgroundColor: '#8d97f2',
  },
  {
    labelId: 7,
    name: 'frontend',
    textColor: 'dark',
    backgroundColor: '#d1a7d0',
  },
];

export const milestones = [
  {
    milestoneId: 0,
    name: 'none',
    progress: 0,
  },
  {
    milestoneId: 1,
    name: '마일스톤1',
    progress: 34,
  },
  {
    milestoneId: 2,
    name: '마일스톤2',
    progress: 78,
  },
  {
    milestoneId: 3,
    name: '마일스톤3',
    progress: 14,
  },
  {
    milestoneId: 4,
    name: '마일스톤4',
    progress: 58,
  },
];

export const textColors = [
  { textColorsId: 1, name: '밝은색' },
  { textColorsId: 2, name: '어두운 색' },
];
