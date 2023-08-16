import { Theme, css } from '@emotion/react';
import { InformationTag } from '@components/common/InformationTag';
import { TextInput } from '@components/common/textInput/TextInput';
import { ColorCodeInput } from '@components/common/ColorCodeInput';
import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';
import { DropDownPanel } from '@components/common/dropDown/DropDownPanel';
import { DropDownList } from '@components/common/dropDown/DropDownList';

type Props = {
  nameInput: string;
  descriptionInput: string;
  colorCodeInput: string;
  selectedTextColor?: string | null;
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
  onSelectTextColor: (color: string) => void;
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
  return (
    <div css={labelEditBodyStyle}>
      <div className="label-preview">
        <InformationTag
          size="S"
          typeVariant="filled"
          fillColor={fillColor}
          textColor={textColor}
        >
          {nameInput ? nameInput : 'Label'}
        </InformationTag>
      </div>

      <div className="input-container">
        <TextInput
          value={nameInput}
          label="이름"
          inputType="text"
          placeholder="레이블의 이름을 입력하세요"
          onChange={onNameChange}
          height={40}
          isError={isNameLengthError}
          caption="20자 이하로 입력해주세요"
        />
        <TextInput
          value={descriptionInput}
          label="설명(선택)"
          inputType="text"
          placeholder="레이블에 대한 설명을 입력하세요"
          onChange={onDescriptionChange}
          height={40}
        />

        <div className="color-code-input-container">
          <ColorCodeInput
            isError={isColorCodeError}
            caption="올바른 색상 코드를 입력해주세요"
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
                  onClick={() => onSelectTextColor(item.color)}
                  isSelected={selectedTextColor === item.color}
                />
              ))}
            </DropDownPanel>
          </DropDownContainer>
        </div>
      </div>
    </div>
  );
};

const textColors = [
  { id: 1, color: 'light', name: '밝은 색' },
  { id: 2, color: 'dark', name: '어두운 색' },
];

const labelEditBodyStyle = (theme: Theme) => css`
  display: flex;
  gap: 24px;

  .label-preview {
    display: flex;
    align-items: center;
    justify-content: center;
    min-width: 288px;
    height: 160px;
    border-radius: ${theme.radius.m};
    border: ${`${theme.border.default} ${theme.neutral.border.default}`};
  }

  .input-container {
    width: 100%;
  }

  .color-code-input-container {
    display: flex;
    gap: 24px;
    align-items: baseline;
  }
`;
