import { useCallback, useEffect, useState } from "react";
import { getProgress } from "../../utils/getProgress";
import { getAccessToken } from "../../utils/localStorage";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { ElementContainer } from "./ElementContainer";
import { MilestoneElement } from "./MilestoneElement";
import { OptionDiv } from "./Sidebar";

export type IssueMilestone = {
  id: number;
  name: string;
  issues: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
};

export type MilestoneData = {
  id: number;
  name: string;
  issues: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
  selected: boolean;
  onClick: () => void;
};

export type AddMilestoneProps = {
  issueMilestone: IssueMilestone | null;
  onMilestoneClick:
    | {
        args: "Number";
        handler: (id: number | null) => void;
      }
    | {
        args: "Data";
        handler: (milestone: IssueMilestone | null) => void;
      };
};

export function AddMilestone({
  issueMilestone,
  onMilestoneClick,
}: AddMilestoneProps) {
  const [milestones, setMilestones] = useState<MilestoneData[]>([]);
  const [milestonesRes, setMilestonesRes] = useState<
    {
      id: number;
      name: string;
      issues: {
        openedIssueCount: number;
        closedIssueCount: number;
      };
    }[]
  >([]);

  const handleMilestoneClick = useCallback(
    (clickedMilestone: {
      id: number;
      name: string;
      issues: {
        openedIssueCount: number;
        closedIssueCount: number;
      };
    }) => {
      if (onMilestoneClick.args === "Number") {
        onMilestoneClick.handler(
          clickedMilestone.id === issueMilestone?.id
            ? null
            : clickedMilestone.id,
        );
      } else {
        onMilestoneClick.handler(
          clickedMilestone.id === issueMilestone?.id ? null : clickedMilestone,
        );
      }
    },
    [issueMilestone, onMilestoneClick],
  );

  const fetchMilestones = useCallback(async () => {
    const response = await fetch("/api/milestones", {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const result = await response.json();

    setMilestonesRes(result.data.milestones);
  }, []);

  useEffect(() => {
    fetchMilestones();
  }, [fetchMilestones]);

  useEffect(() => {
    const milestonesData = milestonesRes.map(
      (milestone: {
        id: number;
        name: string;
        issues: {
          openedIssueCount: number;
          closedIssueCount: number;
        };
      }) => {
        return {
          id: milestone.id,
          name: milestone.name,
          issues: milestone.issues,
          selected: false,
          onClick: () => handleMilestoneClick(milestone),
        };
      },
    );

    setMilestones(milestonesData);
  }, [milestonesRes, handleMilestoneClick]);

  useEffect(() => {
    setMilestones((milestones) =>
      milestones.map((milestone) => ({
        ...milestone,
        selected: milestone.id === issueMilestone?.id,
        onClick: () => handleMilestoneClick(milestone),
      })),
    );
  }, [issueMilestone, handleMilestoneClick]);

  const getMilestoneProgress = (issues?: {
    openedIssueCount: number;
    closedIssueCount: number;
  }) =>
    issues ? getProgress(issues.closedIssueCount, issues.openedIssueCount) : 0;

  return (
    <OptionDiv>
      <DropdownContainer
        key="assignees"
        name="마일스톤"
        optionTitle="마일스톤 설정"
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
