import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import { Box } from '@components/common/box/Box';
import { MilestoneItem } from './MilestoneItem';
import { TableContainer } from '@components/common/Table/TableContainer';
import { TableHeader } from '@components/common/Table/TableHeader';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { Button } from '@components/common/Button';
import { generateEncodedQuery } from '@utils/generateEncodedQuery';

type Props = {
  isAddTableOpen?: boolean;
  openMilestonesCount: number;
  closedMilestoneCount: number;
  milestoneList: Milestone[];
  onAddTableClose?: () => void;
};

export const Body: React.FC<Props> = ({
  isAddTableOpen,
  openMilestonesCount,
  closedMilestoneCount,
  milestoneList,
  onAddTableClose,
}) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  const onMilestoneFilterClick = (queryValue: 'open' | 'closed') => {
    const query = generateEncodedQuery('status', queryValue);

    navigate(query);
  };

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
        />
      )}

      <Box
        header={
          <>
            <div
              css={{
                display: 'flex',
                gap: '24px',
                textWrap: 'nowrap',
                paddingLeft: '32px',
              }}
            >
              <Button
                typeVariant="ghost"
                onClick={() => onMilestoneFilterClick('open')}
              >
                <AlertCircle stroke={theme.neutral.text.strong} />
                <span css={{ font: theme.fonts.availableMedium16 }}>
                  열린 마일스톤({openMilestonesCount})
                </span>
              </Button>
              <Button
                typeVariant="ghost"
                onClick={() => onMilestoneFilterClick('closed')}
              >
                <Archive stroke={theme.neutral.text.strong} />
                <span css={{ font: theme.fonts.availableMedium16 }}>
                  닫힌 마일스톤({closedMilestoneCount})
                </span>
              </Button>
            </div>
          </>
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
          />
        ))}
      </Box>
    </div>
  );
};
