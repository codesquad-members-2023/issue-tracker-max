import { useState, useEffect } from 'react';
import { SubNav } from '@components/labelListPage/SubNav';
import { Body } from '@components/labelListPage/Body';
import { getLabelList } from 'apis/api';

export const LabelListPage: React.FC = () => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [labelListData, setLabelListData] = useState<LabelList>(initialData);

  const fetchLabelList = async () => {
    const pageData = await getLabelList();
    setLabelListData(pageData);
  };

  useEffect(() => {
    fetchLabelList();
  }, []);

  const onAddTableOpen = () => {
    setIsAddTableOpen(true);
  };

  const onAddTableClose = () => {
    setIsAddTableOpen(false);
  };

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
        labelCount={labelListData.labelCount}
        milestoneCount={labelListData.milestoneCount}
        isAddTableOpen={isAddTableOpen}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        labelList={labelListData.labels}
        labelCount={labelListData.labelCount}
        onAddTableClose={onAddTableClose}
        fetchLabelList={fetchLabelList}
      />
    </div>
  );
};

const initialData = {
  labelCount: 0,
  milestoneCount: 0,
  labels: [],
};
