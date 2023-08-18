export type Label = {
  labelId: number;
  name: string;
  fontColor: string;
  backgroundColor: string;
  description?: string;
};

export type Milestone = {
  milestoneId: number;
  milestoneName: string;
  openIssueCount: number;
  closedIssueCount: number;
  description: string;
  dueDate: string;
  isOpen: boolean;
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
  author: {
    id: number;
    username: string;
    profileUrl: string;
  };
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
  commentCount: number;
};

export type IssueSidebar = {
  assigneeIds: number[];
  labelIds: number[];
  milestoneId: number;
};

export type IssueComment = {
  commentId: number;
  username: string;
  profileUrl: string;
  content: string;
  createdAt: string;
};

export enum IssueState {
  open = "open",
  closed = "closed",
}

export enum IssueFilter {
  open = "open",
  closed = "closed",
  writtenByMe = "writtenByMe",
  assignedToMe = "assignedToMe",
  commentedByMe = "commentedByMe",
}

export type IssuesFilterState = {
  status: keyof typeof IssueState | null;
  filterBar: keyof typeof IssueFilter | null;
  author: string | null;
  assignees: Set<string>;
  labels: Set<string>;
  milestone: string | null;
};

export type IssuesFilter = {
  state: IssuesFilterState;
  text: string;
};

export type IssuesFilterActionMap = {
  SET_STATUS: keyof typeof IssueState | null;
  SET_AUTHOR: string | null;
  SET_ASSIGNEES: Set<string>;
  SET_LABELS: Set<string>;
  SET_MILESTONE: string | null;
  SET_FILTER_BAR: keyof typeof IssueFilter | null;
  RESET_FILTER: null;
  SET_FILTER_TEXT: string;
};

export type IssuesFilterAction = {
  [Key in keyof IssuesFilterActionMap]: {
    type: Key;
    payload?: IssuesFilterActionMap[Key];
  };
}[keyof IssuesFilterActionMap];
