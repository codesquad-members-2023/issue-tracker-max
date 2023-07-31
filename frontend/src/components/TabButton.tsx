import { styled } from "styled-components";
import { Button } from "./Button";

export function TabButton({
  tabs,
  onClick,
}: {
  tabs: {
    name: string;
    icon?: string;
    selected?: boolean;
  }[];
  onClick: (name: string) => void;
}) {
  return (
    <StyledTabButton>
      {tabs.map(({ name, icon, selected }) => (
        <Button
          key={name}
          icon={icon}
          size="M"
          buttonType="Ghost"
          flexible="Flexible"
          selected={selected}
          onClick={() => onClick(name)}
        >
          {name}
        </Button>
      ))}
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
      border-right: ${({ theme }) =>
        `${theme.border.default} ${theme.color.neutralBorderDefault}`};
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
