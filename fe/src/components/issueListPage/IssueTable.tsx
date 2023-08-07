import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
import { BoxHeader } from '@components/common/box/BoxHeader';

type Props = {
  openIssueCount: number;
  closedIssueCount: number;
};

export const IssueTable: React.FC<Props> = ({
  openIssueCount,
  closedIssueCount,
}) => {
  return (
    <Box header={<TableHeader {...{ openIssueCount, closedIssueCount }} />}>
      <IssueList />
      <IssueList />
      <IssueList />
      <IssueList />
      <IssueList />
    </Box>
  );
};
