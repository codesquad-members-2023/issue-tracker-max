import { HTMLAttributes } from "react";

interface Props extends HTMLAttributes<HTMLSpanElement> {
  typography?: "bold32" | "bold24" | "bold20" | "bold16" | "bold12" | "medium20" | "medium16" | "medium12";
  color: string;
}
export function Txt({ typography = "medium16", color, ...props }: Props) {
  return (
    <span
      css={{
        margin: 0,
        padding: 0,

        color,
        ...TYPOGRAPHY_VARIANT[typography],
      }}
      {...props}
    />
  );
}

const TYPOGRAPHY_VARIANT = {
  bold32: {
    fontSize: 32,
    fontWeight: 700,
    lineHeight: 48,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  bold24: {
    fontSize: 24,
    fontWeight: 700,
    lineHeight: 36,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  bold20: {
    fontSize: 20,
    fontWeight: 700,
    lineHeight: 32,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  bold16: {
    fontSize: 16,
    fontWeight: 700,
    lineHeight: 24,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  bold12: {
    fontSize: 12,
    fontWeight: 700,
    lineHeight: 16,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  medium20: {
    fontSize: 20,
    fontWeight: 500,
    lineHeight: 32,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  medium16: {
    fontSize: 16,
    fontWeight: 500,
    lineHeight: 24,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  medium12: {
    fontSize: 12,
    fontWeight: 500,
    lineHeight: 16,
    letterSpacing: 0,
    textAlign: "left" as const,
  },
};
