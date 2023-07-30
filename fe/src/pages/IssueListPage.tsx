import { useState } from 'react';

type Props = {};

type Issues = {
  openIssueCount: number;
  closedIssueCount: number;
  issues: {
    id: number;
    title: string;
    author: string;
    labelIds: number[];
    milestoneId: number;
    createdAt: string;
    isOpen: boolean;
  }[];
};

export const IssueListPage: React.FC = ({}: Props) => {
  const [issues, setIssues] = useState<Issues>();

  return <></>;
};
