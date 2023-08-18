import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as EditIcon } from '../../assets/icon/edit.svg';
import { ReactComponent as TrashIcon } from '../../assets/icon/trash.svg';
import { font } from '../../styles/styles';
import Label from './Label';
import Button from '../common/Button';
import { LabelType } from '../../type/label.type';
import { customFetch } from '../../util/customFetch';
import { OnlySuccessRes } from '../../type/Response.type';
import { useState } from 'react';
import LabelEdit from './LabelEdit/LabelEdit';

type Props = {
  label: LabelType;
  onDelete: (labelId: number) => void;
  onEdit: (labelId: number, editedLabel: LabelType) => void;
};

export default function LabelItem({ label, onDelete, onEdit }: Props) {
  const theme = useTheme();
  const [isEditing, setIsEditing] = useState(false);

  const onDeleteLabel = async () => {
    try {
      const response = await customFetch<OnlySuccessRes>({
        method: 'DELETE',
        subUrl: `api/labels/${label.id}`,
      });

      if (response.success) {
        onDelete(label.id!);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onChangeLabelStatus = () => {
    setIsEditing((prev) => !prev);
  };

  return (
    <>
      {isEditing ? (
        <LabelEdit
          label={label}
          onEdit={onEdit}
          onChangeStatus={onChangeLabelStatus}
        />
      ) : (
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
              onClick={() => setIsEditing(true)}
            />
            <Button
              icon={<TrashIcon className="delete" />}
              size="XS"
              color={theme.danger.textDefault}
              value="삭제"
              onClick={onDeleteLabel}
            />
          </div>
        </li>
      )}
    </>
  );
}

const labelItem = (theme: Theme) => css`
  .tag-container {
    width: 176px;
    height: 24px;
    text-align: center;
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
