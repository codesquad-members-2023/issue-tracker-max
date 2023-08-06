import {styled} from "styled-components";
import {Button} from "../../components/Button";
import {TabButton} from "../../components/TabButton";

type LabelHeaderProps = {
  onClick: () => void;
  isAdding: boolean;
  openedMilestoneCount: Number;
  labelCount: Number;
};

export function LabelHeader({
                              onClick,
                              isAdding,
                              openedMilestoneCount,
                              labelCount,
                            }: LabelHeaderProps) {
  const tabs = [
    {name: `label(${labelCount})`, icon: "label", selected: true},
    {
      name: `milestone(${openedMilestoneCount})`,
      icon: "milestone",
      selected: false,
    },
  ];

  const onTabClick = () => {
    console.log("click tab");
  };

  return (
      <Div>
        <TabButton onClick={onTabClick}>
          {tabs.map(({name, icon, selected}, index) => (
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
            size="S"
            buttonType="Container"
            icon="plus"
            onClick={onClick}
            disabled={isAdding}
        >
          레이블 추가
        </Button>
      </Div>
  );
}

const Div = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
`;
