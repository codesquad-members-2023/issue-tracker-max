import { ColorScheme } from "../contexts/ThemeContext";
import { fonts } from "../constants/fonts";
import { css } from "@emotion/react";

export const tableStyle = (color: ColorScheme) => css`
  position: relative;
  display: flex;
  flex-direction: column;
  border-radius: 16px;
  border: 1px solid ${color.neutral.border.default};
  overflow: hidden;
`;

export const tableHeaderStyle = (color: ColorScheme) => css`
  display: flex;
  align-items: center;
  padding: 0 32px;
  box-sizing: border-box;
  height: 64px;
  color: ${color.neutral.text.default};
  background-color: ${color.neutral.surface.default};
  ${fonts.bold16},
`;
