import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { FilterBar } from "../../components/FilterBar";
import { TabButton } from "../../components/TabButton";
import { Icon, IconType } from "../../components/icon/Icon";
import { SingleFilterData } from "./Main";

type MainHeaderProps = {
  singleFilters: SingleFilterData[];
  milestoneCount: number;
  labelCount: number;
  input: string;
  setSingleFilters: (value: string) => void;
  onChangeFilterInput: (event: React.ChangeEvent<HTMLInputElement>) => void;
  handleFilterInput: () => void;
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
  setSingleFilters,
  onChangeFilterInput,
  handleFilterInput,
}: MainHeaderProps) {
  const navigate = useNavigate();
  const queryString = window.location.search;

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
    const options = singleFilters.map((filter, index) => {
      return {
        id: index + 1,
        name: filter.name,
        profile: "",
        selected: filter.selected,
        onClick: () => {
          setSingleFilters(filter.conditions);
        },
      };
    });

    return options;
  };

  const onTabClick = (name: string) => {
    const matchingTab = tabs.find((tab) => tab.name === name);

    matchingTab && matchingTab.onClick();
  };

  const handleEnterKeyPress = (
    event: React.KeyboardEvent<HTMLInputElement>,
  ) => {
    if (event.key === "Enter") {
      handleFilterInput();
    }
  };

  const navigateToNoneFilter = () => {
    navigate("/");
  };

  return (
    <Div>
      <HeaderWrapper>
        <div>
          <FilterBar
            name="필터"
            optionTitle={`이슈 필터`}
            options={setDropdownOptions(singleFilters)}
            onChange={onChangeFilterInput}
            value={input}
            onBlur={handleFilterInput}
            onKeyDown={handleEnterKeyPress}
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
      </HeaderWrapper>
      {queryString && (
        <ClearFilterDiv>
          <a onClick={navigateToNoneFilter}>
            <Icon name="XSquare" />
            <span>현재의 검색 필터 및 정렬 지우기</span>
          </a>
        </ClearFilterDiv>
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding-bottom: 24px;
`;

const HeaderWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const ClearFilterDiv = styled.div`
  width: 100%;
  height: 32px;
  display: flex;
  align-items: center;
  gap: 4px;
  padding-top: 24px;

  & a {
    display: flex;
    cursor: pointer;
  }

  & span {
    color: ${({ theme }) => theme.color.neutralTextDefault};
    font: ${({ theme }) => theme.font.displayMedium12};
  }
`;
