import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { FilterBar } from "../../components/FilterBar";
import { TabButton } from "../../components/TabButton";
import { IconType } from "../../components/icon/Icon";
import { SingleFilterData } from "./Main";

type MainHeaderProps = {
  singleFilters: SingleFilterData[];
  milestoneCount: number;
  labelCount: number;
  input: string;
};

type Tab = {
  name: string;
  icon: keyof IconType;
  selected: boolean;
  onClick: () => void;
};

export function MainHeader({
  singleFilters,
  milestoneCount,
  labelCount,
  input,
}: MainHeaderProps) {
  const navigate = useNavigate();

  const tabs: Tab[] = [
    {
      name: `label(${labelCount})`,
      icon: "Label",
      selected: false,
      onClick: () => navigate("/label"),
    },
    {
      name: `milestone(${milestoneCount})`,
      icon: "Milestone",
      selected: false,
      onClick: () => navigate("/milestone"),
    },
  ];

  const setDropdownOptions = (singleFilters: SingleFilterData[]) => {
    const options = singleFilters.map((filter) => {
      return {
        name: filter.name,
        profile: "",
        selected: filter.selected,
        onClick: () => {
          console.log(filter.name);
        },
      };
    });

    return options;
  };

  const onTabClick = (name: string) => {
    const matchingTab = tabs.find((tab) => tab.name === name);

    matchingTab && matchingTab.onClick();
  };

  return (
    <Div>
      <div>
        <FilterBar
          name="필터"
          optionTitle={`이슈 필터`}
          options={setDropdownOptions(singleFilters)}
          value={input}
        />
      </div>
      <div style={{ display: "flex", gap: "16px" }}>
        <TabButton>
          {tabs.map(({ name, icon, selected }, index) => (
            <Button
              key={`tab-${index}`}
              icon={icon}
              size="M"
              buttonType="Ghost"
              flexible="Flexible"
              selected={selected}
              onClick={() => onTabClick(name)}
            >
              {name}
            </Button>
          ))}
        </TabButton>
        <Button
          icon="Plus"
          size="S"
          buttonType="Container"
          selected
          onClick={() => navigate("/issues/new")}
        >
          이슈 작성
        </Button>
      </div>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
`;
