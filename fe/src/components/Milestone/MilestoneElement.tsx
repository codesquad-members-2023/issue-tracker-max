import { css, useTheme } from "@emotion/react";
import { fonts } from "../../constants/fonts";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Milestone } from "../../pages/MilestonePage";
import { Icon } from "../common/Icon";
import { Txt } from "../util/Txt";
import { Button } from "../common/Button";
import { useEffect, useState } from "react";
import { MilestoneDetail } from "./MilestoneDetail";

const elementContainer = (color: ColorScheme) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 32px;
  padding: 0 32px;
  box-sizing: border-box;
  width: 100%;
  height: 96px;
  background-color: ${color.neutral.surface.strong};
  border-top: 1px solid ${color.neutral.border.default};
  ...${fonts.medium12};
`;

const leftArea = css`
  width: 932px;
  height: 56px;
  gap: 8px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const leftAreaTop = css`
  display: flex;
  align-items: center;
  gap: 16px;
  width: 100%;
  height: 24px;
`;

const dueDay = css`
  display: flex;
  align-items: center;
  gap: 8px;
  height: 24px;
`;

const rightArea = css`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
`;

const progressIndicator = css`
  width: 244px;
  height: 32px;
  display: flex;
  gap: 8px;
  flex-direction: column;
`;

const progressBar = css`
  height: 8px;
  border-radius: 10px;
  background: linear-gradient(
    90deg,
    #007aff 0%,
    #007aff 50.51%,
    #eff0f6 50.52%,
    #eff0f6 100%
  );
`;

const issueCountContainer = css`
  display: flex;
  justify-content: space-between;
  width: 244px;
  height: 16px;
`;

const issueCount = css`
  display: flex;
  gap: 8px;
  width: 118px;
  height: 16px;
`;

export function MilestoneElement({ milestones }: { milestones: Milestone }) {
  const [isEditMode, setIsEditMode] = useState(false);

  const color = useTheme() as ColorScheme;

  useEffect(() => {
    if (isEditMode === false) {
    }
  }, [isEditMode]);

  const onClickEditButton = () => {
    setIsEditMode(true);
  };
  const onClickCancelButton = () => {
    setIsEditMode(false);
  };

  const onClickCompleteButton = () => {
    setIsEditMode(false);
  };

  if (isEditMode) {
    return (
      <MilestoneDetail
        mode="edit"
        milestone={milestones}
        onClickCompleteButton={onClickCompleteButton}
        onClickCancelButton={onClickCancelButton}
      />
    );
  }
  return (
    <div key={milestones.id} css={elementContainer(color)}>
      <div css={leftArea}>
        <div css={leftAreaTop}>
          <div css={{ display: "flex", alignItems: "center", gap: "8px" }}>
            <Icon type="milestone" color={color.palette.blue} />
            <Txt typography="medium16" color={color.neutral.text.strong}>
              {milestones.title}
            </Txt>
          </div>
          <div css={dueDay}>
            <Icon type="calendar" color={color.neutral.text.weak} />
            <Txt typography="medium12" color={color.neutral.text.weak}>
              {milestones.dueDay}
            </Txt>
          </div>
        </div>
        <Txt typography="medium16" color={color.neutral.text.weak}>
          {milestones.description}
        </Txt>
      </div>
      <div css={rightArea}>
        <div css={{ display: "flex", gap: "24px", height: "32px" }}>
          <Button
            type="ghost"
            size="S"
            text="닫기"
            icon="archive"
            textColor={color.neutral.text.default}
          />
          <Button
            onClick={onClickEditButton}
            type="ghost"
            size="S"
            text="편집"
            icon="edit"
            textColor={color.neutral.text.default}
          />
          <Button
            type="ghost"
            size="S"
            text="삭제"
            icon="trash"
            textColor={color.danger.text.default}
          />
        </div>
        <div css={progressIndicator}>
          <div css={progressBar}></div>
          <div css={issueCountContainer}>
            <Txt typography="medium12" color={color.neutral.text.weak}>
              50%
            </Txt>
            <div css={issueCount}>
              <Txt typography="medium12" color={color.neutral.text.weak}>
                열린 이슈 {milestones.issueOpenedCount}
              </Txt>
              <Txt typography="medium12" color={color.neutral.text.weak}>
                닫힌 이슈 {milestones.issueClosedCount}
              </Txt>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
