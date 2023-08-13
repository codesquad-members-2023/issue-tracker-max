import { styled } from "styled-components";

type Props = {
  label: string;
  color: string;
};

export default function LabelItem({ label, color }: Props) {
  return (
    <Wrapper $color={color}>
      <LabelTitle>{label === "" ? "Label" : label}</LabelTitle>
    </Wrapper>
  );
}

const Wrapper = styled.div<{ $color: string }>`
  display: flex;
  justify-content: center;
  align-items: centers;
  height: 24px;
  min-width: 40px;
  background-color: ${({ $color }) => $color};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.text.strong}`};
  border-radius: ${({ theme }) => theme.radius.medium};
`;

const LabelTitle = styled.span`
  padding: 4px 12px;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
