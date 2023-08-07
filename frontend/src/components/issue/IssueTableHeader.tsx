import { useState } from "react";
import { styled } from "styled-components";
import { Button } from "../Button";
import { TabButton } from "../TabButton";
import { DropdownContainer } from "../dropdown/DropdownContainer";
import { IconType } from "../icon/Icon";

type TableHeaderProps = {
  openedIssueCount: number;
  closedIssueCount: number;
  multiFilters: object;
};

export function IssueTableHeader({
  openedIssueCount,
  closedIssueCount,
  multiFilters,
}: TableHeaderProps) {
  const [issueStates, setIssueStates] = useState([
    {
      name: `열린 이슈${openedIssueCount}`,
      icon: "AlertCircle" as keyof IconType,
      selected: true,
    },
    { name: `닫힌 이슈${closedIssueCount}`, icon: "Archive" as keyof IconType },
  ]);

  const onIssueStateClick = (name: string) => {
    setIssueStates((t) =>
      t.map((issueState) =>
        issueState.name === name
          ? { ...issueState, selected: true }
          : { ...issueState, selected: false },
      ),
    );
  };
  return (
    <Div>
      <CheckboxLabel>
        <input type="checkbox" />
      </CheckboxLabel>
      <TabButton type="Ghost">
        {issueStates.map(({ name, icon, selected }, index) => (
          <Button
            key={`tab-${index}`}
            icon={icon}
            size="M"
            buttonType="Ghost"
            flexible="Flexible"
            selected={selected}
            onClick={() => onIssueStateClick(name)}
          >
            {name}
          </Button>
        ))}
      </TabButton>
      <MultiFiltersDiv>
        {Object.entries(multiFilters).map(([key, value], index) => (
          <DropdownContainer
            key={index}
            name={key}
            optionTitle={`${key} 필터`}
            options={value}
            alignment="Right"
          />
        ))}
      </MultiFiltersDiv>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  height: 64px;
  display: flex;
  align-items: center;
  background: ${({ theme }) => theme.color.neutralSurfaceDefault};
  border-top-right-radius: ${({ theme }) => theme.radius.large};
  border-top-left-radius: ${({ theme }) => theme.radius.large};
`;

const CheckboxLabel = styled.label`
  display: flex;
  justify-content: center;
  text-align: center;
  width: 48px;
  height: 100%;
  padding: 8px;
  box-sizing: border-box;
`;

const MultiFiltersDiv = styled.div`
  display: flex;
  flex: 1;
  justify-content: right;
  gap: 32px;
  padding-right: 64px;
`;
