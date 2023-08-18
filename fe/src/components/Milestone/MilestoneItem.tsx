import { Theme, css, useTheme } from '@emotion/react';
import { font, radius } from '../../styles/styles';
import { ReactComponent as MilestoneIcon } from '../../assets/icon/milestone.svg';
import { ReactComponent as CalendarIcon } from '../../assets/icon/calendar.svg';
import { ReactComponent as ArchiveIcon } from '../../assets/icon/archive.svg';
import { ReactComponent as EditIcon } from '../../assets/icon/edit.svg';
import { ReactComponent as DeleteIcon } from '../../assets/icon/trash.svg';
import { getFormatDate } from '../../util/getFormatDate';
import Button from '../common/Button';
import { MilestoneType } from '../../type/milestone.type';
import { OnlySuccessRes } from '../../type/Response.type';
import { customFetch } from '../../util/customFetch';
import { useState } from 'react';
import MilestoneEdit from './MilestoneEdit/MilestoneEdit';

type Props = {
  milestone: MilestoneType;
  onDelete: (id: number) => void;
  onEdit: (milestoneId: number, editedMilestone: MilestoneType) => void;
};

export default function IssueItem({ milestone, onDelete, onEdit }: Props) {
  const theme = useTheme();
  const [isEditing, setIsEditing] = useState(false);

  const onDeleteMilestone = async () => {
    try {
      const response = await customFetch<OnlySuccessRes>({
        method: 'DELETE',
        subUrl: `api/milestones/${milestone.id}`,
      });

      if (response.success) {
        onDelete(milestone.id!);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onChangeMilestoneStatus = () => {
    setIsEditing((prev) => !prev);
  };

  const completionChart =
    milestone.openIssueCount + milestone.closedIssueCount !== 0
      ? Math.floor(
          (milestone.openIssueCount /
            (milestone.openIssueCount + milestone.closedIssueCount)) *
            100
        )
      : 0;

  return (
    <>
      {isEditing ? (
        <MilestoneEdit
          milestone={milestone}
          onEditMilestone={onEdit}
          onChangeStatus={onChangeMilestoneStatus}
        />
      ) : (
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
              <Button
                icon={<ArchiveIcon />}
                size="XS"
                color={theme.neutral.textDefault}
                value="닫기"
                disabled
              />
              <Button
                icon={<EditIcon />}
                size="XS"
                color={theme.neutral.textDefault}
                value="편집"
                onClick={onChangeMilestoneStatus}
              />
              <Button
                icon={<DeleteIcon className="delete" />}
                size="XS"
                color={theme.danger.textDefault}
                value="삭제"
                onClick={onDeleteMilestone}
              />
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
                  <div className="open">
                    열린 이슈 {milestone.openIssueCount}
                  </div>
                  <div className="close">
                    닫힌 이슈 {milestone.closedIssueCount}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </li>
      )}
    </>
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
      display: flex;
      justify-content: flex-end;
      gap: 24px;
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
