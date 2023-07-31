import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';
import {
  issueFilterList,
  issueStateList,
  textColors,
  contributors,
  milestones,
  labels,
  DropDownOptionsType,
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

export const IssueListPage: React.FC = ({}) => {
  const [issues, setIssues] = useState<Issues>();

  return (
    <>
      <DropDownContainer
        name="issueFilter"
        options={issueFilterList}
        alignment="left"
      />
      <DropDownContainer
        name="assignee"
        options={contributors}
        alignment="right"
      />
      <DropDownContainer
        name="author"
        options={contributors}
        alignment="right"
      />
      <DropDownContainer name="label" options={labels} alignment="right" />
      <DropDownContainer
        name="milestone"
        options={milestones}
        alignment="right"
      />
      <DropDownContainer
        name="textColor"
        options={textColors}
        alignment="left"
      />
      <DropDownContainer
        name="issueState"
        options={issueStateList}
        alignment="right"
      />
    </>
  );
};
