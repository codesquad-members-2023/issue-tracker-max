import { Assignee, CommentType, IssueData } from './issue.type';
import { LabelData, LabelType } from './label.type';
import { MilestoneData } from './milestone.type';

export type PostNewIssueRes = {
  success: boolean;
  data?: {
    id: number;
  };
  errorCode?: {
    status: number;
    message: string;
  };
};

export type OnlySuccessRes = {
  success: boolean;
  data?: null;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type PostCommentsRes = {
  success: boolean;
  data?: CommentType;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetIssueRes = {
  success: boolean;
  data?: IssueData;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type PostFileRes = {
  success: boolean;
  data?: string;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetLabelRes = {
  success: boolean;
  data?: LabelData;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetMilestoneRes = {
  success: boolean;
  data?: MilestoneData;
  errorCode?: {
    status: number;
    message: string;
  };
};

export type PostSignInRes = {
  success: boolean;
  data?: {
    accessToken: string;
    refreshToken: string;
    member: {
      id: number;
      name: string;
      imageUrl: string;
    };
  };
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetAssigneesRes = {
  success: boolean;
  data?: Assignee[] | [];
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetLabelsRes = {
  success: boolean;
  data?: LabelType[] | [];
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetMilestonesRes = {
  success: boolean;
  data?:
    | {
        id: number;
        name: string;
      }[]
    | [];
  errorCode?: {
    status: number;
    message: string;
  };
};

export type GetWritersRes = {
  success: boolean;
  data?:
    | {
        id: number;
        name: string;
      }[]
    | [];
  errorCode?: {
    status: number;
    message: string;
  };
};

export type PostNewLabelRes = {
  success: boolean;
  data?: {
    id: number;
  };
  errorCode?: {
    status: number;
    message: string;
  };
};

export type PostNewMilestoneRes = {
  success: boolean;
  data?: {
    id: number;
  };
  errorCode?: {
    status: number;
    message: string;
  };
};
