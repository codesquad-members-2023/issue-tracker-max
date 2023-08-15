import { useState } from "react";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TabButton } from "../../components/TabButton";
import { DropdownContainer } from "../../components/dropdown/DropdownContainer";
import { IconType } from "../../components/icon/Icon";
import { MultiFilters, Option } from "./Main";

type IssueState = {
  name: string;
  icon: keyof IconType;
  selected?: boolean;
};

type TableHeaderProps = {
  openedIssueCount: number;
  closedIssueCount: number;
  multiFilters: MultiFilters;
  setMultiFilterString: (value: string, multipleSelect: boolean) => void;
};

export function MainTableHeader({
  openedIssueCount,
  closedIssueCount,
  multiFilters,
  setMultiFilterString,
}: TableHeaderProps) {
  const [issueStates, setIssueStates] = useState<IssueState[]>([
    {
      name: `열린 이슈${openedIssueCount}`,
      icon: "AlertCircle",
      selected: true,
    },
    { name: `닫힌 이슈${closedIssueCount}`, icon: "Archive" },
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

  const addOnClickToOptions = (
    key: string,
    options: Option[],
    multipleSelect: boolean,
  ) => {
    return options.map((option) => ({
      ...option,
      onClick: () => {
        setMultiFilterString(`${key}:${option.name}`, multipleSelect);
      },
    }));
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
            options={addOnClickToOptions(
              key,
              value.options,
              value.multipleSelect,
            )}
            alignment="Right"
            autoClose
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
