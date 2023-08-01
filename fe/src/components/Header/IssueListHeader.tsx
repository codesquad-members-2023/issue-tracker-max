import styled from "styled-components";
import { Icon } from "components/Icon/Icon";
import { Button } from "components/Button/Button";
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

export const IssueListHeader = () => {
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
    console.log("?");

    const updatedIndicatorList = await fetchFilterData();
    setIndicatorList(updatedIndicatorList);
  };

  return (
    <StyledIssueDefaultHeader>
      <div>
        <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
        <Tap>
          <Button size="M" variant="ghost" states="selected">
            <Icon icon="AlertCircle" stroke="paletteBlue" />
            <p>열린 이슈(2)</p>
          </Button>
          <Button size="M" variant="ghost">
            <Icon icon="Archive" stroke="paletteBlue" />
            <p>닫힌 이슈(0)</p>
          </Button>
        </Tap>
      </div>
      <DropdownTap
        indicatorlList={indicatorList}
        dropdownPosition="right"
        dropdownTitle={(title) => `${title} 필터`}
        onTabClick={handleClick}
      />
    </StyledIssueDefaultHeader>
  );
};

export const ChangeStateHeader = () => {
  return (
    <StyledChangeIssueStateHeader>
      <div>
        <Icon icon="CheckBoxDisable" stroke="paletteBlue" />
        <p className="select">1개 이슈 선택</p>
      </div>

      <DropdownTap
        indicatorlList={changeIssueStateIndicator}
        dropdownPosition="right"
        dropdownTitle={() => "상태 변경"}
      />
    </StyledChangeIssueStateHeader>
  );
};

const StyledCommonHeader = styled.div`
  display: flex;
  height: 64px;
  align-items: center;
  padding: 16px 32px;
  border-radius: ${({ theme: { radius } }) => radius.large};
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  justify-content: space-between;
  align-self: stretch;
  > div {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;

const StyledIssueDefaultHeader = styled(StyledCommonHeader)``;

const StyledChangeIssueStateHeader = styled(StyledCommonHeader)`
  .select {
    font: ${({ theme: { font } }) => font.displayB16};
    color: ${({ theme: { color } }) => color.nuetralTextDefault};
  }
`;

const Tap = styled.div`
  display: flex;
  align-items: center;
  gap: 32px;
  display: flex;
`;
