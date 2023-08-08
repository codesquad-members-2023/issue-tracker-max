import styled from "styled-components";
import { IssueTableItem } from "./IssueTableItem";

interface IssueItem {
  id: number;
  title: string;
  author: string;
  assigneeProfiles?: string[];
  milestone?: string;
  createdAt: string;
  labels?: { name: string; backgroundColor: string; textColor: string }[];
}

interface IssueTableListProps {
  issuesListData: IssueItem[];
}

export const IssueTableList: React.FC<IssueTableListProps> = ({
  issuesListData,
}) => {
  return (
    <TableList>
      {issuesListData.map((issueItem) => (
        <IssueTableItem key={issueItem.id} issueItem={issueItem} />
      ))}
    </TableList>
  );
};

const TableList = styled.ul`
  padding-left: 0;

  > :last-child {
    border-bottom-left-radius: ${({ theme: { radius } }) => radius.large};
    border-bottom-right-radius: ${({ theme: { radius } }) => radius.large};
  }
`;
