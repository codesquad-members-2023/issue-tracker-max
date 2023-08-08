import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
// import { BoxHeader } from '@components/common/box/BoxHeader';

type Props = {
  openIssueCount: IssuePageData['openIssueCount'];
  closedIssueCount: IssuePageData['closedIssueCount'];
  issues: IssuePageData['issues'];
};

export const IssueTable: React.FC<Props> = ({
  openIssueCount,
  closedIssueCount,
  issues,
}) => {
  return (
    <Box header={<TableHeader {...{ openIssueCount, closedIssueCount }} />}>
      {issues.map((issue) => (
        <IssueList key={issue.id} issue={issue} />
      ))}
    </Box>
  );
};
