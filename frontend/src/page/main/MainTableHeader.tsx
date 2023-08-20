import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TabButton } from "../../components/TabButton";
import { DropdownContainer } from "../../components/dropdown/DropdownContainer";
import { IconType } from "../../components/icon/Icon";
import { MultiFilters, Option } from "./Main";

type IssueState = {
  name: string;
  icon: keyof IconType;
  selected: boolean;
  conditions: string;
};

type TableHeaderProps = {
  openedIssueCount: number;
  closedIssueCount: number;
  multiFilters: MultiFilters;
  filterString: string;
  checkedIssueId: number[];
  totalIssueCount: number;
  handleHeaderCheckbox: (value: boolean) => void;
  setMultiFilterString: (value: string, multipleSelect: boolean) => void;
  onChangeIssuesState: (value: "OPENED" | "CLOSED") => void;
};

export function MainTableHeader({
  openedIssueCount,
  closedIssueCount,
  multiFilters,
  filterString,
  checkedIssueId,
  totalIssueCount,
  handleHeaderCheckbox,
  setMultiFilterString,
  onChangeIssuesState,
}: TableHeaderProps) {
  const issueStates: IssueState[] = [
    {
      name: `열린 이슈${openedIssueCount}`,
      icon: "AlertCircle",
      selected: filterString.includes("is:opened"),
      conditions: "is:opened",
    },
    {
      name: `닫힌 이슈${closedIssueCount}`,
      icon: "Archive",
      selected: filterString.includes("is:closed"),
      conditions: "is:closed",
    },
  ];

  const issueStateDropdownOptions = [
    {
      id: 1,
      name: "선택한 이슈 열기",
      selected: false,
      onClick: () => onChangeIssuesState("OPENED"),
    },
    {
      id: 2,
      name: "선택한 이슈 닫기",
      selected: false,
      onClick: () => onChangeIssuesState("CLOSED"),
    },
  ];

  const onIssueStateClick = (conditions: string) => {
    setMultiFilterString(conditions, false);
  };

  const addOnClickToOptions = (
    key: string,
    options: Option[],
    multipleSelect: boolean,
  ) => {
    return options.map((option) => ({
      ...option,
      onClick: () => {
        setMultiFilterString(
          `${key}:${option.id === 0 ? "none" : option.name}`,
          option.id === 0 ? false : multipleSelect,
        );
      },
    }));
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    handleHeaderCheckbox(event.target.checked);
  };

  return (
    <Div>
      <CheckboxLabel>
        <input
          type="checkbox"
          onChange={handleCheckboxChange}
          checked={
            checkedIssueId.length !== 0 &&
            checkedIssueId.length === totalIssueCount
          }
        />
      </CheckboxLabel>
      {!checkedIssueId.length ? (
        <>
          <TabButton type="Ghost">
            {issueStates.map(({ name, icon, selected, conditions }, index) => (
              <Button
                key={`tab-${index}`}
                icon={icon}
                size="M"
                buttonType="Ghost"
                flexible="Flexible"
                selected={selected}
                onClick={() => onIssueStateClick(conditions)}
              >
                {name}
              </Button>
            ))}
          </TabButton>
          <MultiFiltersDiv>
            {Object.entries(multiFilters).map(([key, value], index) => {
              const iconType =
                "avatarUrl" in value.options[0]
                  ? "Profile"
                  : "background" in value.options[0]
                  ? "Palette"
                  : "None";

              return (
                <DropdownContainer
                  key={index}
                  name={key}
                  iconType={iconType}
                  optionTitle={`${key} 필터`}
                  options={addOnClickToOptions(
                    key,
                    value.options,
                    value.multipleSelect,
                  )}
                  alignment="Right"
                  autoClose
                />
              );
            })}
          </MultiFiltersDiv>
        </>
      ) : (
        <>
          <SelectedItemCounter>
            {checkedIssueId.length}개 이슈 선택
          </SelectedItemCounter>
          <DropdownDiv>
            <DropdownContainer
              name="상태 수정"
              optionTitle="상태 변경"
              options={issueStateDropdownOptions}
              alignment="Right"
              autoClose
            />
          </DropdownDiv>
        </>
      )}
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

const SelectedItemCounter = styled.span`
  padding: 0 4px;
  color: ${({ theme }) => theme.color.neutralTextDefault};
  font: ${({ theme }) => theme.font.displayBold16};
`;

const DropdownDiv = styled.div`
  display: flex;
  flex: 1;
  justify-content: right;
  gap: 32px;
  padding-right: 32px;
`;
