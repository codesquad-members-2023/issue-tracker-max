import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps, ButtonSize, ButtonVariant } from "./Button";
import { SIZE } from "./constants";

export default function OutlineButton({
  size,
  variant,
  ...props
}: ButtonProps) {
  return <StyledOutlineButton $size={size} $variant={variant} {...props} />;
}

const StyledOutlineButton = styled(BaseButton)<{
  $size: ButtonSize;
  $variant: ButtonVariant;
}>`
  width: ${({ $size }) => SIZE[$size].width};
  height: ${({ $size }) => SIZE[$size].height};
  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.l : radius.m};

  border: ${({ theme: { border, brand } }) =>
    `${border.default} ${brand.border.default}`};
  color: ${({ theme: { brand } }) => brand.text.weak};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextWeak};
  }
`;
