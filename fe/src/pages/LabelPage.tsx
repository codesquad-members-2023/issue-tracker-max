import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { TabButton } from "../components/common/TabButton";
import { useState } from "react";
import { Button } from "../components/common/Button";
import { LabelDetail } from "../components/Label/LabelDetail";
import { LabelElement } from "../components/Label/LabelElement";
import { useNavigate } from "react-router-dom";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../contexts/ThemeContext";
import { Alert } from "../components/util/Alert";
// import { fonts } from "../constants/fonts";
import { tableHeaderStyle, tableStyle } from "../styles/commonStyles";
import { MainArea } from "../components/common/MainArea";

export type LabelType = {
  id: number;
  title: string;
  description: string;
  backgroundColor: string;
  isDark: boolean;
};

export function LabelPage() {
  const [isLeftSelected, setIsLeftSelected] = useState(true);
  const [isAddLabelOpen, setIsAddLabelOpen] = useState(false);

  const navigate = useNavigate();
  const color = useTheme() as ColorScheme;
  const labelTable = tableStyle(color);
  const labelTableHeader = tableHeaderStyle(color);

  const leftText = "레이블";
  const rightText = "마일스톤";
  const labelCount = labelList.length;
  const milestoneCount = 2;
  const AddLabelButtonStatus = isAddLabelOpen ? "disabled" : "enabled";

  const onClickAddLabelButton = () => {
    setIsAddLabelOpen(true);
  };
  const onClickCompleteButton = () => {
    setIsAddLabelOpen(false);
  };
  const onClickCancelButton = () => {
    setIsAddLabelOpen(false);
  };

  const onClickMilestoneButton = () => {
    console.log("마일스톤 버튼 클릭");
    navigate("/milestone");
  };

  const onClickLeftTab = () => {
    setIsLeftSelected(true);
  };
  const onClickRightTab = () => {
    setIsLeftSelected(false);
    onClickMilestoneButton();
  };

  const leftTabProps = {
    leftIcon: "label",
    leftText: `${leftText}` + `(${labelCount})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText: `${rightText}` + `(${milestoneCount})`,
    onClickRightTab: onClickRightTab,
  };

  return (
    <Background>
      <Header />
      <MainArea>
        <div css={{ display: "flex", justifyContent: "space-between" }}>
          <TabButton
            isLeftSelected={isLeftSelected}
            leftTabProps={leftTabProps}
            rightTabProps={rightTabProps}
          />
          <div className="addLabelButton" onClick={onClickAddLabelButton}>
            <Button
              status={AddLabelButtonStatus}
              type="contained"
              size="S"
              text="레이블 추가"
              icon="plus"
            />
          </div>
        </div>
        <div
          css={{
            display: "flex",
            flexDirection: "column",
            gap: "24px",
            position: "relative",
            top: "24px",
          }}>
          {isAddLabelOpen ? (
            <LabelDetail
              mode="add"
              onClickCompleteButton={onClickCompleteButton}
              onClickCancelButton={onClickCancelButton}
            />
          ) : null}
          <div css={labelTable}>
            <div css={labelTableHeader}>{labelCount}개의 레이블</div>
            {labelList.map((label) => (
              <LabelElement key={label.id} label={label} />
            ))}
          </div>
        </div>
      </MainArea>
      <Alert text="정말 이 레이블을 삭제하시겠습니까?" />
    </Background>
  );
}

const labelList = [
  {
    id: 1,
    title: "Label",
    description: "레이블1 설명",
    backgroundColor: "#FEFEFE",
    isDark: true,
  },
  {
    id: 2,
    title: "documentation",
    description: "레이블2 설명",
    backgroundColor: "#0025E6",
    isDark: false,
  },
  {
    id: 3,
    title: "bug",
    description: "레이블3 설명",
    backgroundColor: "#FF3B30",
    isDark: false,
  },
];
