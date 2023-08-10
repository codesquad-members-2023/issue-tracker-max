import { useTheme } from '@emotion/react';
import { useState } from 'react';
import { InformationTag } from '@components/common/InformationTag';
import { TextInput } from '@components/common/textInput/TextInput';
import { ColorCodeInput } from '@components/common/ColorCodeInput';
import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';
import { DropDownPanel } from '@components/common/dropDown/DropDownPanel';
import { DropDownList } from '@components/common/dropDown/DropDownList';
import { textColors } from '@components/common/dropDown/types';
import { randomColorGenerator } from '@utils/generateRandomColorCode';

type Props = {
  nameInput: string;
  descriptionInput: string;
  colorCodeInput: string;
  selectedTextColor?: number | null;
  displayedTextColor: string;
  fillColor?: string;
  textColor?: string;
  isPanelOpen: boolean;
  isNameLengthError?: boolean;
  isColorCodeError?: boolean;
  onNameChange: (value: string) => void;
  onDescriptionChange: (value: string) => void;
  onPanelOpen: () => void;
  onPanelClose: () => void;
  onColorCodeChange: (value: string) => void;
  onColorCodeRandom: () => void;
  onSelectTextColor: (id: number) => void;
};

export const LabelEditBody: React.FC<Props> = ({
  nameInput,
  descriptionInput,
  colorCodeInput,
  selectedTextColor,
  displayedTextColor,
  fillColor,
  textColor,
  isPanelOpen,
  isNameLengthError,
  isColorCodeError,

  onNameChange,
  onDescriptionChange,
  onPanelOpen,
  onPanelClose,
  onColorCodeChange,
  onColorCodeRandom,
  onSelectTextColor,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        display: 'flex',
        gap: '24px',
      }}
    >
      <div
        css={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          minWidth: '288px',
          height: '153px',
          borderRadius: '11px',
          border: `${theme.border.default} ${theme.neutral.border.default}`,
        }}
      >
        <InformationTag
          size="S"
          typeVariant="filled"
          fillColor={fillColor}
          textColor={textColor}
        >
          {nameInput ? nameInput : 'Label'}
        </InformationTag>
      </div>
      <div
        css={{
          width: '100%',
        }}
      >
        <TextInput
          value={nameInput}
          label="이름"
          inputType="text"
          placeholder="레이블의 이름을 입력하세요"
          onChange={onNameChange}
          height={40}
          isError={isNameLengthError}
          captionString="20자 이하로 입력해주세요"
        />
        <TextInput
          value={descriptionInput}
          label="설명(선택)"
          inputType="text"
          placeholder="레이블에 대한 설명을 입력하세요"
          onChange={onDescriptionChange}
          height={40}
        />
        <div
          css={{
            display: 'flex',
            gap: '24px',
            width: 'fit-content',
            alignItems: 'baseline',
          }}
        >
          <ColorCodeInput
            isError={isColorCodeError}
            captionString="올바른 색상 코드를 입력해주세요"
            value={colorCodeInput}
            onChange={onColorCodeChange}
            onRandomButtonClick={onColorCodeRandom}
          />

          <DropDownContainer
            size="defaultSize"
            indicator={displayedTextColor}
            isPanelOpen={isPanelOpen}
            onClick={onPanelOpen}
          >
            <DropDownPanel
              panelHeader="색상 변경"
              position="left"
              onOutsideClick={onPanelClose}
            >
              {textColors?.map((item) => (
                <DropDownList
                  key={item.id}
                  item={item}
                  onClick={() => onSelectTextColor(item.id)}
                  isSelected={selectedTextColor === item.id}
                />
              ))}
            </DropDownPanel>
          </DropDownContainer>
        </div>
      </div>
    </div>
  );
};
