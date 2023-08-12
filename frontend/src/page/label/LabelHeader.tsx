import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TabButton } from "../../components/TabButton";
import { IconType } from "../../components/icon/Icon";

type LabelHeaderProps = {
  onClick: () => void;
  isAdding: boolean;
  openedMilestoneCount: number;
  labelCount: number;
};

type Tab = {
  name: string;
  icon: keyof IconType;
  selected: boolean;
  onClick: () => void;
};

export function LabelHeader({
  onClick,
  isAdding,
  openedMilestoneCount,
  labelCount,
}: LabelHeaderProps) {
  const navigate = useNavigate();
  const tabs: Tab[] = [
    {
      name: `label(${labelCount})`,
      icon: "Label",
      selected: true,
      onClick: () => {
        navigate("/label");
      },
    },
    {
      name: `milestone(${openedMilestoneCount})`,
      icon: "Milestone",
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
        icon="Plus"
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
