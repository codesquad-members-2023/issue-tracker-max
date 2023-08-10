import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
import { useTheme } from '@emotion/react';

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

  return (
    <Box
      header={
        <TableHeader
          {...{
            openIssueCount,
            closedIssueCount,
            goToFilteredPage,
          }}
        />
      }
    >
      <ul>
        {issues.length !== 0 ? (
          issues.map((issue) => <IssueList key={issue.id} issue={issue} />)
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
