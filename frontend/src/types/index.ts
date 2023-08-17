export type User = {
  name: string;
  profileImg: string;
};

export type Label = {
  id: number;
  name: string;
  backgroundColor: string;
  textColor: string;
};

export type Milestone = {
  id: number;
  name: string;
};

export type Issue = {
  id: number;
  title: string;
  createdAt: string;
  user: User;
  labels: Label[];
  milestone: Milestone;
};

export type Data = {
  openCount: number;
  closedCount: number;
  labelCount: number;
  milestoneCount: number;
  issues: Issue[];
};
