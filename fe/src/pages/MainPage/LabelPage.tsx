import labelIcon from "@assets/icon/label.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import plusIcon from "@assets/icon/plus.svg";
import LabelEditor from "@components/Label/LabelEditor";
import LabelsTableBody from "@components/Table/LabelsTable/LabelsTableBody";
import LabelsTableHeader from "@components/Table/LabelsTable/LabelsTableHeader";
import { Table } from "@components/Table/Table.style";
import Button from "@components/common/Button";
import TabBar from "@components/common/TabBar";
import useFetch from "@hooks/useFetch";
import { getLabels, getMilestoneCount } from "api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";

export default function LabelPage() {
  const navigate = useNavigate();

  const { data: labelsList, reFetch: updateLabelsList } = useFetch(getLabels);
  const { data: milestone } = useFetch(getMilestoneCount);
  const [isLabelEditorOpen, setIsLabelEditorOpen] = useState(false);

  const tabBarLeftInfo = {
    name: "레이블",
    count: labelsList?.length,
    iconSrc: labelIcon,
    callback: () => navigate("/labels"),
  };
  const tabBarRightInfo = {
    name: "마일스톤",
    count: milestone?.count,
    iconSrc: milestoneIcon,
    callback: () => navigate("/milestones"),
  };

  const openLabelEditor = () => {
    setIsLabelEditorOpen(true);
  };

  const closeLabelEditor = () => {
    setIsLabelEditorOpen(false);
  };

  return (
    <StyledLabelPage>
      <LabelNav>
        <TabBar
          currentTabName="레이블"
          left={tabBarLeftInfo}
          right={tabBarRightInfo}
          borderStyle="outline"
        />

        <Button
          variant="container"
          size="S"
          disabled={isLabelEditorOpen === true}
          onClick={openLabelEditor}>
          <img src={plusIcon} alt="편집 취소" />
          <span>레이블 추가</span>
        </Button>
      </LabelNav>

      {isLabelEditorOpen && (
        <LabelEditorContainer>
          <LabelEditor
            variant="add"
            closeEditor={closeLabelEditor}
            updateLabelsList={updateLabelsList}
          />
        </LabelEditorContainer>
      )}

      <Table>
        <LabelsTableHeader numLabels={labelsList?.length} />
        {labelsList && (
          <LabelsTableBody
            {...{
              labelsList,
              updateLabelsList,
            }}
          />
        )}
      </Table>
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

const LabelEditorContainer = styled.div`
  padding: 32px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};
`;
