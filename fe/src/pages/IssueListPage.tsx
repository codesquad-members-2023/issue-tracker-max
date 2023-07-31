import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';
import {
  issueFilterList,
  issueStateList,
  textColors,
  contributors,
  milestones,
  labels,
  // DropDownOptionsType,
  // DropDownIndicatorName,
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
        options={issueFilterList}
        indicator="필터"
        panelHeader="이슈 필터"
        alignment="left"
      />

      <DropDownContainer
        options={contributors}
        indicator="작성자"
        panelHeader="작성자 필터"
        alignment="right"
      />
      <DropDownContainer
        options={contributors}
        indicator="담당자"
        panelHeader="담당자 필터"
        alignment="right"
      />
      <DropDownContainer
        options={labels}
        indicator="레이블"
        panelHeader="레이블 필터"
        alignment="right"
      />
      <DropDownContainer
        options={milestones}
        indicator="마일스톤"
        panelHeader="마일스톤 필터"
        alignment="right"
      />
      {/* <DropDownContainer
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
      /> */}
    </>
  );
};
