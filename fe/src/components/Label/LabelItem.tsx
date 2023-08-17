import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as EditIcon } from '../../assets/icon/edit.svg';
import { ReactComponent as TrashIcon } from '../../assets/icon/trash.svg';
import { font } from '../../styles/styles';
import Label from './Label';
import Button from '../common/Button';
import { LabelType } from '../../type/label.type';

export default function LabelItem(label: LabelType) {
  const theme = useTheme();

  return (
    <li css={labelItem(theme)}>
      <div className="tag-container">
        <Label {...label} />
      </div>
      <div className="label-info">{label.description}</div>
      <div className="button-container">
        <Button
          icon={<EditIcon />}
          size="XS"
          color={theme.neutral.textDefault}
          value="편집"
        />
        <Button
          icon={<TrashIcon className="delete" />}
          size="XS"
          color={theme.danger.textDefault}
          value="삭제"
        />
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
  }
`;
