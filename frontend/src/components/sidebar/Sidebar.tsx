import { styled } from "styled-components";
import { AddAssignee, AddAssigneeProps } from "./AddAssignee";
import { AddLabel, AddLabelProps } from "./AddLabel";
import { AddMilestone, AddMilestoneProps } from "./AddMilestone";

export type SidebarProps = AddAssigneeProps & AddLabelProps & AddMilestoneProps;

export function Sidebar({
  issueAssignees,
  issueLabels,
  issueMilestone,
  onAssigneeClick,
  onLabelClick,
  onMilestoneClick,
}: SidebarProps) {
  return (
    <Div>
      <AddAssignee
        issueAssignees={issueAssignees}
        onAssigneeClick={onAssigneeClick}
      />
      <AddLabel issueLabels={issueLabels} onLabelClick={onLabelClick} />
      <AddMilestone
        issueMilestone={issueMilestone}
        onMilestoneClick={onMilestoneClick}
      />
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

export const OptionDiv = styled.div`
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
