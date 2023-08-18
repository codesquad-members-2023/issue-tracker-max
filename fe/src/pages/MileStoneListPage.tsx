import { useState, useEffect } from 'react';
import { SubNav } from '@components/milestoneListPage/SubNav';
import { Body } from '@components/milestoneListPage/Body';
import { getMilestonesWithQuery } from 'apis/api';
import { useLocation } from 'react-router-dom';

export const MileStoneListPage: React.FC = () => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [milestoneListData, setMilestoneListData] =
    useState<MilestonePageData>(initialData);
  const location = useLocation();

  const onAddTableOpen = () => {
    setIsAddTableOpen(true);
  };
  const onAddTableClose = () => {
    setIsAddTableOpen(false);
  };

  const fetchPageData = async () => {
    const query = location.search || '?status=open';
    const pageData: MilestonePageData = await getMilestonesWithQuery(query);
    setMilestoneListData(pageData);
  };

  const initPageWithFilter = () => {
    fetchPageData();
  };

  useEffect(() => {
    initPageWithFilter();
  }, [window.location.search]);

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
        isAddTableOpen={isAddTableOpen}
        labelCount={milestoneListData.labelCount}
        milestoneCount={milestoneListData.milestoneCount}
        onAddTableOpen={onAddTableOpen}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        milestoneList={milestoneListData.milestones}
        openMilestoneCount={milestoneListData.openMilestoneCount}
        closedMilestoneCount={milestoneListData.closedMilestoneCount}
        onAddTableClose={onAddTableClose}
        fetchPageData={fetchPageData}
      />
    </div>
  );
};
const initialData = {
  openMilestoneCount: 0,
  closedMilestoneCount: 0,
  labelCount: 0,
  milestoneCount: 0,
  milestones: [
    {
      id: 0,
      name: '',
      description: '',
      progress: 0,
      status: 'open',
      openIssueCount: 0,
      closedIssueCount: 0,
      deadline: '2023-08-01T00:00:00',
    },
  ],
};
