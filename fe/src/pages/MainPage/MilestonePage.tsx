import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import MilestoneEditor from "@components/Milestone/MilestoneEditor";
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
  const { data: openMilestone, reFetch: updateOpenMilestone } =
    useFetch(getMilestones);
  const { data: closedMilestone, reFetch: updateCloseMilestone } = useFetch(
    useCallback(() => getMilestones("closed"), [])
  );

  const [isAddNewMilestone, setIsAddNewMilestone] = useState(false);

  const openAddNewMilestone = () => {
    setIsAddNewMilestone(true);
  };

  return (
    <StyledLabelPage>
      <LabelNav>
        {labelsList && openMilestone && closedMilestone && (
          <TabBar
            currentTabName="마일스톤"
            left={{
              name: "레이블",
              count: labelsList.length,
              iconSrc: labelIcon,
              callback: () => navigate("/labels"),
            }}
            right={{
              name: "마일스톤",
              count: openMilestone.length + closedMilestone.length,
              iconSrc: milestoneIcon,
              callback: () => navigate("/milestones"),
            }}
            borderStyle="outline"
          />
        )}
        <Button
          variant="container"
          size="S"
          disabled={isAddNewMilestone === true}
          onClick={openAddNewMilestone}>
          <img src={plusIcon} alt="마일스톤 추가 아이콘" />
          <span>마일스톤 추가</span>
        </Button>
      </LabelNav>

      {isAddNewMilestone && (
        <MilestoneEditorContainer>
          <MilestoneEditor
            variant="add"
            closeEditor={() => setIsAddNewMilestone(false)}
            updateOpenMilestone={updateOpenMilestone}
          />
        </MilestoneEditorContainer>
      )}
      {openMilestone && closedMilestone && (
        <MilestonesTable
          {...{
            openMilestone,
            closedMilestone,
            updateOpenMilestone,
            updateCloseMilestone,
          }}
        />
      )}
    </StyledLabelPage>
  );
}

const MilestoneEditorContainer = styled.div`
  padding: 32px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};
`;

const StyledLabelPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const LabelNav = styled.nav`
  display: flex;
  justify-content: space-between;
`;
