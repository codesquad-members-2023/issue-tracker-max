import { useState, useEffect } from 'react';
import { SubNav } from '@components/milestoneListPage/SubNav';
import { Body } from '@components/milestoneListPage/Body';
import { getMilestonesWithQuery } from 'apis/api';
import { useLocation } from 'react-router-dom';

export const MileStoneListPage: React.FC = () => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [filterValue, setFilterValue] = useState('');
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
    const pageData: MilestonePageData = await getMilestonesWithQuery(
      location.search,
    );
    setMilestoneListData(pageData);
  };

  const initPageWithFilter = () => {
    if (location.search === '') {
      setFilterValue('isOpen');
    } else {
      const value = decodeURIComponent(location.search.replace('?', ''));
      setFilterValue(value);
    }

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
        onAddTableOpen={onAddTableOpen}
        labelCount={milestoneListData.labelCount}
        milestoneCount={milestoneListData.milestoneCount}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        milestoneList={milestoneListData.milestones}
        openMilestonesCount={milestoneListData.openMilestonesCount}
        closedMilestoneCount={milestoneListData.closedMilestoneCount}
        onAddTableClose={onAddTableClose}
      />
    </div>
  );
};
const initialData = {
  openMilestonesCount: 0,
  closedMilestoneCount: 0,
  labelCount: 0,
  milestoneCount: 0,
  milestones: [
    {
      id: 0,
      name: '',
      description: '',
      progress: 0,
      status: 'open', // 'open' 또는 'closed'로 명시적으로 설정합니다.
      openIssueCount: 0,
      closedIssueCount: 0,
      deadline: '2023-08-01T00:00:00',
    },
  ],
};
