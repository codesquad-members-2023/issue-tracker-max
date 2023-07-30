export enum DropDownIndicatorName {
  issueFilter = '이슈 필터',
  assignee = '담당자',
  author = '작성자',
  label = '레이블',
  milestone = '마일스톤',
  issueState = '상태 수정',
  textColor = '텍스트 색상',
}

export type DropDownIndicatorNameType = keyof typeof DropDownIndicatorName;

export const issueFilterList = [
  '열린 이슈',
  '내가 작성한 이슈',
  '나에게 할당된 이슈',
  '내가 댓글을 남긴 이슈',
  '닫힌 이슈',
];

export const issueStateList = ['열린 이슈', '닫힌 이슈'];

export const contributors = [
  {
    id: 'bono1234',
    image: '이미지url',
  },
  {
    id: 'ayaan1234',
    image: '이미지url',
  },
];

export const labels = [
  {
    labelId: 1,
    name: 'feat',
    textColor: 'light',
    backgroundColor: '#FF0000',
  },
  {
    labelId: 2,
    name: 'fix',
    textColor: 'dark',
    backgroundColor: '#800000',
  },
];

export const milestones = [
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
];

export const textColors = ['밝은색', '어두운 색'];
