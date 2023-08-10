import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ListSideBar } from '@components/common/sideBar/ListSideBar';
import { SideBar } from '@components/common/sideBar/SideBar';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
type Props = {};

export const SideBarRightPanel: React.FC<Props> = ({}: Props) => {
  const theme = useTheme() as any;

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
        // onSingleSelectedMilestone={onSingleSelectedMilestone}
        // onMultipleSelectedAssignee={onMultipleSelectedAssignee}
        // onMultipleSelectedLabel={onMultipleSelectedLabel}
        // selections={selections}
        />
      </SideBar>
      <Button typeVariant="ghost" size="S">
        <Trash stroke={theme.danger.text.default} />

        <span>이슈 삭제</span>
      </Button>
    </div>
  );
};
