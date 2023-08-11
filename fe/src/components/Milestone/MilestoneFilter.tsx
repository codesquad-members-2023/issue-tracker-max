import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as AlertCircleIcon } from '../../assets/icon/alertCircle.svg';
import { ReactComponent as ArchiveIcon } from '../../assets/icon/archive.svg';
import { border, font, radius } from '../../styles/styles';

type Props = {
  openMilestoneCount: number;
  closedMilestoneCount: number;
  filterState: 'open' | 'closed';
  onMilestoneFilterClick: (filter: 'open' | 'closed') => void;
};

export default function MilestoneFilter({
  openMilestoneCount,
  closedMilestoneCount,
  filterState,
  onMilestoneFilterClick: onClick,
}: Props) {
  const theme = useTheme();

  return (
    <div css={milestone(theme)}>
      <div
        className={`open-milestone ${filterState === 'open' ? 'active' : ''}`}
        onClick={() => onClick('open')}
      >
        <AlertCircleIcon color="default" />
        열린 마일스톤({openMilestoneCount})
      </div>
      <div
        className={`close-milestone ${
          filterState === 'closed' ? 'active' : ''
        }`}
        onClick={() => onClick('closed')}
      >
        <ArchiveIcon />
        닫힌 마일스톤({closedMilestoneCount})
      </div>
    </div>
  );
}

const milestone = (theme: Theme) => css`
  height: 64px;
  padding: 0 32px;
  display: flex;
  gap: 24px;
  border-radius: ${radius.medium} ${radius.medium} 0 0;
  border-bottom: ${border.default} ${theme.neutral.borderDefault};
  background-color: ${theme.neutral.surfaceDefault};

  .open-milestone,
  .close-milestone {
    display: flex;
    align-items: center;
    gap: 4px;
    cursor: pointer;
    font: ${font.availableMedium16};
  }

  & .active {
    font: ${font.selectedBold16};
    color: ${theme.neutral.textStrong};

    svg path {
      stroke: ${theme.neutral.textStrong};
    }
  }
`;
