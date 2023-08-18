import { styled } from "styled-components";

type Props = {
  label: string;
  backgroundColor: string;
  textColor?: string;
};

export default function LabelItem({
  label,
  backgroundColor,
  textColor = "#ffffff",
}: Props) {
  return (
    <Wrapper $backgroundColor={backgroundColor}>
      <LabelTitle $textColor={textColor}>
        {label === "" ? "Label" : label}
      </LabelTitle>
    </Wrapper>
  );
}

const Wrapper = styled.div<{ $backgroundColor: string }>`
  display: flex;
  align-items: centers;
  height: 24px;
  min-width: 40px;
  background-color: ${({ $backgroundColor }) => $backgroundColor};
  border: ${({ $backgroundColor, theme }) =>
    $backgroundColor === "#FFFFFF"
      ? `${theme.border.default} ${theme.colorSystem.neutral.text.strong}`
      : "none"};
  border-radius: ${({ theme }) => theme.radius.medium};
`;

const LabelTitle = styled.span<{ $textColor: string }>`
  padding: 4px 12px;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ $textColor }) => $textColor};
`;
