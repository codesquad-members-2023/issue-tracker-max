import { useTheme } from '@emotion/react';
import { useState } from 'react';
import { Button } from '@components/common/Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { randomColorGenerator } from '@utils/generateRandomColorCode';
import { LabelEditBody } from './LabelEditBody';
import { editLabel, postNewLabel } from 'apis/api';

type Props = {
  label?: Label;
  typeVariant: 'add' | 'edit';
  header: React.ReactNode;
  onAddTableClose: () => void;
  fetchLabelList: () => Promise<void>;
};

export const LabelEditTable: React.FC<Props> = ({
  label,
  typeVariant,
  header,
  onAddTableClose,
  fetchLabelList,
}) => {
  const theme = useTheme() as any;
  const [nameInput, setNameInput] = useState<string>(label?.name ?? '');
  const [descriptionInput, setDescriptionInput] = useState<string>(
    label?.description ?? '',
  );
  const [colorCodeInput, setColorCodeInput] = useState<string>(
    label?.backgroundColor ?? randomColorGenerator(),
  );
  const [selectedTextColor, setSelectedTextColor] = useState<string | null>(
    label ? (label.textColor === 'light' ? 'light' : 'dark') : null,
  );
  const [isPanelOpen, setIsPanelOpen] = useState<boolean>(false);
  const trimedName = nameInput.trim();

  const onNameChange = (value: string) => {
    if (value.length > 21) return;
    setNameInput(value);
  };

  const onDescriptionChange = (value: string) => {
    setDescriptionInput(value);
  };

  const onPanelOpen = () => {
    setIsPanelOpen(true);
  };

  const onPanelClose = () => {
    setIsPanelOpen(false);
  };

  const onSelectTextColor = (color: string) => {
    setSelectedTextColor(color);
    onPanelClose();
  };

  const onColorCodeChange = (value: string) => {
    setColorCodeInput(value);
  };

  const onColorCodeRandom = () => {
    const colorCode = randomColorGenerator();

    setColorCodeInput(colorCode);
  };

  const onSubmit = async () => {
    try {
      if (typeVariant === 'edit' && label?.id) {
        await editLabel(
          label.id,
          trimedName,
          textColor,
          colorCodeInput,
          descriptionInput || '',
        );
      } else {
        await postNewLabel(
          trimedName,
          textColor,
          colorCodeInput,
          descriptionInput || '',
        );
      }
      onAddTableClose();
      await fetchLabelList();
    } catch (error) {
      console.error('There was a problem with the fetch operation:');
      // todo 에러처리
    }
  };

  const isDataMissing = !nameInput || !colorCodeInput || !selectedTextColor;

  const isDataUnchanged =
    nameInput === (label ? label.name : '') &&
    descriptionInput === (label ? label.description : '') &&
    colorCodeInput === (label ? label.backgroundColor : '') &&
    selectedTextColor ===
      (label ? (label.textColor === 'light' ? 'light' : 'dark') : null);

  const isColorCodeError =
    !colorCodeInput ||
    colorCodeInput[0] !== '#' ||
    (colorCodeInput.length !== 7 && colorCodeInput.length !== 4) ||
    !/^#[0-9A-Fa-f]{3,6}$/.test(colorCodeInput);

  const fillColor = colorCodeInput || randomColorGenerator();

  const displayedTextColor =
    selectedTextColor === null
      ? '텍스트 색상'
      : selectedTextColor === 'light'
      ? '밝은 색'
      : '어두운 색';

  const isNameLengthError = nameInput.length > 20;

  const textColor = selectedTextColor === 'light' ? 'light' : 'dark';

  const addTypeStyle = {
    border: `${theme.border.default}  ${theme.neutral.border.default}`,
    borderRadius: theme.radius.l,
    padding: '32px',
  };

  return (
    <div
      css={{
        width: '100%',
        boxSizing: 'border-box',
        height: 'fit-content',
        display: 'flex',
        backgroundColor: theme.neutral.surface.strong,
        flexDirection: 'column',
        gap: '24px',
        ...(typeVariant === 'add' ? addTypeStyle : {}),
      }}
    >
      {header}
      <LabelEditBody
        nameInput={trimedName}
        descriptionInput={descriptionInput}
        colorCodeInput={colorCodeInput}
        selectedTextColor={selectedTextColor}
        fillColor={fillColor}
        textColor={textColor}
        displayedTextColor={displayedTextColor}
        isNameLengthError={isNameLengthError}
        isColorCodeError={isColorCodeError}
        isPanelOpen={isPanelOpen}
        onNameChange={onNameChange}
        onDescriptionChange={onDescriptionChange}
        onPanelOpen={onPanelOpen}
        onPanelClose={onPanelClose}
        onSelectTextColor={onSelectTextColor}
        onColorCodeChange={onColorCodeChange}
        onColorCodeRandom={onColorCodeRandom}
      />

      <div
        css={{
          display: 'flex',
          gap: '16px',
          justifyContent: 'flex-end',
        }}
      >
        <Button
          typeVariant="outline"
          size="S"
          css={{
            color: theme.brand.text.weak,
          }}
          onClick={onAddTableClose}
        >
          <XSquare stroke={theme.brand.text.weak} />
          취소
        </Button>
        <Button
          typeVariant="contained"
          size="S"
          css={{
            color: theme.brand.text.default,
          }}
          disabled={isDataMissing || isDataUnchanged || isColorCodeError}
          onClick={onSubmit}
        >
          {typeVariant === 'edit' ? (
            <Edit stroke={theme.brand.text.default} />
          ) : (
            <Plus stroke={theme.brand.text.default} />
          )}
          {typeVariant === 'edit' ? '편집 완료' : '완료'}
        </Button>
      </div>
    </div>
  );
};
