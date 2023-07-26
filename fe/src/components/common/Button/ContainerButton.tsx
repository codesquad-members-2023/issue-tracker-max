import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps, ButtonSize, ButtonVariant } from "./Button";
import { SIZE } from "./constants";

export default function ContainerButton({
  size,
  variant,
  ...props
}: ButtonProps) {
  return <StyledContainerButton $size={size} $variant={variant} {...props} />;
}

const StyledContainerButton = styled(BaseButton)<{
  $size: ButtonSize;
  $variant: ButtonVariant;
}>`
  width: ${({ $size }) => SIZE[$size].width};
  height: ${({ $size }) => SIZE[$size].height};
  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.l : radius.m};

  background-color: ${({ theme: { brand } }) => brand.surface.default};
  color: ${({ theme: { brand } }) => brand.text.default};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextDefault};
  }
`;
