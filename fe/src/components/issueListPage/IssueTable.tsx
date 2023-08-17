import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
import { useTheme } from '@emotion/react';
import { useState } from 'react';
import { editIssueStatus } from 'apis/api';
import { useNavigate } from 'react-router-dom';

type Props = {
  openIssueCount: IssuePageData['openIssueCount'];
  closedIssueCount: IssuePageData['closedIssueCount'];
  issues: IssuePageData['issues'];
  goToFilteredPage: (filterValue: string) => void;
  initPageWithFilter: () => void;
};

export const IssueTable: React.FC<Props> = ({
  openIssueCount,
  closedIssueCount,
  issues,
  goToFilteredPage,
  initPageWithFilter,
}) => {
  const theme = useTheme() as any;
  const [checkedIssues, setCheckedIssues] = useState<number[]>([]);
  const navigate = useNavigate();

  const isCheckedIssue = checkedIssues.length > 0;

  const toggleCheckAllIssues = () => {
    if (isCheckedIssue) {
      initCheckedIssues();

      return;
    }

    setCheckedIssues(issues.map((issue) => issue.id));
  };

  const initCheckedIssues = () => {
    setCheckedIssues([]);
  };

  const toggleCheckedIssues = (id: number) => {
    setCheckedIssues((checkedIssues) => {
      if (checkedIssues.includes(id)) {
        return checkedIssues.filter((checkedIssue) => checkedIssue !== id);
      }

      return [...checkedIssues, id];
    });
  };

  const requestStatusChange = async (status: Issue['status']) => {
    await editIssueStatus(checkedIssues, status);
    navigate(location.search);
  };

  return (
    <Box
      header={
        <TableHeader
          {...{
            openIssueCount,
            closedIssueCount,
            goToFilteredPage,
            toggleCheckAllIssues,
            isCheckedIssue,
            checkedIssuesCount: checkedIssues.length,
            requestStatusChange,
            initPageWithFilter,
            initCheckedIssues,
          }}
        />
      }
    >
      <ul>
        {issues.length !== 0 ? (
          issues.map((issue) => (
            <IssueList
              key={issue.id}
              {...{
                issue,
                isChecked: checkedIssues.includes(issue.id),
                toggleCheckedIssues,
              }}
            />
          ))
        ) : (
          <li
            css={{
              backgroundColor: theme.neutral.surface.strong,
            }}
          >
            <span
              css={{
                display: 'flex',
                justifyContent: 'center',
                padding: '36px 80px',
                font: theme.fonts.displayMedium16,
                color: theme.neutral.text.weak,
              }}
            >
              등록된 이슈가 없습니다.
            </span>
          </li>
        )}
      </ul>
    </Box>
  );
};
