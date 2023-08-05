import { IssueTable } from '@components/issueListPage/IssueTable';
import { SubNav } from '@components/issueListPage/SubNav';
import { useEffect, useState } from 'react';

export const IssueListPage: React.FC = ({}) => {
  const [pageData, setPageData] = useState<IssuePageData>(initialPageData);
  const [filterValue, setFilterValue] = useState('status:open');

  const onChangeFilterValue = (value: string) => {
    setFilterValue(value);
  };

  const fetchPageData = async () => {
    const response = await fetch(import.meta.env.VITE_APP_BASE_URL + 'issues');

    if (!response.ok) {
      throw new Error('이슈 목록 가져오기 요청이 실패했습니다.');
    }

    const data = await response.json();

    setPageData(data);
  };

  useEffect(() => {
    fetchPageData();
  }, []);

  return (
    <>
      <SubNav
        {...{
          labelCount: pageData.labelCount,
          milestoneCount: pageData.milestoneCount,
          filterValue,
          onChangeFilterValue,
        }}
      />
      <IssueTable
        {...{
          openIssueCount: pageData.openIssueCount,
          closedIssueCount: pageData.closedIssueCount,
        }}
      />
    </>
  );
};

const initialPageData: IssuePageData = {
  labelCount: 0,
  milestoneCount: 0,
  openIssueCount: 0,
  closedIssueCount: 0,
  issues: [],
};
