import { Children, ReactElement, cloneElement } from "react";
import { styled } from "styled-components";

export function TabButton({
  children,
  onClick,
}: {
  children: ReactElement[];
  onClick: (name: string) => void;
}) {
  return (
    <StyledTabButton>
      {Children.map(children, (child) =>
        cloneElement(child, {
          onClick: () => {
            onClick(child.props.children);
          },
        }),
      )}
    </StyledTabButton>
  );
}

const StyledTabButton = styled.div`
  display: flex;
  align-items: center;
  width: fit-content;
  height: 40px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.color.neutralSurfaceDefault};

  & button {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;

    &:first-child {
      border-radius: ${({ theme }) =>
        `${theme.radius.medium} 0 0 ${theme.radius.medium}`};
    }

    &:not(:last-child) {
      border-right: ${({ theme }) =>
        `${theme.border.default} ${theme.color.neutralBorderDefault}`};
      border-radius: 0;
    }

    &:last-child {
      border-radius: ${({ theme }) =>
        `0 ${theme.radius.medium} ${theme.radius.medium} 0`};
    }

    &.selected {
      background-color: ${({ theme }) => theme.color.neutralSurfaceBold};
    }
  }
`;
