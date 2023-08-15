import { DefaultTheme } from "styled-components";
import { ThemeColorKeys } from "../types/colors";

export const getColorCode = (
  color: ThemeColorKeys | string,
  theme: DefaultTheme,
) => {
  if (/^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/.test(color)) {
    return color;
  }
  return theme.color[color as ThemeColorKeys];
};
