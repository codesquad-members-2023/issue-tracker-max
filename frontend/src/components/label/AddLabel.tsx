import { styled } from 'styled-components';
import LabelForm from './LabelForm';
import FormType from '../../constant/FormType';

export default function AddLabel({ cancel }: { cancel: () => void }) {
  return (
    <Container>
      <Title>새로운 레이블 추가</Title>
      <LabelForm type={FormType.add} cancel={cancel} />
    </Container>
  );
}

const Container = styled.article`
  padding: 32px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  background-color: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const Title = styled.h3`
  ${({ theme }) => theme.font.display.bold[20]}
  color: ${({ theme }) => theme.color.neutral.text.strong};
`;
