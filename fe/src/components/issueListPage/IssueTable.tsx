import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';
import { Box } from '@components/common/box/Box';
import { BoxHeader } from '@components/common/box/BoxHeader';

type Props = {};

export const IssueTable: React.FC<Props> = ({}) => {
  return (
    <Box>
      <BoxHeader>
        <TableHeader />
      </BoxHeader>

      <ul>
        <IssueList />
        <IssueList />
        <IssueList />
        <IssueList />
        <IssueList />
      </ul>
    </Box>
  );
};
