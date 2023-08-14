import { Label } from "@customTypes/index";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import LabelsTableItem from "./LabelsTableItem";

export default function LabelsTableBody({
  labelsList,
  updateLabelsList,
}: {
  labelsList: Label[];
  updateLabelsList: () => void;
}) {
  return (
    <TableBody>
      {labelsList.length ? (
        <ul>
          {labelsList.map((label) => (
            <LabelsTableItem
              key={label.labelId}
              label={label}
              updateLabelsList={updateLabelsList}
            />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>등록된 이슈가 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
