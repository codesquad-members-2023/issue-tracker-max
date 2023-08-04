export type Filter = {
  isOpen: boolean;
  assigneeIds: number[];
  labelIds: [];
  milestoneId: number;
  authorId: number;
  isCommentedByMe: boolean;
};

export type NewIssue = {
  title: string;
  content: string;
  assigneeIds: number[];
  labelIds: number[];
  milestoneId: number;
};

export type Label = {
  title: string;
  color: string;
};

export type Milestone = {
  id: number;
  title: string;
};

export type IssueListProps = {
  id: number;
  title: string;
  isOpen: boolean;
  createAt: string;
  author: string;
  authorProfileUrl: string;
  labels: Label[] | [];
  milestone: Milestone | null;
};

export type ListDataProps = {
  metadata: {
    issueOpenCount: number;
    issueCloseCount: number;
    labelCount: number;
    milestoneCount: number;
  };
  issues: IssueListProps[] | [];
};

export type AssigneesList = {
  id: number;
  nickname: string;
  profile_image_url: string;
};

export type AssigneesProps = {
  assignees: AssigneesList[] | [];
};
