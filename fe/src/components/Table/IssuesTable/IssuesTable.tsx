import Pagination from "@components/Pagination";
import useFetch from "@hooks/useFetch";
import { getIssues } from "api";
import { useIssuesFilter } from "context/IssuesFilterContext";
import { useCallback, useState } from "react";
import { styled } from "styled-components";
import { Table } from "../Table.style";
import IssuesTableBody from "./IssuesTableBody";
import IssuesTableHeader from "./IssuesTableHeader";

export default function IssuesTable() {
  const [pageNumber, setPageNumber] = useState(1);
  const [selectedIssueIds, setSelectedIssueIds] = useState<Set<number>>(
    new Set()
  );

  const { issuesFilter } = useIssuesFilter();
  const { data: issuesList, reFetch: refetchIssuesList } = useFetch(
    useCallback(
      () => getIssues(issuesFilter.text, pageNumber),
      [issuesFilter.text, pageNumber]
    )
  );

  const isAllIssuesSelected =
    selectedIssueIds.size === issuesList?.data.length &&
    selectedIssueIds.size > 0;

  const toggleSelectAll = () => {
    if (isAllIssuesSelected) {
      deselectAllIssues();
    } else {
      selectAllIssues();
    }
  };

  const selectAllIssues = () => {
    const allIssueIds = issuesList?.data.map((issue) => issue.issueNumber);
    setSelectedIssueIds(new Set(allIssueIds));
  };

  const deselectAllIssues = () => {
    setSelectedIssueIds(new Set<number>());
  };

  const toggleSelectIssue = (id: number) => {
    setSelectedIssueIds((prev) => {
      const newSet = new Set(prev);
      if (prev.has(id)) {
        newSet.delete(id);
      } else {
        newSet.add(id);
      }
      return newSet;
    });
  };

  const onPageChange = (page: number) => {
    setPageNumber(page);
  };

  const onPrevPageClick = () => {
    setPageNumber((prev) => prev - 1);
  };

  const onNextPageClick = () => {
    setPageNumber((prev) => prev + 1);
  };

  return (
    <TableWrapper>
      <Table>
        {issuesList && (
          <IssuesTableHeader
            {...{
              numOpen: issuesList.pagination.openCounts,
              numClosed: issuesList.pagination.closedCounts,
              selectedIssueIds,
              isAllIssuesSelected,
              toggleSelectAll,
              refetchIssuesList,
              deselectAllIssues,
            }}
          />
        )}
        {issuesList && (
          <IssuesTableBody
            {...{
              issuesList: issuesList.data,
              selectedIssueIds,
              toggleSelectIssue,
            }}
          />
        )}
      </Table>
      {issuesList && (
        <Pagination
          currentPage={pageNumber}
          totalPages={issuesList.pagination.totalPages}
          {...{ onPageChange, onPrevPageClick, onNextPageClick }}
        />
      )}
    </TableWrapper>
  );
}

const TableWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`;
