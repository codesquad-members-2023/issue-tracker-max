import { styled } from "styled-components";

export function MilestoneElement({
  progress,
  children,
}: {
  progress: number;
  children: string;
}) {
  return (
    <Div>
      <ProgressBar value={progress} max="100"></ProgressBar>
      <Text>{children}</Text>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  gap: 8px;
`;

const ProgressBar = styled.progress`
  width: 100%;
  height: 16px;
`;

const Text = styled.span`
  width: 100%;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;
