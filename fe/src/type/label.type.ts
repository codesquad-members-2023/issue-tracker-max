export type LabelData = {
  milestoneCount: number;
  labels: LabelType[];
};

export type LabelType = {
  id: number | string;
  title: string;
  description?: string;
  textColor: string;
  backgroundColor: string;
};
