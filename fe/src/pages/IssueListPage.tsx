import { Button } from '@components/common/Button';
import { IssueTable } from '@components/issueListPage/IssueTable';
import { SubNav } from '@components/issueListPage/SubNav';
import { getIssueListPageData } from 'apis/api';
import { processFilterString } from '@utils/processFilterString';
import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { useTheme } from '@emotion/react';

export const IssueListPage: React.FC = () => {
  const theme = useTheme() as any;
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

  const initPageWithFilter = () => {
    if (location.search === '') {
      setFilterValue('status:open');
    } else {
      const value = decodeURIComponent(location.search.replace('?query=', ''));
      setFilterValue(value);
    }

    fetchPageData();
  };

  useEffect(() => {
    initPageWithFilter();
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

      {hasFilter && (
        <Button
          {...{ typeVariant: 'ghost', size: 'S' }}
          onClick={() => {
            navigate('/');
          }}
          css={{
            display: 'flex',
            alignItems: 'center',
            width: '200px',
            whiteSpace: 'nowrap',
            marginBottom: '20px',
          }}
        >
          <XSquare stroke={theme.neutral.text.default} />
          <span>현재의 검색 필터 및 정렬 지우기</span>
        </Button>
      )}

      <IssueTable
        {...{
          openIssueCount: pageData.openIssueCount,
          closedIssueCount: pageData.closedIssueCount,
          issues: pageData.issues,
          goToFilteredPage,
          initPageWithFilter,
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
