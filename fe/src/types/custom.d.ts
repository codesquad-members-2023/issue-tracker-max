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

  author: {
    userId: number;
    loginId: string;
    image: string;
  };

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
  isOpen: boolean;
};
