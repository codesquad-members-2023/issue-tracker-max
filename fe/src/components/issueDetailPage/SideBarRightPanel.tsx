import { useTheme } from '@emotion/react';
import { Theme, css } from '@emotion/react';
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
  selectionsOptions: SelectionState['detailPage'];
  selections: SelectionState['newIssuePage'];
  onChangeSelect?: (key: string) => void;
  onSingleSelectedMilestone: (id: number) => void;
  onMultipleSelectedAssignee: (id: number) => void;
  onMultipleSelectedLabel: (id: number) => void;
};

export const SideBarRightPanel: React.FC<Props> = ({
  issueId,
  //
  selections,
  selectionsOptions,
  onChangeSelect,
  onSingleSelectedMilestone,
  onMultipleSelectedAssignee,
  onMultipleSelectedLabel,
}) => {
  const theme = useTheme() as any;
  const navigate = useNavigate();

  const [isDeleteError, setIsDeleteError] = useState<boolean>(false);

  const onDeleteIssue = async () => {
    try {
      setIsDeleteError(false);
      await deleteIssue(issueId);
      navigate('/issues');
    } catch (error) {
      console.error(error);
      setIsDeleteError(true);
    }
  };

  return (
    <div css={rightPanelStyle}>
      <SideBar>
        <ListSideBar
          onSingleSelectedMilestone={onSingleSelectedMilestone}
          onMultipleSelectedAssignee={onMultipleSelectedAssignee}
          onMultipleSelectedLabel={onMultipleSelectedLabel}
          selectionsOptions={selectionsOptions}
          selections={selections}
          onChangeSelect={onChangeSelect}
        />
      </SideBar>
      <Button typeVariant="ghost" size="S" onClick={onDeleteIssue}>
        <Trash stroke={theme.danger.text.default} />
        <span className="button-delete">이슈 삭제</span>
      </Button>
      {isDeleteError && (
        <span css={{ color: theme.danger.text.default }}>
          이슈 삭제에 실패했습니다.
        </span>
      )}
    </div>
  );
};

const rightPanelStyle = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: flex-end;
  & span {
    color: ${theme.neutral.text.default};
    font: ${theme.fonts.availableMedium16};
  }
  .button-delete {
    color: ${theme.danger.text.default};
  }
`;
