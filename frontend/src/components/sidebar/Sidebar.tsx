import { styled } from "styled-components";
import { getProgress } from "../../utils/getProgress";
import { InformationTag } from "../InformationTag";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { AssigneeElement } from "./AssigneeElement";
import { ElementContainer } from "./ElementContainer";
import { MilestoneElmeent } from "./MilestoneElement";
import { IconColor } from "../icon/Icon";

type AssigneeOptionData = {
  id: number;
  name: string;
  profile?: string;
  selected: boolean;
  onClick: () => void;
};

type LabelOptionData = {
  id: number;
  name: string;
  background?: IconColor;
  color?: "LIGHT" | "DARK";
  selected: boolean;
  onClick: () => void;
};

type MilestoneOptionData = {
  id: number;
  name: string;
  selected: boolean;
  issues?: {
    openedIssueCount: number;
    closedIssueCount: number;
  };
  onClick: () => void;
};

export type SidebarOptionData =
  | AssigneeOptionData
  | LabelOptionData
  | MilestoneOptionData;

type SidebarProps = {
  assigneeOptions: AssigneeOptionData[];
  labelOptions: LabelOptionData[];
  milestoneOptions: MilestoneOptionData[];
};

export function Sidebar({
  assigneeOptions,
  labelOptions,
  milestoneOptions,
}: SidebarProps) {
  const collectSelectedOptions = <T extends SidebarOptionData>(
    options: T[],
  ): T[] => {
    return options.filter((option) => option.selected);
  };

  const getMilestoneProgress = (issues?: {
    openedIssueCount: number;
    closedIssueCount: number;
  }) =>
    issues ? getProgress(issues.closedIssueCount, issues.openedIssueCount) : 0;

  const selectedAssigneeOptions = collectSelectedOptions(assigneeOptions);
  const selectedLabelOptions = collectSelectedOptions(labelOptions);
  const selectedMilestoneOptions = collectSelectedOptions(milestoneOptions);

  return (
    <Div>
      <OptionDiv>
        <DropdownContainer
          key="assignees"
          name="담당자"
          optionTitle="담당자 설정"
          options={assigneeOptions}
          type="Long"
          alignment="Center"
        />
        {selectedAssigneeOptions.length > 0 && (
          <ElementContainer direction="Vertical">
            {selectedAssigneeOptions.map(({ id, name, profile }) => (
              <AssigneeElement key={id} name={name} profile={profile} />
            ))}
          </ElementContainer>
        )}
      </OptionDiv>
      <OptionDiv>
        <DropdownContainer
          key="labels"
          name="레이블 "
          optionTitle="레이블 설정"
          options={labelOptions}
          type="Long"
          alignment="Center"
        />
        {selectedLabelOptions.length > 0 && (
          <ElementContainer direction="Horizontal">
            {selectedLabelOptions.map(({ id, name, background }) => (
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
      <OptionDiv>
        <DropdownContainer
          key="milestones"
          name="마일스톤 "
          optionTitle="마일스톤 설정"
          options={milestoneOptions}
          type="Long"
          alignment="Center"
        />
        {selectedMilestoneOptions.length > 0 && (
          <ElementContainer direction="Vertical">
            {selectedMilestoneOptions.map(({ id, name, issues }) => (
              <MilestoneElmeent
                key={id}
                progress={getMilestoneProgress(issues)}
              >
                {name}
              </MilestoneElmeent>
            ))}
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
