import { useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { Button } from '@components/common/Button';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { deleteIssue } from 'apis/api';

type Props = {
  issueId: number;
  //이슈 데이터 받아와서 선택된 라벨, 마일스톤, 담당자 받아와야함
};

export const SideBarRightPanel: React.FC<Props> = ({ issueId }: Props) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  const [isDeleteError, setIsDeleteError] = useState<boolean>(false);

  const onDeleteIssue = async () => {
    try {
      setIsDeleteError(false);
      await deleteIssue(issueId);
      navigate('/');
    } catch (error) {
      console.error(error);
      setIsDeleteError(true);
    }
  };

  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        gap: '16px',
        alignItems: 'flex-end',
        '& span': {
          color: theme.danger.text.default,
          font: theme.fonts.availableMedium12,
        },
      }}
    >
      <SideBar>
        <ListSideBar
          onSingleSelectedMilestone={() => console.log('1')}
          onMultipleSelectedAssignee={() => console.log('2')}
          onMultipleSelectedLabel={() => console.log('3')}
          selections={{
            assignees: [],
            labels: [],
            milestones: null,
          }}
        />
      </SideBar>
      <Button typeVariant="ghost" size="S" onClick={onDeleteIssue}>
        <Trash stroke={theme.danger.text.default} />

        <span>이슈 삭제</span>
      </Button>
      {isDeleteError && (
        <span css={{ color: theme.danger.text.default }}>
          이슈 삭제에 실패했습니다.
        </span>
      )}
    </div>
  );
};
