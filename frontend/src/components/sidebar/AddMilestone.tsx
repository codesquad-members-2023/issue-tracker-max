import { useCallback, useEffect, useState } from "react";
import { getProgress } from "../../utils/getProgress";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { ElementContainer } from "./ElementContainer";
import { MilestoneElement } from "./MilestoneElement";
import { OptionDiv } from "./Sidebar";

type MilestoneData = {
  id: number;
  name: string;
  issues: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
  selected: boolean;
  onClick: () => void;
};

type AddMilestoneProps = {
  issueMilestone: {
    id: number;
    name: string;
    issues: {
      openedIssueCount: number;
      closedIssueCount: number;
    };
  } | null;
  onMilestoneClick: (id: number | null) => void;
};

export function AddMilestone({
  issueMilestone,
  onMilestoneClick,
}: AddMilestoneProps) {
  const [milestones, setMilestones] = useState<MilestoneData[]>([]);

  const fetchMilestones = useCallback(async () => {
    const response = await fetch("/api/milestones");
    const result = await response.json();

    const milestonesData = result.data.milestones.map(
      (milestone: {
        id: number;
        name: string;
        issues: {
          openedIssueCount: number;
          closedIssueCount: number;
        } | null;
      }) => {
        return {
          id: milestone.id,
          name: milestone.name,
          issues: milestone.issues,
          selected: false,
          onClick: () => {},
        };
      },
    );

    setMilestones(milestonesData);
  }, []);

  useEffect(() => {
    fetchMilestones();
  }, [fetchMilestones]);

  useEffect(() => {
    setMilestones((m) =>
      m.map((item) => ({
        ...item,
        selected: issueMilestone?.id === item.id,
        onClick: () => {
          onMilestoneClick(item.id === issueMilestone?.id ? null : item.id);
        },
      })),
    );
  }, [issueMilestone, onMilestoneClick]);

  const getMilestoneProgress = (issues?: {
    openedIssueCount: number;
    closedIssueCount: number;
  }) =>
    issues ? getProgress(issues.closedIssueCount, issues.openedIssueCount) : 0;

  return (
    <OptionDiv>
      <DropdownContainer
        key="assignees"
        name="담당자"
        optionTitle="담당자 설정"
        options={milestones}
        type="Long"
        alignment="Center"
        autoClose
      />
      {issueMilestone && (
        <ElementContainer direction="Vertical">
          <MilestoneElement
            key={issueMilestone.id}
            progress={getMilestoneProgress(issueMilestone.issues)}
          >
            {issueMilestone.name}
          </MilestoneElement>
        </ElementContainer>
      )}
    </OptionDiv>
  );
}
