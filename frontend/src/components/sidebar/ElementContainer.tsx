import { ReactNode } from "react";
import { styled } from "styled-components";

type Direction = "Horizontal" | "Vertical";

export function ElementContainer({
  direction,
  children,
}: {
  direction: Direction;
  children: ReactNode;
}) {
  return <Div $direction={direction}>{children}</Div>;
}

const Div = styled.div<{ $direction: Direction }>`
  width: 100%;
  display: flex;
  flex-direction: ${({ $direction }) =>
    $direction === "Horizontal" ? "row" : "column"};
  gap: ${({ $direction }) => ($direction === "Horizontal" ? "4px" : "16px")};
  flex-wrap: ${({ $direction }) => ($direction === "Horizontal" ? "wrap" : "")};
`;
