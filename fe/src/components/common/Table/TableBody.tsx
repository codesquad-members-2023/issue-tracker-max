import { useTheme } from '@emotion/react';
import { useState } from 'react';
import { InformationTag } from '../InformationTag';
import { TextInput } from '../textInput/TextInput';
import { ColorCodeInput } from '../ColorCodeInput';
import { DropDownContainer } from '../dropDown/DropDownContainer';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { DropDownList } from '../dropDown/DropDownList';
import { textColors } from '../dropDown/types';
import { randomColorGenerator } from '@utils/generateRandomColorCode';
type Props = {
  typeVariant: 'add' | 'edit';
  tableVariant?: 'label' | 'milestone'; //추가할 예정
  nameInput?: string;
  descriptionInput?: string;
  colorCodeInput?: string;
  selectedTextColor?: number | null;
  onNameChange?: (value: string) => void;
  onDescriptionChange?: (value: string) => void;
  onPanelOpen?: () => void;
  onPanelClose?: () => void;
  onColorCodeChange?: (value: string) => void;
  onColorCodeRandom?: () => void;
  onSelectTextColor?: (id: number) => void;
};

export const TableBody: React.FC<Props> = ({ tableVariant }) => {
  const theme = useTheme() as any;
  const [nameInput, setNameInput] = useState<string>('');
  const [descriptionInput, setDescriptionInput] = useState<string>('');
  const [colorCodeInput, setColorCodeInput] = useState<string>(
    randomColorGenerator(),
  );
  const [selectedTextColor, setSelectedTextColor] = useState<number | null>(
    null,
  );

  const [isPanelOpen, setIsPanelOpen] = useState<boolean>(false);

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

  const onSelectTextColor = (id: number) => {
    setSelectedTextColor(id);
    onPanelClose();
  };

  const onColorCodeChange = (value: string) => {
    setColorCodeInput(value);
  };

  const onColorCodeRandom = () => {
    const colorCode = randomColorGenerator();

    setColorCodeInput(colorCode);
  };

  const fillColor = colorCodeInput ? colorCodeInput : randomColorGenerator();

  const displayedTextColor =
    selectedTextColor === null
      ? '텍스트 색상'
      : selectedTextColor === 1
      ? '밝은 색'
      : '어두운 색';

  const isNameLengthError = nameInput.length > 20;

  const textColor = selectedTextColor === 1 ? 'light' : 'dark';

  const isColorCodeError =
    !colorCodeInput ||
    colorCodeInput[0] !== '#' ||
    (colorCodeInput.length !== 7 && colorCodeInput.length !== 4) ||
    !/^#[0-9A-Fa-f]{3,6}$/.test(colorCodeInput);
  return (
    <>
      <div
        css={{
          display: 'flex',
          gap: '24px',
        }}
      >
        {tableVariant === 'label' ? (
          <>
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
          </>
        ) : (
          <div
            css={{
              width: '100%',
              display: 'flex',
              flexDirection: 'column',
            }}
          >
            <div
              css={{
                display: 'flex',
                gap: '16px',
              }}
            >
              <TextInput
                value={'titleInput'}
                label="이름"
                inputType="text"
                placeholder="마일스톤의 이름을 입력하세요"
                onChange={() => console.log('onChange')}
                height={40}
              />
              <TextInput
                value={'titleInput'}
                label="이름"
                inputType="date"
                placeholder="마일스톤의 이름을 입력하세요"
                onChange={() => console.log('onChange')}
                height={40}
              />
            </div>
            <TextInput
              value={'titleInput'}
              label="설명(선택)"
              inputType="text"
              placeholder="마일스톤에 대한 설명을 입력하세요"
              onChange={() => console.log('onChange')}
              height={40}
            />
          </div>
        )}
      </div>
    </>
  );
};
