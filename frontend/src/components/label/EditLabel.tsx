import { styled } from 'styled-components';
import LabelForm from './LabelForm';
import FormType from '../../constant/FormType';
import LabelProps from '../../types/LabelProps';

export default function EditLabel(props: LabelProps & { cancel: () => void }) {
  return (
    <Container>
      <Title>레이블 편집</Title>
      <LabelForm type={FormType.edit} {...props} />
    </Container>
  );
}

const Container = styled.article`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.h3`
  ${({ theme }) => theme.font.display.bold[20]}
  color: ${({ theme }) => theme.color.neutral.text.strong};
`;
