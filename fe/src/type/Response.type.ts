import { CommentType, IssueData } from './issue.type';
import { LabelData } from './label.type';
import { MilestoneData } from './milestone.type';

export type PostNewIssueRes = {
  success: boolean;
  data: {
    id: number;
  };
};

export type OnlySuccessRes = {
  success: boolean;
  data: null;
};

export type PostCommentsRes = {
  success: boolean;
  data: CommentType;
};

export type GetIssueRes = {
  success: boolean;
  data: IssueData;
};

export type PostFileRes = {
  success: boolean;
  data: string;
};

export type GetLabelRes = {
  success: boolean;
  data: LabelData;
};

export type GetMilestoneRes = {
  success: boolean;
  data: MilestoneData;
};

export type PostSignInRes = {
  success: boolean;
  data: {
    accessToken: string;
    refreshToken: string;
    member: {
      id: number;
      name: string;
      imageUrl: string;
    };
  };
};
