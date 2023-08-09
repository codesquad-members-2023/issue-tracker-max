export type Label = {
  labelId: number;
  name: string;
  fontColor: string;
  backgroundColor: string;
  description: string;
};

export type Milestone = {
  milestoneId: number;
  milestoneName: string;
  openIssueCount: number;
  closedIssueCount: number;
};

export type Assignee = {
  username: string;
  profileUrl: string;
};

// TODO: labels, milestone, assignees optional 처리 필요
export type IssueItem = {
  issueNumber: number;
  isOpen: boolean;
  title: string;
  authorName: string;
  createdAt: string;
  labels: Label[];
  milestone: string;
  assignees: Assignee[];
};

export type User = {
  userAccountId: number;
  username: string;
  profileUrl: string;
};

export type IssueInfo = {
  title: string;
  content: string;
  assignees: Set<number>;
  labels: Set<number>;
  milestone: number;
};

export type IssueDetails = {
  issueId: number;
  title: string;
  isOpen: boolean;
  createdAt: string;
  author: {
    username: string;
    profileUrl: string;
  };
  content: string;
};

export type IssueSidebar = {
  assignees: number[];
  labels: number[];
  milestone: number;
};

export type IssueComment = {
  commentId: number;
  username: string;
  profileUrl: string;
  content: string;
  createdAt: string;
};
