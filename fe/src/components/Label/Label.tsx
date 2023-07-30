import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { LabelType } from "../../pages/LabelPage";
import { fonts } from "../../constants/fonts";

type Props = {
  label?: LabelType;
  isDark?: boolean;
  labelTitle?: string;
  randomColor?: string;
  isEditCompleted?: boolean;
  mode?: string;
};

const getLabelName = (
  label?: LabelType,
  labelTitle?: string,
  isEditCompleted?: boolean
) => {
  const defaultLabelTitle = labelTitle || "Label";
  if (isEditCompleted) return labelTitle;
  if (label) return label.title;
  return defaultLabelTitle;
};

const labelStyle = (
  color: ColorScheme,
  label: any,
  isDark?: boolean,
  randomColor?: string
) => css`
  border: ${label
    ? label.backgroundColor === "#FEFEFE"
      ? `1px solid ${color.neutral.border.default}`
      : "none"
    : `1px solid ${color.neutral.border.default}`};
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 16px;
  padding: 0px 12px;
  box-sizing: border-box;
  background-color: ${randomColor
    ? randomColor
    : label
    ? label.backgroundColor
    : color.palette.offWhite};
  width: max-content;
  height: 24px;
  white-space: nowrap;
  ${fonts.medium12};
  color: ${isDark ? color.neutral.text.weak : color.brand.text.default};
`;

export function Label({
  label,
  isDark,
  labelTitle,
  randomColor,
  isEditCompleted,
}: Props) {
  const color = useTheme() as ColorScheme;

  const labelName = getLabelName(label, labelTitle, isEditCompleted);

  return (
    <div css={labelStyle(color, label, isDark, randomColor)}>{labelName}</div>
  );
}
