import { IssueItem as IssueItemType } from "@customTypes/index";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import IssuesTableItem from "./IssuesTableItem";

export default function IssuesTableBody({
  issuesList,
}: {
  issuesList: IssueItemType[] | null;
}) {
  return (
    <TableBody>
      {issuesList ? (
        <ul>
          {issuesList.map((issue) => (
            <IssuesTableItem key={issue.issueNumber} issue={issue} />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>등록된 이슈가 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
