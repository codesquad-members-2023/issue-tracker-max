import { Theme, css, useTheme } from '@emotion/react';
import { font, radius } from '../../styles/styles';
import { ReactComponent as MilestoneIcon } from '/src/assets/icon/milestone.svg';
import { ReactComponent as CalendarIcon } from '/src/assets/icon/calendar.svg';
import { ReactComponent as ArchiveIcon } from '/src/assets/icon/archive.svg';
import { ReactComponent as EditIcon } from '/src/assets/icon/edit.svg';
import { ReactComponent as DeleteIcon } from '/src/assets/icon/trash.svg';
import MilestoneButton from './MilestoneButton';

export default function IssueItem(milestone: Milestone) {
  const theme = useTheme();

  const getFormatDate = (dateString: string) => {
    const date = new Date(dateString);
    const formattedDate = `
    ${date.getFullYear()}. 
    ${String(date.getMonth() + 1).padStart(2, '0')}. 
    ${String(date.getDate()).padStart(2, '0')}
    `;

    return formattedDate;
  };

  const completionChart = Math.floor(
    (milestone.openIssueCount /
      (milestone.openIssueCount + milestone.closedIssueCount)) *
      100
  );

  return (
    <li css={issueItem(theme, completionChart)}>
      <div className="detail">
        <div className="title">
          <div className="milestone-name">
            <MilestoneIcon className="open" />
            {milestone.title}
          </div>
          <div className="due-date">
            {!!milestone.dueDate && (
              <>
                <CalendarIcon />
                {getFormatDate(milestone.dueDate)}
              </>
            )}
          </div>
        </div>
        <div className="description">{milestone.description}</div>
      </div>
      <div className="sub">
        <div className="buttons">
          <MilestoneButton
            color={theme.neutral.textDefault}
            icon={<ArchiveIcon />}
          >
            닫기
          </MilestoneButton>
          <MilestoneButton
            color={theme.neutral.textDefault}
            icon={<EditIcon />}
          >
            편집
          </MilestoneButton>
          <MilestoneButton
            color={theme.danger.textDefault}
            icon={<DeleteIcon />}
          >
            삭제
          </MilestoneButton>
        </div>
        <div className="progress-indicator">
          <progress
            className="progress-bar"
            value={completionChart}
            max={100}
          />
          <div className="progress-info">
            <div className="percent">{completionChart}%</div>
            <div className="counters">
              <div className="open">열린 이슈 {milestone.openIssueCount}</div>
              <div className="close">
                닫힌 이슈 {milestone.closedIssueCount}
              </div>
            </div>
          </div>
        </div>
      </div>
    </li>
  );
}

const issueItem = (theme: Theme, completionChart: number) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 104px;

  .detail {
    display: flex;
    flex-direction: column;
    gap: 14px;

    .title {
      display: flex;
      align-items: center;
      gap: 16px;

      .milestone-name {
        display: flex;
        align-items: center;
        gap: 8px;
        font: ${font.availableMedium16};
        color: ${theme.neutral.textStrong};

        & .open path {
          stroke: ${theme.palette.blue};
        }
      }

      .due-date {
        display: flex;
        align-items: center;
        gap: 8px;
        font: ${font.displayMedium12};
        color: ${theme.neutral.textWeak};
      }
    }

    .description {
      display: flex;
      align-items: center;
      font: ${font.displayMedium16};
      color: ${theme.neutral.textWeak};
    }
  }

  .sub {
    display: flex;
    flex-direction: column;
    gap: 8px;

    .buttons {
      height: 32px;
      display: flex;
      justify-content: flex-end;
      gap: 24px;
      font: ${font.availableMedium12};

      > button {
        display: flex;
        align-items: center;
        gap: 4px;
        background-color: inherit;
      }
    }

    .progress-indicator {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 8px;
      font: ${font.displayMedium12};
      color: ${theme.neutral.textWeak};

      .progress-bar {
        width: 244px;
        height: 8px;
        appearance: none;

        &::-webkit-progress-bar {
          background: ${theme.neutral.surfaceBold};
          border-radius: ${radius.small};
        }

        &::-webkit-progress-value {
          border-radius: ${completionChart === 100
            ? 'inherit'
            : `${radius.small} 0 0 ${radius.small}`};
          background-color: ${theme.palette.blue};
        }
      }

      .progress-info {
        width: 100%;
        display: flex;
        justify-content: space-between;

        .counters {
          display: flex;
          gap: 8px;
        }
      }
    }
  }
`;
