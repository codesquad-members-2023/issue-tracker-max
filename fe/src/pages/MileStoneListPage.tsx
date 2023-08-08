import { useState } from 'react';
import { SubNav } from '@components/milestoneListPage/SubNav';
import { Body } from '@components/milestoneListPage/Body';
type Props = {};

const mockData: MilestonePageData = {
  openMilestonesCount: 1,
  closedMilestoneCount: 2,
  milestones: [
    {
      id: 1,
      name: '마일스톤1',
      description: '마일스톤 설명',
      progress: 70,
      status: 'open',
      openIssueCount: 3,
      closedIssueCount: 7,
      deadline: '2023-08-01T00:00:00',
    },
    {
      id: 2,
      name: '마일스톤2',
      description: '마일스톤 설명',
      progress: 100,
      status: 'open',
      openIssueCount: 0,
      closedIssueCount: 3,
      deadline: '2023-08-01T00:00:00',
    },
  ],
};

export const MileStoneListPage: React.FC<Props> = ({}) => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [isEditMilestoneOpen, setIsEditMilestoneOpen] = useState(false);

  const onAddTableOpen = () => {
    setIsAddTableOpen(true);
  };
  const onAddTableClose = () => {
    setIsAddTableOpen(false);
  };
  const onEditMilestoneClick = () => {};

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        width: '100%',
        gap: '24px',
      }}
    >
      <SubNav
        onAddTableOpen={onAddTableOpen}
        labelCount={17}
        milestoneCount={mockData.milestones.length}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        milestoneList={mockData.milestones}
        openMilestonesCount={mockData.openMilestonesCount}
        closedMilestoneCount={mockData.closedMilestoneCount}
        onEditLabelClick={onEditMilestoneClick}
        onAddTableClose={onAddTableClose}
      />
    </div>
  );
};
