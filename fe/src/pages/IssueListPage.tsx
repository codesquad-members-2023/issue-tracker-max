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
      <DropDownContainer
        options={issueFilterList}
        indicator="필터"
        panelHeader="이슈 필터"
        alignment="left"
        onSelected={onMultipleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={contributors}
        indicator="작성자"
        panelHeader="작성자 필터"
        alignment="right"
        onSelected={onSingleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={contributors}
        indicator="담당자"
        panelHeader="담당자 필터"
        alignment="right"
        onSelected={onMultipleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={labels}
        indicator="레이블"
        panelHeader="레이블 필터"
        alignment="right"
        onSelected={onMultipleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={milestones}
        indicator="마일스톤"
        panelHeader="마일스톤 필터"
        alignment="right"
        onSelected={onSingleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={issueStateList}
        indicator="상태 수정"
        panelHeader="상태 변경"
        alignment="right"
        onSelected={onSingleSelected}
        selectedItems={selectedItems}
      />

      <DropDownContainer
        options={textColors}
        indicator="텍스트 색상"
        panelHeader="색상 변경"
        alignment="left"
        onSelected={onSingleSelected}
        selectedItems={selectedItems}
      />
    </>
  );
};
