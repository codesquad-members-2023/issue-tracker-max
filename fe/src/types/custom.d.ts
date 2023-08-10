type ThemeType = 'light' | 'dark';

type IssuePageData = {
  labelCount: number;
  milestoneCount: number;
  openIssueCount: number;
  closedIssueCount: number;

  issues: Issue[];
};

type Issue = {
  id: number;
  title: string;
  authorLoginId: string;
  assignees: {
    userId: string;
    image: string;
  }[];

  labels: {
    id: number;
    name: string;
    textColor: ThemeType;
    backgroundColor: string;
  }[];

  milestone: {
    id: number;
    name: string;
  };

  createdAt: string;
  status: 'open' | 'closed';
};

type UserInfo = {
  userId: number;
  loginId: string;
  image: string;
};

type FilterState = {
  status: 'initialStatus' | 'open' | 'closed';
  author: string;
  label: string;
  milestone: string;
  assignee: string;
  commentAuthor: string;
};
