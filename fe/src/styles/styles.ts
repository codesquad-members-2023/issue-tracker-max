import { COLOR_BASE } from './base/color';
import { FONT_BASE } from './base/font';

export const themes = {
  light: {
    neutral: {
      textWeak: COLOR_BASE.grayscale600,
      textDefault: COLOR_BASE.grayscale700,
      textStrong: COLOR_BASE.grayscale900,
      surfaceDefault: COLOR_BASE.grayscale100,
      surfaceBold: COLOR_BASE.grayscale200,
      surfaceStrong: COLOR_BASE.grayscale50,
      borderDefault: COLOR_BASE.grayscale300,
      borderDefaultActive: COLOR_BASE.grayscale900,
    },
    brand: {
      textWeak: COLOR_BASE.accentBlue,
      textDefault: COLOR_BASE.grayscale50,
      surfaceWeak: COLOR_BASE.grayscale50,
      surfaceDefault: COLOR_BASE.accentBlue,
      borderDefault: COLOR_BASE.accentBlue,
    },
    danger: {
      textDefault: COLOR_BASE.accentRed,
      surfaceDefault: COLOR_BASE.accentRed,
      borderDefault: COLOR_BASE.accentRed,
    },
    palette: {
      blue: COLOR_BASE.accentBlue,
      navy: COLOR_BASE.accentNavy,
      red: COLOR_BASE.accentRed,
      orange: COLOR_BASE.accentOrange,
      offWhite: COLOR_BASE.grayscale50,
    },
  },
  dark: {
    neutral: {
      textWeak: COLOR_BASE.grayscale500,
      textDefault: COLOR_BASE.grayscale400,
      textStrong: COLOR_BASE.grayscale50,
      surfaceDefault: COLOR_BASE.grayscale900,
      surfaceBold: COLOR_BASE.grayscale700,
      surfaceStrong: COLOR_BASE.grayscale800,
      borderDefault: COLOR_BASE.grayscale600,
      borderDefaultActive: COLOR_BASE.grayscale300,
    },
    brand: {
      textWeak: COLOR_BASE.accentBlue,
      textDefault: COLOR_BASE.grayscale50,
      surfaceWeak: COLOR_BASE.grayscale900,
      surfaceDefault: COLOR_BASE.accentBlue,
      borderDefault: COLOR_BASE.accentBlue,
    },
    danger: {
      textDefault: COLOR_BASE.accentRed,
      surfaceDefault: COLOR_BASE.accentRed,
      borderDefault: COLOR_BASE.accentRed,
    },
    palette: {
      blue: COLOR_BASE.accentBlue,
      navy: COLOR_BASE.accentNavy,
      red: COLOR_BASE.accentRed,
      orange: COLOR_BASE.accentOrange,
      offWhite: COLOR_BASE.grayscale50,
    },
  },
};

export const font = {
  displayBold32: FONT_BASE.bold32,
  displayBold20: FONT_BASE.bold20,
  displayBold16: FONT_BASE.bold16,
  displayBold12: FONT_BASE.bold12,
  displayMedium20: FONT_BASE.medium20,
  displayMedium16: FONT_BASE.medium16,
  displayMedium12: FONT_BASE.medium12,
  selectedBold20: FONT_BASE.bold20,
  selectedBold16: FONT_BASE.bold16,
  selectedBold12: FONT_BASE.bold12,
  availableMedium20: FONT_BASE.medium20,
  availableMedium16: FONT_BASE.medium16,
  availableMedium12: FONT_BASE.medium12,
};

export const radius = {
  half: '50%',
  small: '10px',
  medium: '12px',
  large: '16px',
};

export const border = {
  default: '1px solid',
  icon: '1.6px solid',
  dash: '1px dash',
};

export const opacity = {
  default: '1',
  hover: '0.8',
  press: '0.64',
  disabled: '0.32',
};

export const shadow = {
  light: '0px 0px 8px 0px #14142B0A',
  dark: '0px 0px 16px 0px #14142BCC',
};
