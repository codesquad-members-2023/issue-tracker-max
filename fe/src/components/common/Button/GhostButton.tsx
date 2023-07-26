import styled from "styled-components";
import BaseButton from "./BaseButton";
import { ButtonProps } from "./Button";

export default function GhostButton(props: ButtonProps) {
  return <StyledGhostButton {...props} />;
}

const StyledGhostButton = styled(BaseButton)<ButtonProps>`
  padding: 0 24px;
`;
