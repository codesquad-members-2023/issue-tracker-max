import { useState } from "react";
import { styled } from "styled-components";
import { getProgress } from "../../utils/getProgress";
import { InformationTag } from "../InformationTag";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { IconColor } from "../icon/Icon";
import { AssigneeElement } from "./AssigneeElement";
import { ElementContainer } from "./ElementContainer";
import { MilestoneElmeent } from "./MilestoneElement";

type AssigneeData = {
  id: number;
  name: string;
  profile: string;
  selected: boolean;
  onClick: () => void;
};

type LabelData = {
  id: number;
  name: string;
  color: "LIGHT" | "DARK";
  background: IconColor;
  selected: boolean;
  onClick: () => void;
};

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

type OptionData = AssigneeData | LabelData | MilestoneData;

export function Sidebar({
  onAssigneeClick,
  onLabelClick,
  onMilestoneClick,
}: {
  onAssigneeClick: (id: number) => void;
  onLabelClick: (id: number) => void;
  onMilestoneClick: (id: number) => void;
}) {
  const [assignees, setAssignees] = useState<AssigneeData[]>([]);
  const [labels, setLabels] = useState<LabelData[]>([]);
  const [milestones, setMilestones] = useState<MilestoneData[]>([]);

  const url = "https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io";
  const selectedAssignees = assignees.filter((assignee) => assignee.selected);
  const selectedLabels = labels.filter((label) => label.selected);
  const selectedMilestone = milestones.find((milestone) => milestone.selected);

  const fetchData = async (url: string) => {
    const response = await fetch(url);
    const result = await response.json();
    return result.data;
  };

  const onOptionMapper = <T extends OptionData>(
    data: T[],
    id: number,
    multiSelect: boolean,
  ) => {
    const updatedData = data.map((d) => ({
      ...d,
      selected: d.id === id ? !d.selected : multiSelect ? d.selected : false,
    }));
    return updatedData;
  };

  const mapToAssigneeData = (assignee: {
    id: number;
    loginId: string;
    avatarUrl: string;
  }) => ({
    id: assignee.id,
    name: assignee.loginId,
    profile: assignee.avatarUrl,
    selected: false,
    onClick: () => {
      setAssignees((a) => onOptionMapper(a, assignee.id, true));
      onAssigneeClick(assignee.id);
    },
  });

  const mapToLabelData = (label: {
    id: number;
    name: string;
    color: "LIGHT" | "DARK";
    background: IconColor;
  }) => ({
    ...label,
    selected: false,
    onClick: () => {
      setLabels((l) => onOptionMapper(l, label.id, true));
      onLabelClick(label.id);
    },
  });

  const mapToMilestoneData = (milestone: {
    id: number;
    name: string;
    issues: {
      openedIssueCount: number;
      closedIssueCount: number;
    };
  }) => ({
    ...milestone,
    selected: false,
    onClick: () => {
      setMilestones((m) => onOptionMapper(m, milestone.id, false));
      onMilestoneClick(milestone.id);
    },
  });

  const onAssigneeDivHover = async () => {
    if (assignees.length > 0) return;

    const data = await fetchData(`${url}/api/assignees`);
    const assigneesData = data.map(mapToAssigneeData);

    setAssignees(assigneesData);
  };

  const onLabelDivHover = async () => {
    if (labels.length > 0) return;

    const data = await fetchData(`${url}/api/labels`);
    const labelsData = data.labels.map(mapToLabelData);

    setLabels(labelsData);
  };

  const onMilestoneDivHover = async () => {
    if (milestones.length > 0) return;

    const data = await fetchData(`${url}/api/milestones`);
    const milestonesData = data.milestones.map(mapToMilestoneData);

    setMilestones(milestonesData);
  };

  const getMilestoneProgress = (issues?: {
    openedIssueCount: number;
    closedIssueCount: number;
  }) =>
    issues ? getProgress(issues.closedIssueCount, issues.openedIssueCount) : 0;

  return (
    <Div>
      <OptionDiv onMouseEnter={onAssigneeDivHover}>
        <DropdownContainer
          key="assignees"
          name="담당자"
          optionTitle="담당자 설정"
          options={assignees}
          type="Long"
          alignment="Center"
        />
        {selectedAssignees.length > 0 && (
          <ElementContainer direction="Vertical">
            {selectedAssignees.map(({ id, name, profile }) => (
              <AssigneeElement key={id} name={name} profile={profile} />
            ))}
          </ElementContainer>
        )}
      </OptionDiv>
      <OptionDiv onMouseEnter={onLabelDivHover}>
        <DropdownContainer
          key="labels"
          name="레이블 "
          optionTitle="레이블 설정"
          options={labels}
          type="Long"
          alignment="Center"
        />
        {selectedLabels.length > 0 && (
          <ElementContainer direction="Horizontal">
            {selectedLabels.map(({ id, name, background }) => (
              <InformationTag
                key={id}
                value={name}
                size="S"
                fill={background}
                fontColor="LIGHT"
              />
            ))}
          </ElementContainer>
        )}
      </OptionDiv>
      <OptionDiv onMouseEnter={onMilestoneDivHover}>
        <DropdownContainer
          key="milestones"
          name="마일스톤 "
          optionTitle="마일스톤 설정"
          options={milestones}
          type="Long"
          alignment="Center"
        />
        {selectedMilestone && (
          <ElementContainer direction="Vertical">
            {selectedMilestone && (
              <MilestoneElmeent
                key={selectedMilestone.id}
                progress={getMilestoneProgress(selectedMilestone.issues)}
              >
                {selectedMilestone.name}
              </MilestoneElmeent>
            )}
          </ElementContainer>
        )}
      </OptionDiv>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  width: 288px;
  flex-direction: column;
  align-items: flex-start;
  gap: 1px;
  flex-shrink: 0;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme }) => theme.color.neutralBorderDefault};
`;

const OptionDiv = styled.div`
  display: flex;
  padding: 32px;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
  align-self: stretch;
  background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};

  &:first-child {
    border-radius: ${({ theme }) =>
        `${theme.radius.large} ${theme.radius.large}`}
      0px 0px;
  }

  &:last-child {
    border-radius: 0 0
      ${({ theme }) => `${theme.radius.large} ${theme.radius.large}`};
  }
`;
