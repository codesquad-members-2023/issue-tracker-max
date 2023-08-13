type LabelResponse = {
  success: boolean;
  data: LabelData;
};

type LabelData = {
  milestoneCount: number;
  labels: Label[];
};

type Label = {
  id: number | string;
  title: string;
  description?: string;
  textColor: string;
  backgroundColor: string;
};
