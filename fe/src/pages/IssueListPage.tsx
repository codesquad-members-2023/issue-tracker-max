import { IssueTable } from '@components/issueListPage/IssueTable';
import { SubNav } from '@components/issueListPage/SubNav';
import { getIssueListPageData } from '@utils/api';
import { processFilterString } from '@utils/processFilterString';
import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export const IssueListPage: React.FC = () => {
  const [filterValue, setFilterValue] = useState('');
  const [pageData, setPageData] = useState<IssuePageData>(initialPageData);
  const navigate = useNavigate();
  const location = useLocation();

  const hasFilter = location.search !== '';

  const goToFilteredPage = (filterValue: string) => {
    const value = hasFilter
      ? processFilterString(decodeURIComponent(filterValue))
      : processFilterString('status:open ' + decodeURIComponent(filterValue));

    navigate(`?query=${value}`);
  };

  const onChangeFilterValue = (value: string) => {
    setFilterValue(value);
  };

  const fetchPageData = async () => {
    const pageData = await getIssueListPageData(location.search);

    setPageData(pageData);
  };

  useEffect(() => {
    if (location.search === '') {
      setFilterValue('status:open');
    } else {
      const value = decodeURIComponent(location.search.replace('?query=', ''));
      setFilterValue(value);
    }

    fetchPageData();
  }, [window.location.search]);

  return (
    <>
      <SubNav
        {...{
          filterValue,
          onChangeFilterValue,
          labelCount: pageData.labelCount,
          milestoneCount: pageData.milestoneCount,
          goToFilteredPage,
        }}
      />
      <IssueTable
        {...{
          openIssueCount: pageData.openIssueCount,
          closedIssueCount: pageData.closedIssueCount,
          issues: pageData.issues,
          goToFilteredPage,
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
