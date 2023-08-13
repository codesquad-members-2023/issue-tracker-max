import { useState, useEffect } from 'react';
import { SubNav } from '@components/labelListPage/SubNav';
import { Body } from '@components/labelListPage/Body';
import { getLabelListPageData } from 'apis/api';
type Props = {};

export const LabelListPage: React.FC<Props> = ({}) => {
  const [isAddTableOpen, setIsAddTableOpen] = useState(false);
  const [labelListData, setLabelListData] = useState<Label[]>([]);

  const fetchLabelList = async () => {
    const pageData = await getLabelListPageData();
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
        labelCount={labelListData.length}
        milestoneCount={13}
        isAddTableOpen={isAddTableOpen}
      />
      <Body
        isAddTableOpen={isAddTableOpen}
        labelList={labelListData}
        labelCount={labelListData.length}
        onAddTableClose={onAddTableClose}
        fetchLabelList={fetchLabelList}
      />
    </div>
  );
};
