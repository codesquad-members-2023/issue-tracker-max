import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TabButton } from "../../components/TabButton";
import { IconType } from "../../components/icon/Icon";
import { MilestoneData } from "./Milestone";
import { MilestoneTableElement } from "./MilestoneTableElement";

type MilestoneTableProps = {
  milestones: MilestoneData[];
  openCount: number;
  closeCount: number;
  status: "OPENED" | "CLOSED";
};

type Tab = {
  name: string;
  icon: keyof IconType;
  selected: boolean;
  onClick: () => void;
};

export function MilestoneTable({
  milestones,
  openCount,
  closeCount,
  status,
}: MilestoneTableProps) {
  const navigate = useNavigate();

  const tabs: Tab[] = [
    {
      name: `열린 마일스톤(${openCount})`,
      icon: "AlertCircle",
      selected: status === "OPENED",
      onClick: () => {
        navigate("/milestone/opened");
      },
    },
    {
      name: `닫힌 마일스톤(${closeCount})`,
      icon: "Archive",
      selected: status === "CLOSED",
      onClick: () => {
        navigate("/milestone/closed");
      },
    },
  ];

  return (
    <Div>
      <TableHeader>
        <TabButton type="Ghost">
          {tabs.map(({ name, icon, selected, onClick }, index) => (
            <Button
              key={`tab-${index}`}
              icon={icon}
              size="M"
              buttonType="Ghost"
              flexible="Flexible"
              selected={selected}
              onClick={onClick}
            >
              {name}
            </Button>
          ))}
        </TabButton>
      </TableHeader>
      <TableBody>
        {milestones.map((milestone, index) => (
          <MilestoneTableElement
            key={index}
            milestone={milestone}
            status={status}
          />
        ))}
      </TableBody>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background: ${({ theme }) => theme.color.neutralSurfaceStrong};
`;

const TableHeader = styled.div`
  display: flex;
  height: 64px;
  padding: 20px 32px;
  box-sizing: border-box;
  align-items: center;
  align-self: stretch;
  color: ${({ theme }) => theme.color.neutralTextDefault};
  font: ${({ theme }) => theme.font.displayBold16};
  background: ${({ theme }) => theme.color.neutralSurfaceDefault};
  border-top-right-radius: ${({ theme }) => theme.radius.large};
  border-top-left-radius: ${({ theme }) => theme.radius.large};
`;

const TableBody = styled.div`
  width: 100%;
`;
