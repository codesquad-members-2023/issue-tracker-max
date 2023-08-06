import { styled } from "styled-components";
import ProgressBar from "../ProgressBar/ProgressBar";

type Props = {
  percentage: number;
  openIssues: number;
  closedIssues: number;
};

export default function ProgressIndicator({
  percentage,
  openIssues,
  closedIssues,
}: Props) {
  return (
    <Container>
      <ProgressBar percentage={percentage} />
      <Info>
        <ProgressInfo>{percentage}%</ProgressInfo>
        <IssueInfo>
          <OpenIssue>열린 이슈 {openIssues}</OpenIssue>
          <ClosedIssue>닫힌 이슈 {closedIssues}</ClosedIssue>
        </IssueInfo>
      </Info>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
`;

const Info = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const IssueInfo = styled.div`
  display: flex;
  gap: 8px;
`;

const ProgressInfo = styled.span``;

const OpenIssue = styled.span``;

const ClosedIssue = styled.span``;
