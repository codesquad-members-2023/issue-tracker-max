import { useState, useEffect, useCallback, useRef } from "react";
import { Link, useLocation } from "react-router-dom";
import styled from "styled-components";

import { Icon } from "components/Common/Icon/Icon";
import { Button } from "components/Common/Button/Button";
import { DropdownTap } from "components/Dropdown/DropdownTap";
import { IssueTableItem } from "./IssueTableItem";
import { DropdownPanel } from "components/Dropdown/DropdownPanel";

const INITIAL_SUBFILLTER_DATA = [
  { id: 1, title: "담당자", filter: "assignees" },
  { id: 2, title: "레이블", filter: "labels" },
  { id: 3, title: "마일스톤", filter: "milestones" },
  { id: 4, title: "작성자", filter: "authors" },
];

const ISSUE_STATE_DATA = [
  { id: 1, name: "선택한 이슈 열기", filter: "open" },
  { id: 2, name: "선택한 이슈 닫기", filter: "closed" },
];

interface IssueItem {
  id: number;
  title: string;
  author: string;
  assigneeProfiles?: string[];
  milestone?: string;
  createdAt: string;
  labels?: { name: string; backgroundColor: string; textColor: string }[];
}

interface IssueTableData {
  openIssueCount: number;
  closedIssueCount: number;
  issues: IssueItem[] | null;
}

interface IssueTableProps {
  tableData: IssueTableData | null;
}

export const IssueTable: React.FC<IssueTableProps> = ({ tableData }) => {
  const location = useLocation();
  const [isChecked, setIsChecked] = useState(false);
  const [subFilterData, setSubFilterData] = useState(INITIAL_SUBFILLTER_DATA);
  const isFirstClickRef = useRef(true);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const fetchFilterData = useCallback(async () => {
    const response = await fetch(`http://43.200.169.143:8080/api/filters`);
    const data = await response.json();
    console.log(data, "필터데이터");

    return subFilterData.map((item) => ({
      ...item,
      items: data[item.filter],
    }));
  }, [subFilterData]);

  useEffect(() => {
    fetchFilterData();
  }, [fetchFilterData]);

  const handleClick = async () => {
    if (isFirstClickRef.current) {
      const updatedIndicatorList = await fetchFilterData();
      setSubFilterData(updatedIndicatorList);
      isFirstClickRef.current = false;
    }
  };

  const handleClickCheckBox = () => setIsChecked(!isChecked);
  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  if (!tableData) return null;

  const issueTableList = () => (
    <TableList>
      {tableData.issues && tableData.issues.length > 0 ? (
        tableData.issues.map((issueItem) => (
          <IssueTableItem key={issueItem.id} issueItem={issueItem} />
        ))
      ) : (
        <EmptyResult>등록된 이슈가 없습니다.</EmptyResult>
      )}
    </TableList>
  );

  if (isChecked) {
    return (
      <>
        <ChangeIssueStateHeader style={{ position: "relative" }}>
          <CheckBox onClick={handleClickCheckBox}>
            <Icon icon="CheckBoxDisable" stroke="paletteBlue" />
            <p className="select">1개 이슈 선택</p>
          </CheckBox>
          <Button variant="ghost" size="M" onClick={toggleDropdown}>
            상태 수정
            <Icon icon="ChevronDown"></Icon>
          </Button>
          {isDropdownOpen && (
            <DropdownPanel
              items={ISSUE_STATE_DATA}
              title="상태 변경"
              $position="right"
              onClose={() => setIsDropdownOpen(false)}
            />
          )}
        </ChangeIssueStateHeader>
        {issueTableList()}
      </>
    );
  }
  /* 열린 이슈, 닫힌 이슈 selected ON/OFF는 나중에 필터 api 나오면 다시 처리하기 */
  return (
    <>
      <DefaultHeader>
        <div>
          <CheckBox onClick={handleClickCheckBox}>
            <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
          </CheckBox>
          <TapBox>
            <Link to="/issues/open">
              <Button
                size="M"
                variant="ghost"
                states={
                  location.pathname === "/issues/open" ? "selected" : undefined
                }
              >
                <Icon icon="AlertCircle" stroke="paletteBlue" />
                <p>열린 이슈({tableData.openIssueCount})</p>
              </Button>
            </Link>
            <Link to="/issues/closed">
              <Button
                size="M"
                variant="ghost"
                states={
                  location.pathname === "/issues/closed"
                    ? "selected"
                    : undefined
                }
              >
                <Icon icon="Archive" stroke="paletteBlue" />
                <p>닫힌 이슈({tableData.closedIssueCount})</p>
              </Button>
            </Link>
          </TapBox>
        </div>
        <DropdownTap
          dropdownData={subFilterData}
          dropdownPosition="right"
          dropdownTitle={(title) => `${title} 필터`}
          onTabClick={handleClick}
        />
      </DefaultHeader>
      {issueTableList()}
    </>
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
  > :first-child {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;

const DefaultHeader = styled(CommonStyle)``;

const CheckBox = styled.div`
  cursor: pointer;
  gap: 32px;
`;

const ChangeIssueStateHeader = styled(CommonStyle)`
  position: relative;
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

const TableList = styled.ul`
  padding-left: 0;

  > :last-child {
    border-bottom-left-radius: ${({ theme: { radius } }) => radius.large};
    border-bottom-right-radius: ${({ theme: { radius } }) => radius.large};
  }
`;

const EmptyResult = styled.li`
  display: flex;
  padding: 0px 36px;
  height: 96px;
  border: 1px solid black;
  align-items: center;
  justify-content: center;
  border: ${({ theme: { border } }) => border.default};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  border-top: none;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceStrong};
`;
