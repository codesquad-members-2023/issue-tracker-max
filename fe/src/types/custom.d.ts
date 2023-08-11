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

type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};

type Label = {
  id: number;
  name: string;
  textColor: ThemeType;
  backgroundColor: string;
  description: string;
};

type MilestonePageData = {
  openMilestonesCount: number;
  closedMilestoneCount: number;
  milestones: Milestone[];
};

type Milestone = {
  id: number;
  name: string;
  description: string;
  progress: number;
  status: 'open' | 'closed';
  openIssueCount: number;
  closedIssueCount: number;
  deadline: string;
};
