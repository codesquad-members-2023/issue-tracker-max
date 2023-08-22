import { styled } from 'styled-components';
import TextInput from '../common/TextInput';
import Button from '../common/button/BaseButton';
import FormType from '../../constant/FormType';
import MilestoneFormProps from '../../types/MilestoneFormProps';

export default function MilestoneForm<T extends FormType>(
  props: {
    type: T;
  } & MilestoneFormProps<T>
) {
  return (
    <Container>
      <BaseInfo>
        <TextInput
          sizeType="short"
          labelName="이름"
          helpText="마일스톤 이름을 입력하세요"
        />
        <TextInput
          sizeType="short"
          labelName="완료일(선택)"
          helpText="YYYY.MM.DD"
        />
      </BaseInfo>

      <TextInput
        sizeType="short"
        labelName="설명(선택)"
        helpText="마일스톤에 대한 설명을 입력하세요"
      />
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
    </Container>
  );
}

const Container = styled.form`
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: end;
  & > fieldset {
    width: 100%;
  }
`;

const BaseInfo = styled.div`
  display: flex;
  width: 100%;
  gap: 16px;
  & > fieldset {
    width: 100%;
  }
`;

const Menu = styled.menu`
  display: flex;
  gap: 16px;
`;
