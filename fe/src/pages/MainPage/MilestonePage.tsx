import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import MilestonesTable from "@components/Table/MilestonesTable";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getLabels, getMilestones } from "api";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function MilestonePage() {
  const navigate = useNavigate();

  const { data: labelsList } = useFetch(getLabels);
  const { data: openMilestone } = useFetch(getMilestones);
  const { data: closedMilestone } = useFetch(
    useCallback(() => getMilestones("closed"), [])
  );

  const [isAddNewMilestone, setIsAddNewMilestone] = useState(false);

  const openMilestoneCount = openMilestone?.length || 0;
  const closedMilestoneCount = closedMilestone?.length || 0;

  const tabBarLeftInfo = {
    name: "레이블",
    count: labelsList ? labelsList.length : 0,
    iconSrc: labelIcon,
    callback: () => navigate("/labels"),
  };
  const tabBarRightInfo = {
    name: "마일스톤",
    count: openMilestoneCount + closedMilestoneCount,
    iconSrc: milestoneIcon,
    callback: () => navigate("/milestones"),
  };

  const openAddNewMilestone = () => {
    setIsAddNewMilestone(true);
  };

  return (
    <StyledLabelPage>
      <LabelNav>
        <TabBar
          currentTabName="마일스톤"
          left={tabBarLeftInfo}
          right={tabBarRightInfo}
          borderStyle="outline"
        />
        <Button
          variant="container"
          size="S"
          disabled={isAddNewMilestone === true}
          onClick={openAddNewMilestone}>
          <img src={plusIcon} alt="편집 취소" />
          <span>마일스톤 추가</span>
        </Button>
      </LabelNav>

      {isAddNewMilestone && <div>add new milestone component</div>}
      <MilestonesTable {...{ openMilestone, closedMilestone }} />
    </StyledLabelPage>
  );
}

const StyledLabelPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const LabelNav = styled.nav`
  display: flex;
  justify-content: space-between;
`;
