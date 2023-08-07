import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TabButton } from "../../components/TabButton";

type LabelHeaderProps = {
  onClick: () => void;
  isAdding: boolean;
  openedMilestoneCount: number;
  labelCount: number;
};

export function LabelHeader({
  onClick,
  isAdding,
  openedMilestoneCount,
  labelCount,
}: LabelHeaderProps) {
  const navigate = useNavigate();
  const tabs = [
    {
      name: `label(${labelCount})`,
      icon: "label",
      selected: true,
      onClick: () => {
        navigate("/label");
      },
    },
    {
      name: `milestone(${openedMilestoneCount})`,
      icon: "milestone",
      selected: false,
      onClick: () => {
        navigate("/milestone");
      },
    },
  ];

  return (
    <Div>
      <TabButton>
        {tabs.map(({ name, icon, selected, onClick }, index) => (
          <Button
            key={`tab-${index}`}
            icon={icon}
            size="M"
            buttonType="Ghost"
            flexible="Flexible"
            selected={selected}
            onClick={onClick}
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
