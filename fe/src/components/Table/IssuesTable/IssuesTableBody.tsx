import { IssueItem as IssueItemType } from "@customTypes/index";
import { useIssuesFilter } from "context/IssuesFilterContext";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import IssuesTableItem from "./IssuesTableItem";

export default function IssuesTableBody({
  issuesList,
  selectedIssueIds,
  toggleSelectIssue,
}: {
  issuesList: IssueItemType[];
  selectedIssueIds: Set<number>;
  toggleSelectIssue: (id: number) => void;
}) {
  const { issuesFilter } = useIssuesFilter();

  const issuesStatus = issuesFilter.state.status;
  const openIssuesList = issuesList.filter((issue) => issue.isOpen);
  const closedIssuesList = issuesList.filter((issue) => !issue.isOpen);
  const currentIssuesList = issuesStatus
    ? issuesStatus === "open"
      ? openIssuesList
      : closedIssuesList
    : issuesList;

  return (
    <TableBody>
      {currentIssuesList.length ? (
        <ul>
          {issuesList.map((issue) => (
            <IssuesTableItem
              {...{
                key: issue.issueNumber,
                issue,
                isSelected: selectedIssueIds.has(issue.issueNumber),
                toggleSelectIssue,
              }}
            />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>
          검색과 일치하는 결과가 없습니다.
        </EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
