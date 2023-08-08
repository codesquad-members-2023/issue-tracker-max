import { styled } from 'styled-components';

export default function ProgressIndicator({
  openCount,
  closeCount,
}: {
  openCount: number;
  closeCount: number;
}) {
  const calculatePercentage = (part: number, total: number) => {
    if (total === 0) {
      return 100;
    }
    return (part / total) * 100;
  };

  const percentage = calculatePercentage(closeCount, openCount + closeCount);
  return (
    <Container>
      <h3 className="blind">마일스톤</h3>
      <ProgressIndicatorBar value={percentage} max="100" />
      <br />
      <InfoText>
        <CompletionChart>
          <Description
            title="진행률"
            details={`${percentage.toFixed()}%`}
            hideTitle
          />
        </CompletionChart>
        <dt className="blind">이슈 갯수</dt>
        <dd>
          <Counters>
            <li>
              <dl>
                <Description title="열린 이슈" details={openCount} />
              </dl>
            </li>
            <li>
              <dl>
                <Description title="닫힌 이슈" details={closeCount} />
              </dl>
            </li>
          </Counters>
        </dd>
      </InfoText>
    </Container>
  );
}

function Description({
  title,
  details,
  hideTitle,
}: {
  title: string;
  details: string | number;
  hideTitle?: boolean;
}) {
  return (
    <>
      {hideTitle ? <dt className="blind">{title}</dt> : <dt>{title}</dt>}
      <dd>{details}</dd>
    </>
  );
}

const Container = styled.article`
  width: 244px;
  height: 32px;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[12]}
`;

const ProgressIndicatorBar = styled.progress`
  width: 100%;
  margin-bottom: 8px;
`;

const InfoText = styled.dl`
  display: flex;
  justify-content: space-between;
`;

const CompletionChart = styled.dl``;

const Counters = styled.ul`
  display: flex;
  gap: 8px;

  dl {
    display: inline-flex;
    gap: 4px;
  }
`;
