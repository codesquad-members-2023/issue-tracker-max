import { ReactNode } from "react";
import { styled } from "styled-components";

export function ContentBox({
  header,
  body,
}: {
  header: ReactNode;
  body: ReactNode;
}) {
  return (
    <Div>
      <Header>{header}</Header>
      <Body>{body}</Body>
    </Div>
  );
}

const Div = styled.div`
  align-self: stretch;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 2px;
  border-radius: ${({ theme }) => theme.radius.large};
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  background-color: ${({ theme }) => theme.color.neutralBorderDefault};
`;

const Header = styled.div`
  align-self: stretch;
  display: flex;
  padding: 16px 24px;
  justify-content: space-between;
  align-items: center;
  border-radius: ${({ theme }) =>
    `${theme.radius.large} ${theme.radius.large} 0px 0px`};
  background-color: ${({ theme }) => theme.color.neutralSurfaceDefault};
`;

const Body = styled.div`
  align-self: stretch;
  display: flex;
  align-items: flex-start;
  gap: 10px;
  border-radius: ${({ theme }) =>
    `0px 0px ${theme.radius.large} ${theme.radius.large}`};
  background-color: ${({ theme }) => theme.color.neutralSurfaceStrong};
`;
