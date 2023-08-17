import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import { ReactComponent as DeleteIcon } from '../../../assets/icon/trash.svg';
import { border, font, radius } from '../../../styles/styles';
import Button from '../../common/Button';

type Props = {
  onClick: () => void;
};

export default function SidebarDetail({ onClick }: Props) {
  const theme = useTheme();

  return (
    <div css={sidebar(theme)}>
      <div className="buttons">
        <button className="assignee">
          담당자
          <PlusIcon />
        </button>
        <button className="label">
          레이블
          <PlusIcon />
        </button>
        <button className="milestone">
          마일스톤
          <PlusIcon />
        </button>
      </div>
      <Button
        icon={<DeleteIcon />}
        size="S"
        color={theme.danger.textDefault}
        value="이슈 삭제"
        onClick={onClick}
      />
    </div>
  );
}

const sidebar = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  gap: 16px;

  .buttons {
    display: flex;
    flex-direction: column;
    width: 288px;
    height: 282px;
    border: ${border.default} ${theme.neutral.borderDefault};
    border-radius: ${radius.large};
    background-color: ${theme.neutral.surfaceStrong};

    button {
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex: 1;
      padding: 0 32px;
      background-color: inherit;
      font: ${font.availableMedium16};
      color: ${theme.neutral.textDefault};
    }

    .assignee {
      border-radius: ${radius.large} ${radius.large} 0 0;
      border-bottom: ${border.default} ${theme.neutral.borderDefault};
    }

    .label {
      border-bottom: ${border.default} ${theme.neutral.borderDefault};
    }

    .milestone {
      border-radius: 0 0 ${radius.large} ${radius.large};
    }
  }

  > button {
    margin-left: auto;
  }
`;
