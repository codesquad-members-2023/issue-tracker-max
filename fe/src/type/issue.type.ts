import { LabelType } from './label.type';

export type IssueData = {
  labelCount: number;
  milestoneCount: number;
  openIssueCount: number;
  closedIssueCount: number;
  issues: Issue[];
};

export type Issue = {
  id: number;
  isOpen: boolean;
  title: string;
  history: IssueHistory;
  labels: LabelType[] | [];
  assignees: Assignee[] | [];
  writer: {
    id: number;
    name: string;
    imageUrl: string;
  };
  milestone: {
    id: number;
    title: string;
  };
};

export type IssueHistory = {
  editor: string;
  modifiedAt: string;
};

export type Assignee = {
  id: number;
  name: string;
};

export type IssueDetailResponse = {
  success: boolean;
  data: IssueDetailType;
};

export type IssueDetailType = {
  id: number;
  isOpen: boolean;
  title: string;
  history: IssueHistory;
  labels: LabelType[] | [];
  assignees: Assignee[] | [];
  writer: {
    id: number;
    name: string;
  };
  milestone: {
    id: number;
    title: string;
  };
  comments: CommentType[];
};

export type CommentType = {
  id: number;
  writer: {
    id: number;
    name: string;
    imageUrl: string;
  };
  content: string;
  createdAt: string;
};
