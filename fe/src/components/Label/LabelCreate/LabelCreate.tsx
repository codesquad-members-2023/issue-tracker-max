import { Theme, css, useTheme } from '@emotion/react';
import Button from '../../common/Button';
import { border, font, radius } from '../../../styles/styles';
import { ReactComponent as XSquareIcon } from '../../../assets/icon/xSquare.svg';
import { ReactComponent as PlusIcon } from '../../../assets/icon/plus.svg';
import LabelCreateInputForm from './LabelCreateInputForm';
import { customFetch } from '../../../util/customFetch';
import { PostNewLabelRes } from '../../../type/Response.type';
import { LabelType } from '../../../type/label.type';
import { useContext } from 'react';
import { LabelContext } from '../../Context/LabelContext';

type Props = {
  onChangeStatus: () => void;
  onAddNewLabel: (newLabel: LabelType) => void;
};

export default function LabelCreate({ onChangeStatus, onAddNewLabel }: Props) {
  const theme = useTheme();
  const { ...context } = useContext(LabelContext);

  const onPostNewLabel = async () => {
    const newLabel = {
      title: context.title,
      description: context.description,
      textColor: context.textColor,
      backgroundColor: context.backgroundColor,
    };

    try {
      const response = await customFetch<PostNewLabelRes>({
        method: 'POST',
        subUrl: 'api/labels',
        body: JSON.stringify(newLabel),
      });

      if (response.success) {
        if (response.data) {
          onAddNewLabel({ id: response.data.id, ...newLabel });
          onChangeStatus();
        }
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div css={labelCreate(theme)}>
      <div className="title">새로운 레이블 추가</div>
      <LabelCreateInputForm />
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
          onClick={onPostNewLabel}
          value="완료"
          icon={<PlusIcon />}
        />
      </div>
    </div>
  );
}

const labelCreate = (theme: Theme) => css`
  width: 1280px;
  height: 337px;
  margin: 0 auto 24px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  padding: 32px;
  background-color: ${theme.neutral.surfaceStrong};
  box-sizing: border-box;
  border-radius: ${radius.large};
  border: ${border.default} ${theme.neutral.borderDefault};

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
