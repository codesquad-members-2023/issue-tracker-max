import { styled } from 'styled-components';
import MilestoneForm from './MilestoneForm';
import FormType from '../../constant/FormType';

export default function AddMilestone({ cancel }: { cancel: () => void }) {
  return (
    <Container>
      <Title>새로운 마일스톤 추가</Title>
      <MilestoneForm type={FormType.add} {...{ cancel }} />
    </Container>
  );
}

const Container = styled.article`
  padding: 32px;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  background-color: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const Title = styled.h3`
  ${({ theme }) => theme.font.display.bold[20]}
  color: ${({ theme }) => theme.color.neutral.text.strong};
`;
