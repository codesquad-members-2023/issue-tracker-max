import { useState } from 'react';
import { useTheme } from '@emotion/react';
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

  const isOpen = milestone.status === 'open';

  return (
    <li
      css={{
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '32px',

        boxSizing: 'border-box',
      }}
    >
      {isEditing ? (
        <>
          <MilestoneEditTable
            header={<TableHeader title="마일스톤 편집" />}
            typeVariant="edit"
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
            <div
              css={{
                display: 'flex',
                alignItems: 'center',
                gap: '16px',
              }}
            >
              <div
                css={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '8px',
                  color: theme.neutral.text.strong,
                  font: theme.fonts.availableMedium16,
                }}
              >
                <Milestone fill={theme.palette.blue} />
                <span>{milestone.name}</span>
              </div>
              <div
                css={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '8px',
                  color: theme.neutral.text.weak,
                  font: theme.fonts.availableMedium12,
                }}
              >
                <Calendar stroke={theme.neutral.text.weak} />
                <span>{milestone.deadline}</span>
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
                onClick={onDeleteMilestone}
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
    </li>
  );
};
