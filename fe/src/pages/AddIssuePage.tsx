import { useState } from 'react';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';

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

  return (
    <>
      <SideBar>
        <ListSideBar
          onSingleSelectedMilestone={onSingleSelectedMilestone}
          onMultipleSelectedAssignee={onMultipleSelectedAssignee}
          onMultipleSelectedLabel={onMultipleSelectedLabel}
          selectedAssignees={selectedAssignees}
          selectedLabels={selectedLabels}
          selectedMilestones={selectedMilestones}
        />
      </SideBar>
    </>
  );
};
