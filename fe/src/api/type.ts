export type NewIssueBody = {
  title: string;
  content: string;
  assignees: number[];
  labels: number[];
  milestone: number;
};

export type EditAssigneesBody = {
  addUserAccountId: number[];
  removeUserAccountId: number[];
};

export type EditLabelsBody = {
  addLabelsId: number[];
  removeLabelsId: number[];
};

export type EditMilestoneBody = {
  milestoneId: number;
};

export type Pagination = {
  currentPage: number;
  totalCounts: number;
  openCounts: number;
  closedCounts: number;
  totalPages: number;
};
