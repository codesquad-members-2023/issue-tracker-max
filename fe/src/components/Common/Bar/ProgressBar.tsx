import React from "react";
import styled from "styled-components";

type ProgressBarProps = {
  openIssues: number;
  closedIssues: number;
};

export const ProgressBar: React.FC<ProgressBarProps> = ({
  openIssues,
  closedIssues,
}) => {
  const totalIssues = openIssues + closedIssues;
  const closedPercentage =
    totalIssues !== 0 ? Math.floor((closedIssues / totalIssues) * 100) : 0;

  return (
    <BarLayout>
      <BarBox>
        <ProgressBox percentage={closedPercentage} />
      </BarBox>
      <BarDetail>
        <p>{closedPercentage}%</p>
        <div>
          <span>열린 이슈 {openIssues}</span>
          <span>닫힌 이슈 {closedIssues}</span>
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
  justify-content: space-between;
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  span {
    padding-left: 8px;
  }
`;
