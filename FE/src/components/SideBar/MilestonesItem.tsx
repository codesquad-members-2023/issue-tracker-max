import { styled } from "styled-components";
import ProgressBar from "../ProgressBar/ProgressBar";

type Props = {
  title: string;
  percentage: number;
};

export default function MilestonesItem({ title, percentage }: Props) {
  return (
    <Container>
      <ProgressBar percentage={percentage}></ProgressBar>
      <Title>{title}</Title>
    </Container>
  );
}

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const Title = styled.span`
  width: 100%;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
