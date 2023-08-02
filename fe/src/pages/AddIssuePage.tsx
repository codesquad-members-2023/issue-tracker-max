import { useState } from 'react';
import { SideBar } from '@components/common/sideBar/SideBar';

type SelectedItems = {
  [key: number]: boolean;
};

export const AddIssuePage: React.FC = ({}) => {
  // const [assigneesData, setAssigneesData] = useState<any[]>([]);
  // const [labelsData, setLabelsData] = useState<any[]>([]);
  // const [milestonesData, setMilestonesData] = useState<any[]>([]);

  const [selectedAssignees, setSelectedAssignees] = useState<SelectedItems>({});
  const [selectedLabels, setSelectedLabels] = useState<SelectedItems>({});
  const [selectedMilestones, setSelectedMilestones] = useState<SelectedItems>(
    {},
  );

  const onMultipleSelectedAssignee = (index: number) => {
    setSelectedAssignees((prev) => ({ ...prev, [index]: !prev[index] }));
  };
  const onMultipleSelectedLabel = (index: number) => {
    setSelectedLabels((prev) => ({ ...prev, [index]: !prev[index] }));
  };

  const onSingleSelectedMilestone = (index: number) => {
    setSelectedMilestones({ [index]: true });
  };

  const dropdownList = [
    {
      id: 0,
      panelHeader: '담당자 필터',
      indicator: '담당자',
    },
    { id: 1, panelHeader: '레이블 필터', indicator: '레이블' },
    {
      id: 2,
      panelHeader: '마일스톤 필터',
      indicator: '마일스톤',
    },
  ];

  return (
    <>
      <SideBar
        // dropdownList={dropdownList}
        onSingleSelectedMilestone={onSingleSelectedMilestone}
        onMultipleSelectedAssignee={onMultipleSelectedAssignee}
        onMultipleSelectedLabel={onMultipleSelectedLabel}
        selectedAssignees={selectedAssignees}
        selectedLabels={selectedLabels}
        selectedMilestones={selectedMilestones}
      ></SideBar>
    </>
  );
};
