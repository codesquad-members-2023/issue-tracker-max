import styled from "styled-components";

export default function ProgressBar({
  variant = "label",
  name,
  openCount,
  closeCount,
}: {
  variant?: "label" | "percent";
  name: string;
  openCount: number;
  closeCount: number;
}) {
  const percentage = Math.round((closeCount / (openCount + closeCount)) * 100);

  return (
    <Wrapper>
      <progress id={name} max={100} value={percentage} />
      {variant === "label" && <label htmlFor={name}>{name}</label>}
      {variant === "percent" && (
        <Info>
          <PercentText>{percentage}%</PercentText>
          <IssueCount>{`열린 이슈 ${openCount}`}</IssueCount>
          <IssueCount>{`닫힌 이슈 ${closeCount}`}</IssueCount>
        </Info>
      )}
    </Wrapper>
  );
}

const IssueCount = styled.span`
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;

const Info = styled.div`
  display: flex;
  justify-content: space-between;
`;

const PercentText = styled.span`
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 8px;

  progress {
    width: 100%;
    appearance: none;
    height: 10px;

    &::-webkit-progress-bar {
      border-radius: ${({ theme: { radius } }) => radius.l};
      background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
    }

    &::-webkit-progress-value {
      border-radius: ${({ theme: { radius } }) =>
        `${radius.l} 0 0 ${radius.l}`};
      background-color: ${({ theme: { brand } }) => brand.surface.default};
    }
  }

  label {
    font: ${({ theme: { font } }) => font.displayMD12};
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }
`;
