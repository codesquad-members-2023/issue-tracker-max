import { styled } from "styled-components";
import {
  CommonStyle,
  SizeStyles,
  TypeStyles,
  LargeGapStyle,
  IconSizeStyles,
  IconColorStyles,
} from "./ButtonStyle";

type Props = {
  icon?: string;
  label: string;
  type?: "container" | "outline" | "ghost";
  size?: "large" | "medium" | "small";
  width?: string;
  height?: string;
  onClick: () => void;
};

export default function Button({
  icon,
  label,
  type = "container",
  size = "small",
  width,
  height,
  onClick,
}: Props) {
  return (
    <StyledButton
      icon={icon}
      label={label}
      type={type}
      size={size}
      width={width}
      height={height}
      onClick={onClick}
    >
      <IconImg
        icon={icon}
        label={label}
        type={type}
        size={size}
        width={width}
        height={height}
        onClick={onClick}
        src={`/icons/${icon}.svg`}
      />
      <ButtonLabel>{label}</ButtonLabel>
    </StyledButton>
  );
}

const StyledButton = styled.button<Props>`
  ${CommonStyle}
  ${({ size }) => size && SizeStyles[size]}
  ${({ type }) => type && TypeStyles[type]}
  ${({ size, type }) => size !== "small" && type !== "ghost" && LargeGapStyle}
  ${({ width }) => width && `width: ${width};`}
  ${({ height }) => height && `height: ${height};`}
`;

const IconImg = styled.img<Props>`
  ${({ size }) => size && IconSizeStyles[size]}
  ${({ type }) => type && IconColorStyles[type]}
`;

const ButtonLabel = styled.span``;
