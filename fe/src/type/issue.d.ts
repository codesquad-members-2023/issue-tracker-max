type Issue = {
  id: number;
  number: string;
  title: string;
  status: string;
  writer: string;
  history: IssueHistory;
  labels: LabelType[] | [];
  assignees: Assignee[] | [];
  milestone: string;
};

type IssueHistory = {
  modifier: string;
  dateTime: string;
};

type LabelType = {
  id: number;
  name: string;
  description: string;
  textColor: string;
  backgroundColor: string;
};

type Assignee = {
  id: number;
  name: string;
};

type Milestone = {
  id: number;
  name: string;
  description: string;
  dueDate: string;
  openIssueCount: number;
  closedIssueCount: number;
};
