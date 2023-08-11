import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as EditIcon } from '/src/assets/icon/edit.svg';
import { ReactComponent as TrashIcon } from '/src/assets/icon/trash.svg';
import { font } from '../../styles/styles';
import Label from './Label';

export default function LabelItem(label: Label) {
  const theme = useTheme();

  return (
    <li css={labelItem(theme)}>
      <div className="tag-container">
        <Label {...label} />
      </div>
      <div className="label-info">{label.description}</div>
      <div className="button-container">
        <button className="edit-button">
          <EditIcon />
          편집
        </button>
        <button className="delete-button">
          <TrashIcon />
          삭제
        </button>
      </div>
    </li>
  );
}

const labelItem = (theme: Theme) => css`
  .tag-container {
    width: 176px;
    height: 24px;
  }

  .label-info {
    width: 870px;
    height: 24px;
    font: ${font.displayMedium16};
    color: ${theme.neutral.textWeak};
  }

  .button-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 106px;
    gap: 24px;

    button {
      height: 32px;
      display: flex;
      gap: 4px;
      align-items: center;
      background-color: inherit;
      font: ${font.availableMedium12};
    }

    .edit-button {
      color: ${theme.neutral.textDefault};
    }

    .delete-button {
      color: ${theme.danger.textDefault};

      svg path {
        stroke: ${theme.danger.textDefault};
      }
    }
  }
`;
