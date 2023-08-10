import { Label } from "@customTypes/index";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import LabelsTableItem from "./LabelsTableItem";

export default function LabelsTableBody({
  labelsList,
}: {
  labelsList: Label[] | null;
}) {
  return (
    <TableBody>
      {labelsList ? (
        <ul>
          {labelsList.map((label) => (
            <LabelsTableItem key={label.labelId} label={label} />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>등록된 이슈가 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
