import { styled } from 'styled-components';
import Label from './Label';
import TextInput from '../common/TextInput';
import ColorCodeInput from '../common/ColorCodeInput';
import DropdownIndicator from '../common/DropdownIndicator';
import Button from '../common/button/BaseButton';
import { useState } from 'react';
import FormType from '../../constant/FormType';
import LabelFormProps from '../../types/LabelFormProps';

export default function LabelForm<T extends FormType>(
  props: {
    type: T;
  } & LabelFormProps<T>
) {
  const [textColor, setTextColor] = useState(
    props.type === FormType.edit ? props.textColor : 'FFFFFF'
  );
  const [backgroundColor, setBackgroundColor] = useState(
    props.type === FormType.edit ? props.backgroundColor : 'FFFFFF'
  );
  const [name, setName] = useState(
    props.type === FormType.edit ? props.name : 'label'
  );

  function submitHandler(e: React.FormEvent) {
    e.preventDefault();
  }
  return (
    <Container onSubmit={submitHandler}>
      <Output>
        <Label {...{ textColor, backgroundColor, name }} />
      </Output>
      <Field>
        <TextInput
          id="name"
          sizeType="short"
          labelName="이름"
          helpText="레이블의 이름을 입력하세요"
        />
        <TextInput
          id="label-description"
          sizeType="short"
          labelName="설명(선택)"
          helpText="레이블의 설명을 입력하세요"
        />
        <ColorCodeInputField>
          <ColorCodeInput label={'배경 색상'} />
          <DropdownIndicator text="텍스트 색상" />
        </ColorCodeInputField>
        <Menu>
          <li>
            <Button
              type="button"
              iconName="xSquare"
              outline
              onClick={props.cancel}>
              취소
            </Button>
          </li>
          <li>
            <Button type="submit" iconName="plus">
              {props.type === FormType.edit && '편집 '}완료
            </Button>
          </li>
        </Menu>
      </Field>
    </Container>
  );
}

const Container = styled.form`
  display: flex;
  gap: 24px;
`;

const Output = styled.output`
  min-width: 288px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
`;

const Field = styled.fieldset`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: end;
  gap: 16px;
  & > fieldset {
    width: 100%;
  }
`;

const ColorCodeInputField = styled.fieldset`
  display: flex;
  align-items: center;
  gap: 24px;
`;

const Menu = styled.menu`
  display: flex;
  gap: 16px;
`;
