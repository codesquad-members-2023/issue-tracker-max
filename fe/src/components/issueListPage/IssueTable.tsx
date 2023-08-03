import { useTheme } from '@emotion/react';
import { TableHeader } from './TableHeader';
import { IssueList } from './IssueList';

type Props = {};

export const IssueTable: React.FC<Props> = ({}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        overflow: 'hidden',
      }}
    >
      <TableHeader />

      <ul
        css={{
          '> li:not(:last-child)': {
            borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
          },
        }}
      >
        <IssueList />
        <IssueList />
        <IssueList />
        <IssueList />
        <IssueList />
      </ul>
    </div>
  );
};
