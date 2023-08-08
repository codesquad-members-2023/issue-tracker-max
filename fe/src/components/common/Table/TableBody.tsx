import { useTheme } from '@emotion/react';
import { TableHeader } from './TableHeader';
import { InformationTag } from '../InformationTag';
import { TextInput } from '../textInput/TextInput';
import { ColorCodeInput } from '../ColorCodeInput';
import { DropDownContainer } from '../dropDown/DropDownContainer';
import { DropDownPanel } from '../dropDown/DropDownPanel';
import { DropDownList } from '../dropDown/DropDownList';
import { textColors } from '../dropDown/types';
import { Button } from '../Button';
import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { InputContainer } from '../textInput/InputContainer';
type Props = {
  typeVariant: 'add' | 'edit';
  tableVariant: 'label' | 'milestone';
};

export const TableBody: React.FC<Props> = ({ tableVariant, typeVariant }) => {
  const theme = useTheme() as any;
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
                width: '288px',
                height: '153px',
                borderRadius: '11px',
                border: `${theme.border.default} ${theme.neutral.border.default}`,
              }}
            >
              <InformationTag
                size="S"
                typeVariant="filled"
                fillColor="#ffffff"
                textColor="#000000"
              >
                버그
              </InformationTag>
            </div>
            <div
              css={{
                width: '100%',
              }}
            >
              <TextInput
                value={'titleInput'}
                label="이름"
                inputType="text"
                placeholder="레이블의 이름을 입력하세요"
                onChange={() => console.log('onChange')}
                height={40}
              ></TextInput>
              <TextInput
                value={'titleInput'}
                label="설명(선택)"
                inputType="text"
                placeholder="레이블에 대한 설명을 입력하세요"
                onChange={() => console.log('onChange')}
                height={40}
              ></TextInput>
              <div
                css={{
                  display: 'flex',
                  gap: '24px',
                  width: 'fit-content',
                }}
              >
                <InputContainer height={40}>
                  <ColorCodeInput
                    value="어쩌고"
                    onChange={() => console.log('onChange')}
                    onRandomButtonClick={() =>
                      console.log('onRandomButtonClick')
                    }
                  ></ColorCodeInput>
                </InputContainer>

                <DropDownContainer
                  size="defaultSize"
                  indicator="텍스트 색상" // 패널이 닫히며 클릭한 색 항목으로 바뀌어야함
                  isPanelOpen={true}
                  onClick={() => console.log('onClick')}
                >
                  <DropDownPanel
                    panelHeader="색상 변경"
                    position="left"
                    onOutsideClick={() => console.log('panelClose')}
                  >
                    {textColors?.map((item) => (
                      <DropDownList
                        key={item.id}
                        item={item}
                        onClick={() => console.log('무슨무슨색onClick')}
                        isSelected={true}
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
