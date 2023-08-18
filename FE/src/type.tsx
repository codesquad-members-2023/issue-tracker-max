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
  id: number;
  title: string;
  backgroundColor: string;
  textColor: string;
  description: string;
};

export type Milestone = {
  id: number;
  title: string;
  progress: number;
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
  profileImageUrl: string;
};

export type AssigneesProps = {
  assignees: AssigneesList[] | null;
};

export type FetchedLabels = {
  metadata: {
    totalLabelCount: number;
    totalMilestoneCount: number;
  };
  labels: Label[] | null;
};

export type Comment = {
  id: number;
  author: AssigneesList;
  content: string;
  createAt: string;
};

export type FetchedDetail = {
  id: number;
  title: string;
  content: string;
  isOpen: boolean;
  createAt: string;
  author: {
    id: number;
    nickname: string;
    profileImageUrl: string;
  };
  assignees: AssigneesList[] | null;
  labels: Label[] | null;
  milestone: Milestone | null;
  comments: Comment[] | null;
};

export type FetchedMilestone = {
  metadata: {
    totalLabelCount: number;
    totalMilestoneCount: number;
    openMilestoneCount: number;
    closeMilestoneCount: number;
  };
  milestones: MilestoneData[] | null;
};

export interface MilestoneData extends Milestone {
  deadline: string;
  isOpen: boolean;
  description: string;
  openIssueCount: number;
  closeIssueCount: number;
}

export type AssigneesData = {
  assignees: AssigneesList[];
};

export type LabelsData = {
  labels: Label[];
};

export type MilestonesData = {
  milestones: Milestone[];
};

export type AuthorsData = {
  authors: AssigneesList[];
};

export type AssigneesSideBar = {
  assignees: AssigneesList[];
  members: AssigneesList[];
};

export type LabelsSideBar = {
  assignedLabels: Label[];
  labels: Label[];
};

export type MilestonesSideBar = {
  assignedMilestones: Milestone[];
  milestones: Milestone[];
};

export type UserProfile = {
  memberId: number;
  exp: number;
  email: string;
  nickname: string;
  profileImageUri: string;
};
