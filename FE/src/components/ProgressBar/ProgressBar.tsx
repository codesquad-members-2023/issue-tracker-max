import { styled } from "styled-components";

type Props = {
  percentage: number;
};

export default function ProgressBar({ percentage }: Props) {
  return (
    <Wrapper>
      <ProgressFilter $percentage={percentage}></ProgressFilter>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 100%;
  height: 8px;
  border-radius: 10px;
`;

const ProgressFilter = styled.div<{ $percentage: number }>`
  height: 100%;
  border-radius: inherit;
  background: ${({ theme, $percentage }) => `linear-gradient(
    to right,
    ${theme.colorSystem.brand.surface.default} 0%,
    ${theme.colorSystem.brand.surface.default} ${$percentage}%,
    ${theme.colorSystem.neutral.surface.bold} ${$percentage}%,
    ${theme.colorSystem.neutral.surface.bold} 100%
  )`};
`;
