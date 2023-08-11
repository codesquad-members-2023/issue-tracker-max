import { Milestone } from "@customTypes/index";
import { EmptyTableBodyItem, TableBody } from "../Table.style";
import MilestonesTableItem from "./MilestonesTableItem";

export default function MilestonesTableBody({
  milestoneList,
}: {
  milestoneList: Milestone[] | null;
}) {
  const hasMilestone = milestoneList && milestoneList.length !== 0;

  return (
    <TableBody>
      {hasMilestone ? (
        <ul>
          {milestoneList.map((milestone) => (
            <MilestonesTableItem
              key={milestone.milestoneId}
              milestone={milestone}
            />
          ))}
        </ul>
      ) : (
        <EmptyTableBodyItem>마일스톤이 없습니다.</EmptyTableBodyItem>
      )}
    </TableBody>
  );
}
