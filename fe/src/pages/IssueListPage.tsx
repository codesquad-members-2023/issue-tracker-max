import { IssueTable } from '@components/issueListPage/IssueTable';
import { SubNav } from '@components/issueListPage/SubNav';
import { useState } from 'react';

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

export const IssueListPage: React.FC = ({}) => {
  const [issues, setIssues] = useState<Issues>();
  const [filterString, setFilterString] = useState('status:open');

  return (
    <>
      <SubNav {...{ filterString }} />
      <IssueTable />
    </>
  );
};
