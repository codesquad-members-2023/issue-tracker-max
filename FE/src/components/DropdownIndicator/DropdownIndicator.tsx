import { styled } from "styled-components";

type Props = {
  text: string;
  padding?: string;
  onClick(): void;
};

export default function DropdownIndicator({ text, padding, onClick }: Props) {
  return (
    <IndicatorButton text={text} padding={padding} onClick={onClick}>
      <IndicatorLabel>{text}</IndicatorLabel>
      <IndicatorIcon src={"/icons/chevronDown.svg"} />
    </IndicatorButton>
  );
}

const IndicatorButton = styled.button<Props>`
  display: flex;
  align-items: center;
  padding: ${({ padding }) => padding && padding};
  gap: 4px;
  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }
  &:active {
    opacity: ${({ theme }) => theme.opacity.press};
  }
  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
  }
`;

const IndicatorLabel = styled.span`
  width: 60px;
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  text-align: left;
`;

const IndicatorIcon = styled.img`
  filter: ${({ theme }) => theme.filter.neutral.text.default};
`;
