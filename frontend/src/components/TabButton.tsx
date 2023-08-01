import { Children, ReactElement, cloneElement } from "react";
import { styled } from "styled-components";

export function TabButton({
  type = "Outline",
  children,
  onClick,
}: {
  type?: "Ghost" | "Outline";
  children: ReactElement[];
  onClick: (name: string) => void;
}) {
  const TabButtonDiv =
    type === "Ghost" ? GhostTabButtonDiv : OutlineTabButtonDiv;

  return (
    <TabButtonDiv>
      {Children.map(children, (child) =>
        cloneElement(child, {
          onClick: () => {
            onClick(child.props.children);
          },
        }),
      )}
    </TabButtonDiv>
  );
}

const BaseTabButtonDiv = styled.div`
  display: flex;
  align-items: center;
  width: fit-content;
  height: 40px;

  & button {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
  }
`;

const GhostTabButtonDiv = styled(BaseTabButtonDiv)`
  border: none;
  border-radius: 0;
  background-color: transparent;

  & button {
    padding: 0 4px;
  }
`;

const OutlineTabButtonDiv = styled(BaseTabButtonDiv)`
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.color.neutralBorderDefault}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.color.neutralSurfaceDefault};

  & button {
    &:not(:last-child) {
      border-right: ${({ theme }) =>
        `${theme.border.default} ${theme.color.neutralBorderDefault}`};
      border-radius: 0;
    }

    &:first-child {
      border-radius: ${({ theme }) =>
        `${theme.radius.medium} 0 0 ${theme.radius.medium}`};
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
