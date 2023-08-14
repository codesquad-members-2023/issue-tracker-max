import { styled } from "styled-components";
import { TableHeader } from "../Table.style";

export default function LabelsTableHeader({
  numLabels,
}: {
  numLabels?: number;
}) {
  return (
    <TableHeader>
      <TableHeaderContents>{numLabels}개의 레이블</TableHeaderContents>
    </TableHeader>
  );
}

const TableHeaderContents = styled.div`
  font: ${({ theme: { font } }) => font.displayBold16};
  color: ${({ theme: { neutral } }) => neutral.text.default};
`;
