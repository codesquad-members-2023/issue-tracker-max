import { styled } from "styled-components";

type Props = {
  type?: "open" | "close";
  icon?: string;
  label: string;
  size?: "L" | "S";
};

export default function InformationTag({
  type = "open",
  icon,
  label,
  size = "S",
}: Props) {
  return (
    <Wrapper $type={type} $size={size}>
      {icon && <TagImg src={`/icons/${icon}.svg`} />}
      <TagLabel>{label}</TagLabel>
    </Wrapper>
  );
}

const Wrapper = styled.div<{ $type: "open" | "close"; $size: "L" | "S" }>`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  padding: ${({ $size }) => ($size === "L" ? "8px 16px" : "4px 8px")};
  background-color: ${({ $type, theme }) =>
    $type === "open"
      ? theme.colorSystem.palette.blue
      : theme.colorSystem.neutral.text.weak};
  color: ${({ theme }) => theme.colorSystem.brand.text.default};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const TagImg = styled.img`
  filter: ${({ theme }) => theme.filter.brand.text.default};
`;

const TagLabel = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
`;
