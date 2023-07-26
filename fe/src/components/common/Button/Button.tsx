import React from "react";
import ContainerButton from "./ContainerButton";
import GhostButton from "./GhostButton";
import OutlineButton from "./OutlineButton";

export type ButtonVariant = "container" | "outline" | "ghost";
export type ButtonSize = "S" | "M" | "L";

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant: ButtonVariant;
  size: ButtonSize;
}

export default function Button(props: ButtonProps) {
  const buttonComponent = {
    container: <ContainerButton {...props} />,
    outline: <OutlineButton {...props} />,
    ghost: <GhostButton {...props} />,
  };

  return buttonComponent[props.variant];
}
