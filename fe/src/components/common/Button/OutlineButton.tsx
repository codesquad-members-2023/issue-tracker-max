import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps } from "./Button";
import { SIZE } from "./constants";

export default function OutlineButton(props: ButtonProps) {
  return <StyledOutlineButton {...props} />;
}

const StyledOutlineButton = styled(BaseButton)<ButtonProps>`
  width: ${({ size }) => size && SIZE[size].width};
  height: ${({ size }) => size && SIZE[size].height};
  border-radius: ${({ theme: { radius }, size }) =>
    size === "L" ? radius.l : radius.m};

  border: ${({ theme: { border, brand } }) =>
    `${border.default} ${brand.border.default}`};
  color: ${({ theme: { brand } }) => brand.text.weak};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextWeak};
  }
`;
