import { Theme, css, useTheme } from '@emotion/react';
import Button from '../../common/Button';
import { border, font } from '../../../styles/styles';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { ReactComponent as EditIcon } from '../../../assets/icon/edit.svg';
import { customFetch } from '../../../util/customFetch';
import { OnlySuccessRes } from '../../../type/Response.type';
import LabelEditInputForm from './LabelEditInputForm';
import { useState } from 'react';
import { LabelType } from '../../../type/label.type';

type Props = {
  label: LabelType;
  onEdit: (labelId: number, editedLabel: LabelType) => void;
  onChangeStatus: () => void;
};

export default function LabelEdit({ label, onEdit, onChangeStatus }: Props) {
  const theme = useTheme();
  const [title, setTitle] = useState(label.title);
  const [description, setDescription] = useState(label.description);
  const [textColor, setTextColor] = useState(label.textColor);
  const [backgroundColor, setBackgroundColor] = useState(label.backgroundColor);

  const onPutEditedLabel = async () => {
    const editedLabel = {
      title: title,
      description: description,
      textColor: textColor,
      backgroundColor: backgroundColor,
    };

    try {
      const response = await customFetch<OnlySuccessRes>({
        method: 'PUT',
        subUrl: `api/labels/${label.id}`,
        body: JSON.stringify(editedLabel),
      });

      if (response.success) {
        onEdit(label.id!, { id: label.id, ...editedLabel });
        onChangeStatus();
      }
    } catch (error) {
      console.error(error);
    }
  };

  const onChangeTitle = (title: string) => {
    setTitle(title);
  };

  const onChangeDescription = (description: string) => {
    setDescription(description);
  };

  const onChangeTextColor = (color: string) => {
    switch (color) {
      case 'white':
        setTextColor('white');
        break;
      case 'black':
        setTextColor('black');
        break;
      default:
        break;
    }
  };

  const onChangeBackgroundColor = (color: string) => {
    setBackgroundColor(color);
  };

  return (
    <div css={labelCreate(theme)}>
      <div className="title">레이블 편집</div>
      <LabelEditInputForm
        title={title}
        description={description}
        textColor={textColor}
        backgroundColor={backgroundColor}
        onChangeTitle={onChangeTitle}
        onChangeDescription={onChangeDescription}
        onChangeTextColor={onChangeTextColor}
        onChangeBackgroundColor={onChangeBackgroundColor}
      />
      <div className="buttons">
        <Button
          color={theme.brand.textWeak}
          border={`${border.default} ${theme.brand.borderDefault}`}
          onClick={onChangeStatus}
          value="취소"
          icon={<XSquareIcon />}
        />
        <Button
          color={theme.brand.textDefault}
          backgroundColor={theme.brand.surfaceDefault}
          onClick={onPutEditedLabel}
          value="편집 완료"
          icon={<EditIcon />}
        />
      </div>
    </div>
  );
}

const labelCreate = (theme: Theme) => css`
  width: 1280px;
  height: 337px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 22px;
  padding: 32px;
  background-color: ${theme.neutral.surfaceStrong};
  box-sizing: border-box;
  border-bottom: ${border.default} ${theme.neutral.borderDefault};

  .title {
    font: ${font.displayBold20};
    color: ${theme.neutral.textStrong};
  }

  .buttons {
    margin-left: auto;
    display: flex;
    gap: 24px;
  }
`;
