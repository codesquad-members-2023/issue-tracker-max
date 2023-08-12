type IssueResponse = {
  success: boolean;
  data: IssueData;
};

type IssueData = {
  labelCount: number;
  milestoneCount: number;
  openIssueCount: number;
  closedIssueCount: number;
  issues: Issue[];
};

type Issue = {
  id: number;
  isOpen: boolean;
  title: string;
  history: IssueHistory;
  labels: Label[] | [];
  assignees: Assignee[] | [];
  writer: {
    id: number;
    name: string;
  };
  milestone: {
    id: number;
    title: string;
  };
};

type IssueHistory = {
  editor: string;
  modifiedAt: string;
};

type Assignee = {
  id: number;
  name: string;
};

type IssueDetail = {
  issue: Issue;
  comments: CommentType[];
};

type CommentType = {
  id: number;
  writer: {
    id: number;
    name: string;
  };
  content: string;
  dateTime: string;
};
