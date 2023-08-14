import { Milestone } from "@customTypes/index";
import { useState } from "react";
import { Table } from "../Table.style";
import MilestonesTableBody from "./MilestonesTableBody";
import MilestonesTableHeader from "./MilestonesTableHeader";

type TabName = "열린 마일스톤" | "닫힌 마일스톤";

export default function MilestonesTable({
  openMilestone,
  closedMilestone,
  updateOpenMilestone,
  updateCloseMilestone,
}: {
  openMilestone: Milestone[];
  closedMilestone: Milestone[];
  updateOpenMilestone: () => void;
  updateCloseMilestone: () => void;
}) {
  const [currentTabName, setCurrentTabName] =
    useState<TabName>("열린 마일스톤");
  const currentList =
    currentTabName === "열린 마일스톤" ? openMilestone : closedMilestone;

  const numOpen = openMilestone.length;
  const numClosed = closedMilestone.length;

  const handleTabClick = (tabName: TabName) => {
    setCurrentTabName(tabName);
  };

  return (
    <Table>
      <MilestonesTableHeader
        {...{ currentTabName, handleTabClick, numOpen, numClosed }}
      />
      <MilestonesTableBody
        milestoneList={currentList}
        {...{ updateOpenMilestone, updateCloseMilestone }}
      />
    </Table>
  );
}
