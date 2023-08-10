import React from "react";
import styled from "styled-components";

type ProgressBarProps = {
  openIssueCount: number;
  closedIssuesCount: number;
};

export const ProgressBar: React.FC<ProgressBarProps> = ({
  openIssueCount,
  closedIssuesCount,
}) => {
  const totalIssues = openIssueCount + closedIssuesCount;
  const closedPercentage =
    totalIssues !== 0 ? Math.floor((closedIssuesCount / totalIssues) * 100) : 0;

  return (
    <BarLayout>
      <BarBox>
        <ProgressBox percentage={closedPercentage} />
      </BarBox>
      <BarDetail>
        <p>{closedPercentage}%</p>
        <div>
          <span>열린 이슈 {openIssueCount}</span>
          <span>닫힌 이슈 {closedIssuesCount}</span>
        </div>
      </BarDetail>
    </BarLayout>
  );
};

const BarLayout = styled.div`
  width: 240px;
`;

const BarBox = styled.div`
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceBold};
  border-radius: 7px;
`;

const ProgressBox = styled.div<{ percentage: number }>`
  height: 8px;
  width: ${(props) => `${props.percentage}%`};
  border-radius: 10px;
  transition: width 0.3s ease-in-out;
  background-color: ${({ theme: { color } }) => color.paletteBlue};
`;

const BarDetail = styled.div`
  display: flex;
  margin-top: 8px;
  justify-content: space-between;
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  span {
    padding-left: 8px;
  }
`;
