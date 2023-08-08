import styled from "styled-components";
import { Icon } from "components/Common/Icon/Icon";
import { Button } from "components/Common/Button/Button";
import { useState, useEffect, useCallback } from "react";

import { DropdownTap } from "components/Dropdown/DropdownTap";

const initialData = [
  { id: 1, title: "담당자", filter: "assignees" },
  { id: 2, title: "레이블", filter: "labels" },
  { id: 3, title: "마일스톤", filter: "milestones" },
  { id: 4, title: "작성자", filter: "authors" },
];

const changeIssueStateIndicator: MyIndicatorItem[] = [
  {
    id: 1,
    title: "상태 수정",
    filter: "",
    items: [
      { id: 1, name: "선택한 이슈 열기", filter: "open" },
      { id: 2, name: "선택한 이슈 닫기", filter: "closed" },
    ],
  },
];

type SubFilterItem = {
  id: number;
  name: string;
  filter: string;
  profile?: string;
  backgroundColor?: string;
};

interface MyIndicatorItem {
  id: number;
  title: string;
  filter: string;
  items?: SubFilterItem[];
}

interface IssueTableProps {
  isChecked: boolean;
  openIssueCount: number;
  closedIssueCount: number;
  onClickCheckBox: () => void;
}

export const IssueTableHeader: React.FC<IssueTableProps> = ({
  isChecked,
  onClickCheckBox,
  openIssueCount,
  closedIssueCount,
}) => {
  const [indicatorList, setIndicatorList] =
    useState<MyIndicatorItem[]>(initialData);

  const fetchFilterData = useCallback(async () => {
    const response = await fetch(`/api/filters`);
    const data = await response.json();

    return indicatorList.map((item) => ({
      ...item,
      items: data[item.filter],
    }));
  }, [indicatorList]);

  useEffect(() => {
    fetchFilterData();
  }, [fetchFilterData]);

  const handleClick = async () => {
    const updatedIndicatorList = await fetchFilterData();
    setIndicatorList(updatedIndicatorList);
  };

  if (isChecked) {
    return (
      <ChangeIssueStateHeader>
        <CheckBox onClick={onClickCheckBox}>
          <Icon icon="CheckBoxDisable" stroke="paletteBlue" />
          <p className="select">1개 이슈 선택</p>
        </CheckBox>
        <DropdownTap
          indicatorlList={changeIssueStateIndicator}
          dropdownPosition="right"
          dropdownTitle={() => "상태 변경"}
        />
      </ChangeIssueStateHeader>
    );
  }

  return (
    <DefaultHeader>
      <div>
        <CheckBox onClick={onClickCheckBox}>
          <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
        </CheckBox>
        <TapBox>
          <Button size="M" variant="ghost" states="selected">
            <Icon icon="AlertCircle" stroke="paletteBlue" />
            <p>열린 이슈({openIssueCount})</p>
          </Button>
          <Button size="M" variant="ghost">
            <Icon icon="Archive" stroke="paletteBlue" />
            <p>닫힌 이슈({closedIssueCount})</p>
          </Button>
        </TapBox>
      </div>
      <DropdownTap
        indicatorlList={indicatorList}
        dropdownPosition="right"
        dropdownTitle={(title) => `${title} 필터`}
        onTabClick={handleClick}
      />
    </DefaultHeader>
  );
};

const CommonStyle = styled.div`
  display: flex;
  height: 64px;
  align-items: center;
  padding: 16px 32px;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top-right-radius: ${({ theme: { radius } }) => radius.large};
  border-top-left-radius: ${({ theme: { radius } }) => radius.large};
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  justify-content: space-between;
  align-self: stretch;
  > div {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;

const DefaultHeader = styled(CommonStyle)``;

const CheckBox = styled.div`
  cursor: pointer;
`;

const ChangeIssueStateHeader = styled(CommonStyle)`
  .select {
    font: ${({ theme: { font } }) => font.displayB16};
    color: ${({ theme: { color } }) => color.nuetralTextDefault};
  }
`;

const TapBox = styled.div`
  display: flex;
  align-items: center;
  gap: 32px;
  display: flex;
`;
