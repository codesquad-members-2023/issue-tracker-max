import { useState } from "react";
import { styled } from "styled-components";

import { useNavigate } from "react-router-dom";
import { Button } from "../../components/Button";
import { FilterBar } from "../../components/FilterBar";
import { TabButton } from "../../components/TabButton";
import { SingleFilterData } from "./Main";

type MainHeaderProps = {
  singleFilters: SingleFilterData[];
  milestoneCount: number;
  labelCount: number;
};

export function MainHeader({
  singleFilters,
  milestoneCount,
  labelCount,
}: MainHeaderProps) {
  const [tabs, setTabs] = useState([
    { name: `label(${labelCount})`, icon: "label", selected: false },
    {
      name: `milestone(${milestoneCount})`,
      icon: "milestone",
      selected: false,
    },
  ]);
  const navigate = useNavigate();

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
    setTabs((t) =>
      t.map((tab) =>
        tab.name === name
          ? { ...tab, selected: true }
          : { ...tab, selected: false },
      ),
    );
  };

  return (
    <Div>
      <div>
        <FilterBar
          name="필터"
          optionTitle={`이슈 필터`}
          options={setDropdownOptions(singleFilters)}
          value="is:issue is:open"
        />
      </div>
      <div style={{ display: "flex", gap: "16px" }}>
        <TabButton onClick={onTabClick}>
          {tabs.map(({ name, icon, selected }, index) => (
            <Button
              key={`tab-${index}`}
              icon={icon}
              size="M"
              buttonType="Ghost"
              flexible="Flexible"
              selected={selected}
            >
              {name}
            </Button>
          ))}
        </TabButton>
        <Button
          icon="plus"
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
