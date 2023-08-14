import { useState } from 'react';
import { Theme, css } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Trash } from '@assets/icons/trash.svg';
import { TableHeader } from '@components/common/Table/TableHeader';
import { LabelEditTable } from './LabelEditTable';
import { deleteLabel } from 'apis/api';
import { Alert } from '@components/common/Alert';

type Props = {
  label: Label;
  fetchLabelList: () => Promise<void>;
};

export const LabelItem: React.FC<Props> = ({ label, fetchLabelList }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [isOpenAlert, setIsOpenAlert] = useState(false);

  const onEditLabelOpen = () => {
    setIsEditing(true);
  };

  const onEditLabelClose = () => {
    setIsEditing(false);
  };

  const onAlertOpen = () => {
    setIsOpenAlert(true);
  };

  const onAlertClose = () => {
    setIsOpenAlert(false);
  };

  const onDeleteLabel = async () => {
    try {
      await deleteLabel(label.id);
      fetchLabelList();
      onAlertClose();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <li css={labelItemStyle}>
      {isEditing ? (
        <LabelEditTable
          header={<TableHeader title="레이블 편집" />}
          fetchLabelList={fetchLabelList}
          label={label}
          typeVariant="edit"
          onAddTableClose={onEditLabelClose}
        />
      ) : (
        <>
          <div className="label-area">
            <InformationTag
              size="S"
              typeVariant="filled"
              fillColor={label.backgroundColor}
              textColor={label.textColor}
            >
              {label.name}
            </InformationTag>
          </div>

          <div className="description">{label.description}</div>

          <div className="button-container">
            <Button
              className="edit-button"
              typeVariant="ghost"
              size="S"
              onClick={onEditLabelOpen}
            >
              <Edit />
              편집
            </Button>
            <Button
              className="delete-button"
              typeVariant="ghost"
              size="S"
              onClick={onAlertOpen}
            >
              <Trash />
              삭제
            </Button>
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
            onConfirm: onDeleteLabel,
          }}
        >
          <span>해당 레이블을 삭제하시겠습니까?</span>
        </Alert>
      )}
    </li>
  );
};

const labelItemStyle = (theme: Theme) => css`
  padding: 32px;
  display: flex;
  align-items: center;
  gap: 32px;

  .label-area {
    min-width: 176px;
  }

  .description {
    width: 100%;
    color: ${theme.neutral.text.weak};
    font: ${theme.fonts.displayMedium16};
  }

  .button-container {
    min-width: 106px;
    display: flex;
    gap: 24px;

    .edit-button,
    .delete-button {
      width: 43px;
      padding: 0;
      gap: 4px;
    }

    .edit-button {
      stroke: ${theme.neutral.text.default};
    }

    .delete-button {
      color: ${theme.danger.text.default};
      stroke: ${theme.danger.text.default};
    }
  }
`;
