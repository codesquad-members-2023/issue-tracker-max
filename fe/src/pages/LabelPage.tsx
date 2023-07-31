import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { TabButton } from "../components/common/TabButton";
import { useEffect, useState } from "react";
import { Button } from "../components/common/Button";
import { LabelDetail } from "../components/Label/LabelDetail";
import { LabelElement } from "../components/Label/LabelElement";
import { useNavigate } from "react-router-dom";
import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../contexts/ThemeContext";
import { Alert } from "../components/util/Alert";
// import { fonts } from "../constants/fonts";
import { tableHeaderStyle, tableStyle } from "../styles/commonStyles";
import { MainArea } from "../components/common/MainArea";
import { TotalCount } from "./MilestonePage";
import { LoadingBar } from "../components/common/LoadingBar";

export type LabelType = {
  id: number;
  title: string;
  description: string;
  backgroundColor: string;
  isDark: boolean;
};

const TOTAL_COUNT_URL =
  "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/common/navigation";
const LABEL_URL =
  "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/labels";

const tableContainer = css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  position: relative;
  top: 24px;
`;

export function LabelPage() {
  const [totalCount, setTotalCount] = useState<TotalCount | undefined>();
  const [labels, setLabels] = useState<LabelType[] | undefined>();
  const [loading, setLoading] = useState(true);

  const [isLeftSelected, setIsLeftSelected] = useState(true);
  const [isAddLabelOpen, setIsAddLabelOpen] = useState(false);

  const navigate = useNavigate();
  const color = useTheme() as ColorScheme;
  const labelTable = tableStyle(color);
  const labelTableHeader = tableHeaderStyle(color);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const totalCountResponse = await fetch(TOTAL_COUNT_URL);
        const labelsResponse = await fetch(LABEL_URL);
        const totalCountData = await totalCountResponse.json();
        const labelsData = await labelsResponse.json();

        setTotalCount(totalCountData);
        setLabels(labelsData.labels);
        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const leftText = "레이블";
  const rightText = "마일스톤";

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
    leftText: `${leftText}` + `(${totalCount ? totalCount.labelsCount : ""})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText:
      `${rightText}` + `(${totalCount ? totalCount?.milestonesCount : ""})`,
    onClickRightTab: onClickRightTab,
  };

  if (loading) {
    return (
      <Background>
        <LoadingBar />
        <Header />
      </Background>
    );
  }
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
        <div css={tableContainer}>
          {isAddLabelOpen ? (
            <LabelDetail
              mode="add"
              onClickCompleteButton={onClickCompleteButton}
              onClickCancelButton={onClickCancelButton}
            />
          ) : null}
          <div css={labelTable}>
            <div css={labelTableHeader}>
              {totalCount?.labelsCount}개의 레이블
            </div>
            {labels?.map((label) => (
              <LabelElement key={label.id} label={label} />
            ))}
          </div>
        </div>
      </MainArea>
      <Alert text="정말 이 레이블을 삭제하시겠습니까?" />
    </Background>
  );
}

// const labelList = [
//   {
//     id: 1,
//     title: "Label",
//     description: "레이블1 설명",
//     backgroundColor: "#FEFEFE",
//     isDark: true,
//   },
//   {
//     id: 2,
//     title: "documentation",
//     description: "레이블2 설명",
//     backgroundColor: "#0025E6",
//     isDark: false,
//   },
//   {
//     id: 3,
//     title: "bug",
//     description: "레이블3 설명",
//     backgroundColor: "#FF3B30",
//     isDark: false,
//   },
// ];
