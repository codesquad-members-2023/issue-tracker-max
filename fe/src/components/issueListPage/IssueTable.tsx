import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
import { useTheme } from '@emotion/react';
import { useState } from 'react';

type Props = {
  openIssueCount: IssuePageData['openIssueCount'];
  closedIssueCount: IssuePageData['closedIssueCount'];
  issues: IssuePageData['issues'];
  goToFilteredPage: (filterValue: string) => void;
};

export const IssueTable: React.FC<Props> = ({
  openIssueCount,
  closedIssueCount,
  issues,
  goToFilteredPage,
}) => {
  const theme = useTheme() as any;
  const [checkedIssues, setCheckedIssues] = useState<number[]>([]);

  const isCheckedIssue = checkedIssues.length > 0;

  const toggleCheckAllIssues = () => {
    if (checkedIssues.length > 0) {
      setCheckedIssues([]);

      return;
    }

    setCheckedIssues(issues.map((issue) => issue.id));
  };

  const toggleCheckedIssues = (id: number) => {
    setCheckedIssues((checkedIssues) => {
      if (checkedIssues.includes(id)) {
        return checkedIssues.filter((checkedIssue) => checkedIssue !== id);
      }

      return [...checkedIssues, id];
    });
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
