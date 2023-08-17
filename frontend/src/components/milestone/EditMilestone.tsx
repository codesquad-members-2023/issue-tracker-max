import { styled } from 'styled-components';
import MilestoneForm from './MilestoneForm';
import FormType from '../../constant/FormType';
import MilestoneProps from '../../types/MilestoneProps';

export default function EditMilestone(
  props: MilestoneProps & { cancel: () => void }
) {
  return (
    <Container>
      <Title>마일스톤 편집</Title>
      <MilestoneForm type={FormType.edit} {...props} />
    </Container>
  );
}

const Container = styled.article`
  padding: 32px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Title = styled.h3`
  ${({ theme }) => theme.font.display.bold[20]}
  color: ${({ theme }) => theme.color.neutral.text.strong};
`;
