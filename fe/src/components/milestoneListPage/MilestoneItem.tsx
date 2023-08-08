import { useState } from 'react';

import { useTheme } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { ReactComponent as Calendar } from '@assets/icons/calendar.svg';
import { ReactComponent as Milestone } from '@assets/icons/milestone.svg';
import { TableContainer } from '@components/common/Table/TableContainer';
import { TableHeader } from '@components/common/Table/TableHeader';
import { ProgressBar } from '@components/common/ProgressBar';
import { ProgressLabel } from '@components/common/ProgressLabel';

type Props = {
  name: string;
  description: string;
  progress: number;
  openIssueCount: number;
  closedIssueCount: number;
  deadline: string;
  // onEditLabelClick?: (id: number) => void;
};

export const MilestoneItem: React.FC<Props> = ({
  name,
  description,
  progress,
  openIssueCount,
  closedIssueCount,
  deadline,

  // onEditLabelClick,
}) => {
  const theme = useTheme() as any;
  const [isEditing, setIsEditing] = useState(false);

  const onEditMilestoneOpen = () => {
    setIsEditing(true);
  };

  const onEditMilestoneClose = () => {
    setIsEditing(false);
  };

  return (
    <li
      css={{
        // height: '96px',
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '32px',

        boxSizing: 'border-box',
      }}
    >
      {isEditing ? (
        <TableContainer
          tableVariant="milestone"
          typeVariant="edit"
          onAddTableClose={onEditMilestoneClose}
          header={<TableHeader title="마일스톤 편집" />}
        ></TableContainer>
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
                <Milestone stroke={theme.palette.blue} />
                <span>{name}</span>
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
                <span>{deadline}</span>
              </div>
            </div>
            <span
              css={{
                width: '100%',
                color: theme.neutral.text.weak,
                font: theme.fonts.displayMedium16,
              }}
            >
              {description}
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
                onClick={() => console.log('닫기')}
              >
                <Archive stroke={theme.neutral.text.default} />
                닫기
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
              >
                <Trash stroke={theme.danger.text.default} />
                삭제
              </Button>
            </div>
            <ProgressBar progress={progress} />
            <ProgressLabel
              progress={progress}
              openIssueCount={openIssueCount}
              closeIssueCount={closedIssueCount}
            />
          </div>
        </>
      )}
    </li>
  );
};
