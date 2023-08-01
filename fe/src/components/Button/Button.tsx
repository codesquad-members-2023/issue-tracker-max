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
  `,
  M: css`
    font: ${({ theme: { font } }) => font.availableM16};
    gap: 8px;
    width: 184px;
  `,
  S: css`
    font: ${({ theme: { font } }) => font.availableM12};
    gap: 4px;
    width: 128px;
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

////////////////////////////////
// type ButtonProps<T extends ElementType> = {
//   as?: T;
//   disabled?: boolean;
//   children?: ReactNode;
//   buttonType: 'contained' | 'outline' | 'ghost';
//   states?: 'selected' | 'danger';
//   size: 'L' | 'M' | 'S';
// } & ComponentPropsWithoutRef<T>;

// export const Button = <T extends ElementType = 'button'>({
//   as,
//   children,
//   ...props
// }: ButtonProps<T>): JSX.Element => {
//   return (
//     <StyledButton as={as} {...props}>
//       {children}
//     </StyledButton>
//   );
// };

// interface StyledButtonProps {
//   disabled?: boolean;
//   as?: React.ElementType;
//   buttonType: 'contained' | 'outline' | 'ghost';
//   size: 'L' | 'M' | 'S';
//   states?: 'danger' | 'selected';
// }

// const statesStyle = {
//   selected: css`
//     color: ${({ theme: { color } }) => color.nuetralTextStrong};
//   `,
//   danger: css`
//     color: ${({ theme: { color } }) => color.dangerTextDefault};
//   `,
// };

// const buttonStyleMap = {
//   containedL: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     gap: 8px;
//     align-items: center;
//     width: 240px;
//     color: ${({ theme: { color } }) => color.brandTextDefault};
//     background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM20};
//   `,
//   containedM: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     width: 184px;
//     gap: 8px;
//     color: ${({ theme: { color } }) => color.brandTextDefault};
//     background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM16};
//   `,
//   containedS: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     width: 128px;
//     gap: 4px;
//     color: ${({ theme: { color } }) => color.brandTextDefault};
//     background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM16};
//   `,
//   outlineL: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     width: 240px;
//     gap: 8px;
//     color: ${({ theme: { color } }) => color.brandTextWeak};
//     border: ${({ theme: { border } }) => border.default};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM20};
//   `,
//   outlineM: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     width: 184px;
//     gap: 8px;
//     color: ${({ theme: { color } }) => color.brandTextWeak};
//     border: ${({ theme: { border } }) => border.default};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM16};
//   `,
//   outlineS: css`
//     padding: 12px 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     width: 128px;
//     gap: 4px;
//     color: ${({ theme: { color } }) => color.brandTextWeak};
//     border: ${({ theme: { border } }) => border.default};
//     border-radius: ${({ theme: { radius } }) => radius.medium};
//     font: ${({ theme: { font } }) => font.availableM12};
//   `,
//   ghostL: css`
//     display: flex;
//     align-items: center;

//     gap: 4px;
//     align-items: center;
//     padding: 12px 0;
//     font: ${({ theme: { font } }) => font.availableM20};
//     color: ${({ theme: { color } }) => color.nuetralTextDefault};
//   `,
//   ghostM: css`
//     display: flex;
//     align-items: center;
//     padding: 12px 0;
//     gap: 4px;
//     font: ${({ theme: { font } }) => font.availableM16};
//     color: ${({ theme: { color } }) => color.nuetralTextDefault};
//   `,
//   ghostS: css`
//     display: flex;
//     align-items: center;
//     padding: 12px 0;
//     gap: 4px;
//     font: ${({ theme: { font } }) => font.availableM12};
//     color: ${({ theme: { color } }) => color.nuetralTextDefault};
//   `,
// };

// const StyledButton = styled.button.attrs<StyledButtonProps>(
//   ({ buttonType, size, ...rest }) => rest,
// )<StyledButtonProps>`
//   ${(props) => props.as === 'a' && 'cursor: pointer;'}
//   ${(props) =>
//     props.buttonType &&
//     props.size &&
//     buttonStyleMap[`${props.buttonType}${props.size}`]}
//   ${(props) => props.states && statesStyle[props.states]}
//   ${(props) => props.disabled && 'pointer-events: none;'}

//   &:hover {
//     opacity: ${({ theme: { opacity } }) => opacity.hover};
//   }

//   &:active {
//     opacity: ${({ theme: { opacity } }) => opacity.press};
//   }

//   &:disabled {
//     cursor: not-allowed;
//     opacity: ${({ theme: { opacity } }) => opacity.disabled};
//   }

//   transition: opacity 0.3s ease;
// `;

/* ${(props) => props.as === 'a' && 'display: inline-block;'}
  ${(props) => props.buttonType && typeStyles[props.buttonType]}
  ${(props) => props.size && commonSizeStyles[props.size]}
  ${(props) =>
    props.as === 'a' && props.buttonType === 'ghost' && 'width: auto;'}
  ${(props) =>
    props.buttonType !== 'ghost' && props.size && sizeWidthStyles[props.size]} */
