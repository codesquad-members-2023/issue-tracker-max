import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { FilterBar } from '@components/common/FilterBar';
import { DropDownIndicator } from '@components/common/dropDown/DropDownIndicator';
import { useState } from 'react';
import { DropDownPanel } from '@components/common/dropDown/DropDownPanel';

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

type SelectedItems = {
  [key: number]: boolean;
};

export const IssueListPage: React.FC = ({}) => {
  const [issues, setIssues] = useState<Issues>();

  return <></>;
};
