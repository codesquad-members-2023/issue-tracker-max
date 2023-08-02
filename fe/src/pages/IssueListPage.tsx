import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { FilterBar } from '@components/common/FilterBar';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { TextArea } from '@components/common/TextArea';
import { DropDownIndicator } from '@components/common/dropDown/DropDownIndicator';
import {
  issueFilterList,
  issueStateList,
  textColors,
  contributors,
  milestones,
  labels,
} from '@components/common/dropDown/types';
import { useState } from 'react';
import { DropDownPanel } from '@components/common/dropDown/DropDownPanel';
import { SideBar } from '@components/common/sideBar/SideBar';

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
    console.log(selectedItems);
  };

  const onMultipleSelected = (index: number) => {
    setSelectedItems((prev) => ({ ...prev, [index]: !prev[index] }));
    console.log(selectedItems);
  };

  const [selectedOptions, setSelectedOptions] = useState<any[]>([]);

  const dropdownlist = [
    {
      id: 0,
      panelHeader: '담당자 필터',
      indicator: '담당자',
      options: contributors,
    },
    { id: 1, panelHeader: '레이블 필터', indicator: '레이블', options: labels },
    {
      id: 2,
      panelHeader: '마일스톤 필터',
      indicator: '마일스톤',
      options: milestones,
    },
    {
      id: 3,
      panelHeader: '작성자 필터',
      indicator: '작성자',
      options: contributors,
    },
  ];

  return (
    <>
      <div
        css={{
          display: 'flex',
          gap: '8px',
        }}
      >
        {dropdownlist.map((item) => (
          <DropDownIndicator indicator={item.indicator} size="M">
            <DropDownPanel
              panelHeader={item.panelHeader}
              alignment="right"
              options={item.options}
              onSelected={onSingleSelected}
              selectedItems={selectedItems}
            />
          </DropDownIndicator>
        ))}
      </div>

      <DropDownIndicator indicator={'텍스트 색상'} size="defaultSize">
        <DropDownPanel
          panelHeader="색상 변경"
          alignment="left"
          options={textColors}
          onSelected={onSingleSelected}
          selectedItems={selectedItems}
        />
      </DropDownIndicator>
    </>
  );
};
