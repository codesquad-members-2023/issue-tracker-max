import { useState } from 'react';
import { css, useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as Calendar } from '@assets/icons/calendar.svg';
import { ReactComponent as Milestone } from '@assets/icons/milestone.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { ProgressBar } from '@components/common/ProgressBar';
import { ProgressLabel } from '@components/common/ProgressLabel';
import { MilestoneEditTable } from './MilestoneEditTable';
import { TableHeader } from '@components/common/Table/TableHeader';
import { deleteMilestone, editMilestoneStatus } from 'apis/api';
import { Alert } from '@components/common/Alert';
import { Theme } from '@emotion/react/macro';

type Props = {
  milestone: Milestone;
  fetchPageData: () => Promise<void>;
};

export const MilestoneItem: React.FC<Props> = ({
  milestone,
  fetchPageData,
}) => {
  const theme = useTheme() as any;
  const [isEditing, setIsEditing] = useState(false);
  const [isOpenAlert, setIsOpenAlert] = useState(false);

  const onEditMilestoneOpen = () => {
    setIsEditing(true);
  };

  const onEditMilestoneClose = () => {
    setIsEditing(false);
  };

  const onAlertOpen = () => {
    setIsOpenAlert(true);
  };

  const onAlertClose = () => {
    setIsOpenAlert(false);
  };

  const onEditMilestoneStatus = async () => {
    const status = milestone.status === 'open' ? 'closed' : 'open';
    try {
      await editMilestoneStatus(milestone.id, status);
      fetchPageData();
      onAlertClose();
    } catch (error) {
      console.error(error);
    }
  };

  const onDeleteMilestone = async () => {
    try {
      await deleteMilestone(milestone.id);
      fetchPageData();
      onAlertClose();
    } catch (error) {
      console.error(error);
    }
  };

  const formatDeadline = (deadline: string) => {
    if (deadline) {
      return deadline
        .split('-')
        .join('.')
        .slice(0, deadline.indexOf('T'))
        .replace(/-/g, '.');
    }
  };

  const isOpen = milestone.status === 'open';

  return (
    <li css={milestoneItemStyle}>
      {isEditing ? (
        <>
          <MilestoneEditTable
            header={<TableHeader title="마일스톤 편집" />}
            typeVariant="edit"
            milestone={milestone}
            onAddTableClose={onEditMilestoneClose}
            fetchPageData={fetchPageData}
          />
        </>
      ) : (
        <>
          <div
            css={{
              display: 'flex',
              flexDirection: 'column',
              gap: '8px',
            }}
          >
            <div className="milestone-info">
              <div className="milestone-info__title">
                <Milestone className="icon-milestone" />
                <span>{milestone.name}</span>
              </div>
              <div className="milestone-info__date">
                <Calendar className="icon-calendar" />
                <span>{formatDeadline(milestone.deadline ?? '')}</span>
              </div>
            </div>
            <span
              css={{
                width: '100%',
                height: '24px',
                color: theme.neutral.text.weak,
                font: theme.fonts.displayMedium16,
              }}
            >
              {milestone.description}
            </span>
          </div>

          <div
            css={{
              display: 'flex',
              flexDirection: 'column',
              gap: '8px',
              alignItems: 'flex-end',
            }}
          >
            <div
              css={{
                display: 'flex',
                alignItems: 'center',
                gap: '24px',
              }}
            >
              <Button
                typeVariant="ghost"
                size="S"
                css={{
                  width: 'fit-content',
                  padding: '0',
                }}
                onClick={onEditMilestoneStatus}
              >
                {isOpen ? (
                  <>
                    <Archive stroke={theme.neutral.text.default} />
                    닫기
                  </>
                ) : (
                  <>
                    <AlertCircle stroke={theme.neutral.text.default} />
                    열기
                  </>
                )}
              </Button>
              <Button
                typeVariant="ghost"
                size="S"
                css={{
                  width: 'fit-content',
                  padding: '0',
                }}
                onClick={onEditMilestoneOpen}
              >
                <Edit stroke={theme.neutral.text.default} />
                편집
              </Button>
              <Button
                typeVariant="ghost"
                size="S"
                css={{
                  width: 'fit-content',
                  padding: '0',
                  color: theme.danger.text.default,
                }}
                onClick={onAlertOpen}
              >
                <Trash stroke={theme.danger.text.default} />
                삭제
              </Button>
            </div>
            <ProgressBar progress={milestone.progress} />
            <ProgressLabel
              progress={milestone.progress}
              openIssueCount={milestone.openIssueCount}
              closeIssueCount={milestone.closedIssueCount}
            />
          </div>
        </>
      )}
      {isOpenAlert && (
        <Alert
          {...{
            action: 'danger',
            leftButtonText: '취소',
            rightButtonText: '삭제',
            onClose: onAlertClose,
            onConfirm: onDeleteMilestone,
          }}
        >
          <span>해당 마일스톤을 삭제하시겠습니꽈?</span>
        </Alert>
      )}
    </li>
  );
};

const milestoneItemStyle = (theme: Theme) => css`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 32px;
  box-sizing: border-box;

  .milestone-info {
    display: flex;
    align-items: center;
    gap: 16px;

    &__title {
      display: flex;
      align-items: center;
      gap: 8px;

      color: ${theme.neutral.text.strong};
      font: ${theme.fonts.availableMedium16};

      .icon-milestone {
        fill: ${theme.palette.blue};
      }
    }

    &__date {
      display: flex;
      align-items: center;
      gap: 8px;

      color: ${theme.neutral.text.weak};
      font: ${theme.fonts.availableMedium12};

      .icon-calendar {
        stroke: ${theme.neutral.text.weak};
      }
    }
  }
`;
