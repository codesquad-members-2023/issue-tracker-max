import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import TabBar from "@components/common/TabBar";
import { TableHeader } from "../Table.style";

export default function MilestonesTableHeader({
  currentTabName,
  handleTabClick,
  numOpen,
  numClosed,
}: {
  currentTabName: "열린 마일스톤" | "닫힌 마일스톤";
  handleTabClick: (tabName: "열린 마일스톤" | "닫힌 마일스톤") => void;
  numOpen: number;
  numClosed: number;
}) {
  const tabBarLeftInfo = {
    name: "열린 마일스톤",
    count: numOpen,
    iconSrc: alertIcon,
    callback: () => handleTabClick("열린 마일스톤"),
  };
  const tabBarRightInfo = {
    name: "닫힌 마일스톤",
    count: numClosed,
    iconSrc: archiveIcon,
    callback: () => handleTabClick("닫힌 마일스톤"),
  };

  return (
    <TableHeader>
      <TabBar
        currentTabName={currentTabName}
        left={tabBarLeftInfo}
        right={tabBarRightInfo}
        borderStyle="none"
      />
    </TableHeader>
  );
}
