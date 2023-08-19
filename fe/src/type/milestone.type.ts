export type MilestoneData = {
  labelCount: number;
  oppositeCount: number;
  milestones: MilestoneType[];
};

export type MilestoneType = {
  id: number;
  title: string;
  description: string;
  dueDate: string;
  openIssueCount: number;
  closedIssueCount: number;
};
