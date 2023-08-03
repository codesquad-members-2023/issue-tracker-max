import styled from "styled-components";
import { IssueTableItem } from "./IssueTableItem";

export const IssueTableList = () => {
  return (
    <TableList>
      <IssueTableItem />
      <IssueTableItem />
      <IssueTableItem />
      <IssueTableItem />
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
