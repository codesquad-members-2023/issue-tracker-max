import { css, useTheme } from '@emotion/react';
import { DropDownIndicator } from '../dropDown/DropDownIndicator';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { contributors, labels, milestones } from '../dropDown/types';
import { ListAssignee } from './ListAssignee';
import { ListLabel } from './ListLabel';
import { ListMilstone } from './ListMilestone';

type Props = {
  onSingleSelectedMilestone: (index: number) => void;
  onMultipleSelectedAssignee: (index: number) => void;
  onMultipleSelectedLabel: (index: number) => void;
  selectedAssignees: { [key: number]: boolean };
  selectedLabels: { [key: number]: boolean };
  selectedMilestones: { [key: number]: boolean };
};

export const ListSideBar: React.FC<Props> = ({
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
  selectedAssignees,
  selectedLabels,
  selectedMilestones,
}) => {
  const theme = useTheme() as any;
  // 비슷한 자료 형태라 묶어주는 작업이 필요할듯
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

  // 페이지에서 기능 붙이면서 문제 없으면 배열로 만들어서 렌더링하는 방식으로 바꿔보기
  return (
    <>
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
        <ListAssignee selectedAssigneesData={selectedAssigneesData} />
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
        <ListLabel selectedLabelsData={selectedLabelsData} />
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
        <ListMilstone selectedMilestonesData={selectedMilestonesData} />
      </div>
    </>
  );
};
