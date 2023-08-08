import { useTheme } from '@emotion/react';
import { Box } from '@components/common/box/Box';
import { MilestoneItem } from './MilestoneItem';
import { TableContainer } from '@components/common/Table/TableContainer';
import { TableHeader } from '@components/common/Table/TableHeader';

type Props = {
  isAddTableOpen?: boolean;
  openMilestonesCount: number;
  closedMilestoneCount: number;
  milestoneList: Milestone[];
  onEditLabelClick?: (id: number) => void;
  onAddTableClose?: () => void;
};

export const Body: React.FC<Props> = ({
  isAddTableOpen,
  openMilestonesCount,
  closedMilestoneCount,
  milestoneList,
  onEditLabelClick,
  onAddTableClose,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
      }}
    >
      {isAddTableOpen && (
        <TableContainer
          tableVariant="milestone"
          typeVariant="add"
          onAddTableClose={onAddTableClose}
          header={<TableHeader title="새로운 마일스톤 추가" />}
        ></TableContainer>
      )}

      <Box
        header={
          <span
            css={{
              marginLeft: '32px',
              color: theme.neutral.text.default,
              font: theme.fonts.displayBold16,
            }}
          >
            열린 마일스톤({openMilestonesCount})
          </span>
        }
      >
        {milestoneList.map((milestone) => (
          <MilestoneItem
            key={milestone.id}
            name={milestone.name}
            description={milestone.description}
            progress={milestone.progress}
            openIssueCount={milestone.openIssueCount}
            closedIssueCount={milestone.closedIssueCount}
            deadline={milestone.deadline}
            // onEditLabelClick={onEditLabelClick}
          />
        ))}
      </Box>
    </div>
  );
};
