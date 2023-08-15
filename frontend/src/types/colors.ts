import { designSystem } from "../constants/designSystem";

type DarkThemeColorKeys = keyof typeof designSystem.DARK.color;
type LightThemeColorKeys = keyof typeof designSystem.LIGHT.color;
type HexColorCode = `#${string}`;
export type ThemeColorKeys = DarkThemeColorKeys | LightThemeColorKeys;
export type Color = ThemeColorKeys | HexColorCode;
