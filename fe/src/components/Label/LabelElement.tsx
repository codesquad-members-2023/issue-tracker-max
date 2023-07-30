import { useContext, useEffect, useState } from "react";
import { Button } from "../common/Button";
// import { fonts } from "../util/Txt";
import { LabelDetail } from "./LabelDetail";
import { LabelType } from "../../pages/LabelPage";
import { Label } from "./Label";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useTheme } from "@emotion/react";
import { LabelContext } from "../../contexts/LabelContext";
import { fonts } from "../../constants/fonts";

export function LabelElement({ label }: { label: LabelType }) {
  const color = useTheme() as ColorScheme;
  const [isEditMode, setIsEditMode] = useState(false);
  const labelContextValue = useContext(LabelContext)!;
  const { setIsLabelAlertOpen, setDeleteElementId } = labelContextValue!;

  const onClickEditButton = () => {
    setIsEditMode(true);
  };

  const onClickCancelButton = () => {
    setIsEditMode(false);
  };

  const onClickCompleteButton = () => {
    setIsEditMode(false);
  };

  const onClickDeleteButton = () => {
    console.log(label.id, "번 레이블 삭제"); // API 연결 전까지 임시사용
    setIsLabelAlertOpen(true);
    setDeleteElementId(label.id);
  };

  useEffect(() => {
    if (isEditMode === false) {
    }
  }, [isEditMode]);

  if (isEditMode)
    return (
      <LabelDetail
        mode="edit"
        label={label}
        onClickCompleteButton={onClickCompleteButton}
        onClickCancelButton={onClickCancelButton}
      />
    );

  return (
    <div
      key={label.id}
      css={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        gap: "32px",
        padding: "0 32px",
        boxSizing: "border-box",
        width: "100%",
        height: "96px",
        backgroundColor: color.neutral.surface.strong,
        borderTop: `1px solid ${color.neutral.border.default}`,
        ...fonts.medium12,
      }}>
      <div className="labelWrapper" css={{ width: "178px", height: "24px" }}>
        <Label isDark={label.isDark} label={label} />
      </div>
      <div
        className="description"
        css={{
          display: "flex",
          alignItems: "center",
          width: "870px",
          height: "24px",

          color: color.neutral.text.weak,
          ...fonts.medium16,
        }}>
        {label.description}
      </div>
      <div
        className="buttonTab"
        css={{
          display: "flex",
          gap: "24px",
          width: "106px",
          height: "32px",
          justifyContent: "space-between",
          alignItems: "center",
        }}>
        <Button
          onClick={onClickEditButton}
          icon="edit"
          type="ghost"
          size="S"
          text="편집"
        />
        <Button
          onClick={onClickDeleteButton}
          icon="trash"
          type="ghost"
          size="S"
          text="삭제"
          textColor={color.danger.text.default}
        />
      </div>
    </div>
  );
}
