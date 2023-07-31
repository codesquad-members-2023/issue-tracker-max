import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';
import {
  issueFilterList,
  issueStateList,
  textColors,
  contributors,
  milestones,
  labels,
} from '@components/common/dropDown/types';
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

type SelectedItems = {
  [key: number]: boolean;
};

export const IssueListPage: React.FC = ({}) => {
  const [issues, setIssues] = useState<Issues>();
  const [selectedItems, setSelectedItems] = useState<SelectedItems>({});

  const onSingleSelected = (index: number) => {
    setSelectedItems({ [index]: true });
  };

  const onMultipleSelected = (index: number) => {
    setSelectedItems((prev) => ({ ...prev, [index]: !prev[index] }));
  };

  return (
    <>
      
    </>
  );
};
