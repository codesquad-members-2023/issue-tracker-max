type ThemeType = 'light' | 'dark';
type StatusType = 'open' | 'closed';

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
  status: StatusType;
  openIssueCount: number;
  closedIssueCount: number;
  deadline: string;
};

//ISSUE_DETAIL_PAGE
type User = {
  userId: number;
  loginId: string;
  image: string;
};

type IssueDetailLabel = {
  id: number;
  name: string;
  textColor: ThemeType;
  backgroundColor: string;
};

type IssueDetailMilestone = {
  id: number;
  name: string;
  progress: number;
};

type Comment = {
  id: number;
  author: User;
  contents: string;
  createdAt: string;
};

type IssueDetailPageData = {
  id: number;
  title: string;
  contents: string;
  status: StatusType;
  createdAt: string;
  author: User;
  assignees: User[];
  labels: IssueDetailLabel[];
  milestone: IssueDetailMilestone;
  comments: Comment[];
};

// SIDE_BAR_SELECTED_OPTION
// type SelectionState = {
//   assignees: number[];
//   labels: number[];
//   milestones: number | null;
// };
// // ISSUE_DETAIL_SIDE_BAR_SELECTED_OPTION
// type IssueDetailSideBarData = {
//   assignees: User[];
//   labels: IssueDetailLabel[];
//   milestones: { id: number; name: string; progress: number } | null;
// };

type SelectionState = {
  newIssuePage: {
    assignees: number[];
    labels: number[];
    milestones: number | null;
  };
  detailPage: {
    assignees: User[];
    labels: IssueDetailLabel[];
    milestones: { id: number; name: string; progress: number } | null;
  };
};
