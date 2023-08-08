import { IssueTable } from '@components/issueListPage/IssueTable';
import { SubNav } from '@components/issueListPage/SubNav';
import { getIssueListPageData } from '@utils/api';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

export const IssueListPage: React.FC = ({}) => {
  const [pageData, setPageData] = useState<IssuePageData>(initialPageData);
  const location = useLocation();

  useEffect(() => {
    (async () => {
      const query = location.search;
      const pageData = await getIssueListPageData(query);

      setPageData(pageData);
    })();
  }, [location]);

  return (
    <>
      <SubNav
        {...{
          labelCount: pageData.labelCount,
          milestoneCount: pageData.milestoneCount,
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
