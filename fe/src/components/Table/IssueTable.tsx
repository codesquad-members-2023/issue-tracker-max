import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import styled from "styled-components";

import { Icon } from "components/Common/Icon/Icon";
import { Button } from "components/Common/Button/Button";
import { DropdownTap } from "components/Dropdown/DropdownTap";
import { IssueTableItem } from "./IssueTableItem";
import { DropdownPanel2 } from "components/Dropdown/DropdownPanel2";
import { useFetch } from "../../hook/useApiRequest";

const INITIAL_SUBFILLTER_DATA = [
  { id: 1, title: "담당자", filter: "assignee" },
  { id: 2, title: "레이블", filter: "label" },
  { id: 3, title: "마일스톤", filter: "milestone" },
  { id: 4, title: "작성자", filter: "author" },
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

interface FilterData {
  [key: string]: string[]; // Assuming the filters contain arrays of strings
}

export const IssueTable: React.FC<IssueTableProps> = ({ tableData }) => {
  const location = useLocation();
  const { makeRequest } = useFetch();
  const [issueListData, setIssueListData] = useState<IssueItem[] | null>(null);
  const [selectedIssueIds, setSelectedIssueIds] = useState<number[]>([]);
  const [subFilterData, setSubFilterData] = useState(INITIAL_SUBFILLTER_DATA);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const queryParams = new URLSearchParams(location.search);
  const qValue = queryParams.get("q");

  const isOpenSelected = qValue === "is:open" || location.pathname === "/";
  const isClosedSelected = qValue === "is:closed";

  const fetchFilterData = async () => {
    try {
      const data = (await makeRequest(
        "http://43.200.169.143:8080/api/filters/main",
        "GET",
      )) as FilterData;

      setSubFilterData((prevData) =>
        prevData.map((item) => ({
          ...item,
          items: data[item.filter],
        })),
      );
    } catch (error) {
      console.error("Error fetching filter data:", error);
    }
  };

  useEffect(() => {
    fetchFilterData();
  }, []);

  useEffect(() => {
    if (tableData) {
      setIssueListData(tableData.issues);
    }
  }, [tableData]);

  const handleAllCheck = () => {
    if (selectedIssueIds.length === 0) {
      const ids = tableData?.issues?.map((issue) => issue.id) || [];
      setSelectedIssueIds(ids);
    } else {
      setSelectedIssueIds([]);
    }
  };

  const handleSingleCheck = (id: number, checked: boolean) => {
    if (checked) {
      setSelectedIssueIds((prevIds) => [...prevIds, id]);
    } else {
      setSelectedIssueIds((prevIds) =>
        prevIds.filter((prevId) => prevId !== id),
      );
    }
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  if (!tableData) return null;

  const handleSelectState = async (item: { filter?: string }) => {
    toggleDropdown();
    if (item.filter && selectedIssueIds.length > 0) {
      const body = {
        isOpen: item.filter === "open" ? true : false,
        issues: selectedIssueIds,
      };

      try {
        await makeRequest(
          "http://43.200.169.143:8080/api/issues",
          "PATCH",
          body,
        );

        const updatedIssues = issueListData?.filter(
          (issue) => !selectedIssueIds.includes(issue.id),
        );
        setSelectedIssueIds([]);
        setIssueListData(updatedIssues || null);
      } catch (error) {
        console.error("Error updating issues:", error);
      }
    }
  };

  if (selectedIssueIds.length > 0) {
    return (
      <>
        <ChangeIssueStateHeader style={{ position: "relative" }}>
          <CheckBox onClick={handleAllCheck}>
            <Icon icon="CheckBoxDisable" stroke="paletteBlue" />
            <p className="select">{selectedIssueIds.length}개 이슈 선택</p>
          </CheckBox>
          <Button variant="ghost" size="M" onClick={toggleDropdown}>
            상태 수정
            <Icon icon="ChevronDown"></Icon>
          </Button>
          {isDropdownOpen && (
            <DropdownPanel2
              items={ISSUE_STATE_DATA}
              title="상태 변경"
              $position="right"
              onClose={() => setIsDropdownOpen(false)}
              type="issuesState"
              onItemSelect={handleSelectState}
            />
          )}
        </ChangeIssueStateHeader>
        <TableListBox>
          {issueListData && issueListData.length > 0 ? (
            issueListData.map((issueItem) => (
              <IssueTableItem
                key={issueItem.id}
                issueItem={issueItem}
                onCheckChange={handleSingleCheck}
                selectedIssueIds={selectedIssueIds}
              />
            ))
          ) : (
            <EmptyResult>등록된 이슈가 없습니다.</EmptyResult>
          )}
        </TableListBox>
      </>
    );
  }

  return (
    <>
      <DefaultHeader>
        <div>
          <CheckBox onClick={handleAllCheck}>
            <Icon icon="CheckBoxInitial" stroke="paletteBlue" />
          </CheckBox>
          <TapBox>
            <Link to="/issues/filtered?q=is:open">
              <Button
                size="M"
                variant="ghost"
                states={isOpenSelected ? "selected" : undefined}
              >
                <Icon icon="AlertCircle" stroke="paletteBlue" />
                <p>열린 이슈({tableData.openIssueCount | 0})</p>
              </Button>
            </Link>
            <Link to="/issues/filtered?q=is:closed">
              <Button
                size="M"
                variant="ghost"
                states={isClosedSelected ? "selected" : undefined}
              >
                <Icon icon="Archive" stroke="paletteBlue" />
                <p>닫힌 이슈({tableData.closedIssueCount | 0})</p>
              </Button>
            </Link>
          </TapBox>
        </div>
        <DropdownTap
          dropdownData={subFilterData}
          dropdownPosition="right"
          dropdownTitle={(title) => `${title} 필터`}
          // onTabClick={handleClick}
        />
      </DefaultHeader>
      <TableListBox>
        {issueListData && issueListData.length > 0 ? (
          issueListData.map((issueItem) => (
            <IssueTableItem
              key={issueItem.id}
              issueItem={issueItem}
              onCheckChange={handleSingleCheck}
              selectedIssueIds={selectedIssueIds}
            />
          ))
        ) : (
          <EmptyResult>등록된 이슈가 없습니다.</EmptyResult>
        )}
      </TableListBox>
    </>
  );
};

const CommonStyle = styled.div`
  margin-top: 24px;
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

const TableListBox = styled.ul`
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
