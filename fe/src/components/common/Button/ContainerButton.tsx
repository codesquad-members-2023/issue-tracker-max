import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps } from "./Button";
import { SIZE } from "./constants";

export default function ContainerButton(props: ButtonProps) {
  return <StyledContainerButton {...props} />;
}

const StyledContainerButton = styled(BaseButton)<ButtonProps>`
  width: ${({ size }) => size && SIZE[size].width};
  height: ${({ size }) => size && SIZE[size].height};
  border-radius: ${({ theme: { radius }, size }) =>
    size === "L" ? radius.l : radius.m};

  background-color: ${({ theme: { brand } }) => brand.surface.default};
  color: ${({ theme: { brand } }) => brand.text.default};

  & > img {
    filter: ${({ theme: { filter } }) => filter.brandTextDefault};
  }
`;
