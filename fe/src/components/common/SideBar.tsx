import { css, useTheme } from '@emotion/react';
import { useState } from 'react';
import { Button } from './Button';

import { ReactComponent as UserImageSmall } from '@assets/icons/userImageSmall.svg';
import { DropDownIndicator } from './dropDown/DropDownIndicator';
import { DropDownPanel } from './dropDown/DropDownPanel';
import { contributors, labels, milestones } from './dropDown/types';
import { ReactComponent as Milestone } from '@assets/icons/milestone.svg';
import { InformationTag } from './InformationTag';
import { ProgressLabel } from './ProgressLabel';
import { ProgressBar } from './ProgressBar';
type Props = {
  dropdownList?: any[];
  onSingleSelectedMilestone: (index: number) => void;
  onMultipleSelectedAssignee: (index: number) => void;
  onMultipleSelectedLabel: (index: number) => void;
  selectedAssignees: { [key: number]: boolean };
  selectedLabels: { [key: number]: boolean };
  selectedMilestones: { [key: number]: boolean };
};

export const SideBar: React.FC<Props> = ({
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
  selectedAssignees,
  selectedLabels,
  selectedMilestones,
}) => {
  const theme = useTheme() as any;
  const [dropdownSelections, setDropdownSelections] = useState<{
    [key: number]: any[];
  }>({});

  const assigneeOptions = contributors.slice(1);
  const labelOptions = labels.slice(1);
  const milestoneOptions = milestones.slice(1);

  const selectedAssigneeIds = Object.keys(selectedAssignees)
    .filter((id) => selectedAssignees[id])
    .map((id) => parseInt(id) + 1);

  const selectedAssigneesData = assigneeOptions.filter((assignee) =>
    selectedAssigneeIds.includes(assignee.userId),
  );

  const selectedLabelIds = Object.keys(selectedLabels)
    .filter((id) => selectedLabels[id])
    .map((id) => parseInt(id) + 1);

  const selectedLabelsData = labelOptions.filter((label) =>
    selectedLabelIds.includes(label.labelId),
  );

  const selectedMilestoneIds = Object.keys(selectedMilestones)
    .filter((id) => selectedMilestones[id])
    .map((id) => parseInt(id) + 1);

  const selectedMilestonesData = milestoneOptions.filter((milestone) =>
    selectedMilestoneIds.includes(milestone.milestoneId),
  );

  return (
    <div
      css={{
        width: '288px',
        background: theme.neutral.surface.strong,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        borderRadius: theme.radius.l,
      }}
    >
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator indicator="담당자" size="L">
          <DropDownPanel
            panelHeader="담당자 설정"
            alignment="center"
            options={assigneeOptions}
            onSelected={onMultipleSelectedAssignee}
            selectedItems={selectedAssignees}
          />
        </DropDownIndicator>
        {selectedAssigneesData &&
          selectedAssigneesData.map((assignee) => (
            <>
              <div
                css={{
                  position: 'relative',
                  display: 'flex',
                  gap: '8px',
                }}
              >
                <UserImageSmall fill={theme.neutral.surface.bold} />
                <img
                  alt="userImage"
                  src={assignee.image}
                  css={{
                    width: '20px',
                    height: '20px',
                    position: 'absolute',
                    top: 0,
                    left: 0,
                  }}
                />
                <span
                  css={{
                    flex: '1 0 0',
                    font: theme.fonts.displayMedium12,
                    color: theme.neutral.text.strong,
                  }}
                >
                  {assignee.loginId}
                </span>
              </div>
            </>
          ))}
      </div>
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator indicator="레이블" size="L">
          <DropDownPanel
            panelHeader="레이블 설정"
            alignment="center"
            options={labelOptions}
            onSelected={onMultipleSelectedLabel}
            selectedItems={selectedLabels}
          />
        </DropDownIndicator>
        <div
          css={{
            display: 'flex',
            gap: '4px',
            flexWrap: 'wrap',
          }}
        >
          {selectedLabelsData.map((label) => (
            <InformationTag
              size="S"
              fillColor={label.backgroundColor}
              textColor={label.textColor}
              typeVariant="filled"
            >
              {label.name}
            </InformationTag>
          ))}
        </div>
      </div>
      <div
        css={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          padding: '32px',
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          '&:last-child': {
            borderBottom: 'none',
          },
        }}
      >
        <DropDownIndicator indicator="마일스톤" size="L">
          <DropDownPanel
            panelHeader="마일스톤 설정"
            alignment="center"
            options={milestoneOptions}
            onSelected={onSingleSelectedMilestone}
            selectedItems={selectedMilestones}
          />
        </DropDownIndicator>
        {selectedMilestonesData.map((milestone) => (
          <div>
            <ProgressBar progress={milestone.progress} />
            <ProgressLabel name={milestone.name} />
          </div>
        ))}
      </div>
    </div>
  );
};
