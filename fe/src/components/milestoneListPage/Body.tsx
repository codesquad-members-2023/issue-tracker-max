import { useNavigate } from 'react-router-dom';
import { useTheme } from '@emotion/react';
import { Box } from '@components/common/box/Box';
import { MilestoneItem } from './MilestoneItem';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { Button } from '@components/common/Button';
import { TableHeader } from '@components/common/Table/TableHeader';
import { MilestoneEditTable } from './MilestoneEditTable';

type Props = {
  isAddTableOpen?: boolean;
  openMilestoneCount: number;
  closedMilestoneCount: number;
  milestoneList: Milestone[];
  onAddTableClose: () => void;
  fetchPageData: () => Promise<void>;
};

export const Body: React.FC<Props> = ({
  isAddTableOpen,
  openMilestoneCount,
  closedMilestoneCount,
  milestoneList,
  onAddTableClose,
  fetchPageData,
}) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  const onMilestoneFilterClick = (queryValue: 'open' | 'closed') => {
    navigate(`?status=${queryValue}`);
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
        <MilestoneEditTable
          header={<TableHeader title="새로운 마일스톤 추가" />}
          typeVariant="add"
          onAddTableClose={onAddTableClose}
          fetchPageData={fetchPageData}
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
                  열린 마일스톤({openMilestoneCount})
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
        <ul>
          {milestoneList.map((milestone) => (
            <MilestoneItem
              key={milestone.id}
              milestone={milestone}
              fetchPageData={fetchPageData}
            />
          ))}
        </ul>
      </Box>
    </div>
  );
};
