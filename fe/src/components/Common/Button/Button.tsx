// import { ReactNode } from 'react';
import styled, { css } from "styled-components";

const CommonStyledButton = styled.button`
  padding: 12px 0;
  justify-content: center;
  align-items: center;
  display: flex;
  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    pointer-events: none;
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  transition: opacity 0.3s ease;
`;

export type ButtonVariant = "contained" | "outline" | "ghost";
export type ButtonSize = "S" | "M" | "L";
export type ghostFontColor = "default" | "selected" | "danger";

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant: ButtonVariant;
  size: ButtonSize;
  states?: ghostFontColor;
}

export const Button = ({
  variant,
  size,
  states = "default",
  ...props
}: ButtonProps) => {
  const buttonComponent = {
    contained: <ContainedButton $size={size} {...props} />,
    outline: <OutlineButton $size={size} {...props} />,
    ghost: <GhostButton $size={size} $states={states} {...props} />,
  };

  return buttonComponent[variant];
};

const STYLES = {
  L: css`
    font: ${({ theme: { font } }) => font.availableM20};
    gap: 8px;
    width: 240px;
    height: 56px;
  `,
  M: css`
    font: ${({ theme: { font } }) => font.availableM16};
    gap: 8px;
    width: 184px;
    height: 48px;
  `,
  S: css`
    font: ${({ theme: { font } }) => font.availableM12};
    gap: 4px;
    width: 128px;
    height: 40px;
  `,
};

const ContainedButton = styled(CommonStyledButton)<{
  $size: ButtonSize;
}>`
  ${({ $size }) => STYLES[$size]}

  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.large : radius.medium};

  color: ${({ theme: { color } }) => color.brandTextDefault};
  background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
`;

const OutlineButton = styled(CommonStyledButton)<{
  $size: ButtonSize;
}>`
  ${({ $size }) => STYLES[$size]}

  border-radius: ${({ theme: { radius }, $size }) =>
    $size === "L" ? radius.large : radius.medium};

  border: ${({ theme: { border } }) => border.default};
  color: ${({ theme: { color } }) => color.brandTextWeak};
`;

const GhostButton = styled(CommonStyledButton)<{
  $size: ButtonSize;
  $states: ghostFontColor;
}>`
  ${({ $size }) => STYLES[$size]}
  width: unset;
  gap: 4px;
  ${({ theme: { font }, $states }) =>
    $states === "selected" && `font: ${font.selectedB16}`};
  color: ${({ theme: { color }, $states }) => {
    switch ($states) {
      case "selected":
        return color.nuetralTextStrong;
      case "danger":
        return color.dangerTextDefault;
      default:
        return color.nuetralTextDefault;
    }
  }};
`;
