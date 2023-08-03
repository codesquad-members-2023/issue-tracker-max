import { styled } from "styled-components";

type Props = {
  icon?: string;
  label: string;
  padding?: string;
  width?: string;
  height?: string;
  onClick(): void;
};

export default function DropdownIndicator({
  icon = "chevronDown",
  label,
  padding = "4px 0px",
  width = "80px",
  height = "32px",
  onClick,
}: Props) {
  return (
    <IndicatorButton
      $padding={padding}
      $width={width}
      $height={height}
      onClick={onClick}
    >
      <IndicatorLabel>{label}</IndicatorLabel>
      <IndicatorIcon src={`/icons/${icon}.svg`} />
    </IndicatorButton>
  );
}

const IndicatorButton = styled.button<{
  $padding: string;
  $width: string;
  $height: string;
}>`
  width: ${({ $width }) => $width};
  height: ${({ $height }) => $height};
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: ${({ $padding }) => $padding && $padding};
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
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  text-align: left;
`;

const IndicatorIcon = styled.img`
  width: 16px;
  height: 16px;
  filter: ${({ theme }) => theme.filter.neutral.text.default};
`;
