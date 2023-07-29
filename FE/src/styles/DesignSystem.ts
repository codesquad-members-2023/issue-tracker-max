import { colors } from "./base/Colors";
import { typoSystem } from "./base/Typo";
import { objectStyles } from "./base/Object";

export const lightMode = {
  colorSystem: {
    neutral: {
      text: {
        weak: colors.grayScale600,
        default: colors.grayScale700,
        strong: colors.grayScale900,
      },
      surface: {
        default: colors.grayScale100,
        bold: colors.grayScale200,
        strong: colors.grayScale50,
      },
      border: {
        default: colors.grayScale300,
        defaultActive: colors.grayScale900,
      },
    },
    brand: {
      text: {
        weak: colors.accentBlue,
        default: colors.grayScale50,
      },
      surface: {
        weak: colors.grayScale50,
        default: colors.accentBlue,
      },
      border: {
        default: colors.accentBlue,
      },
    },
    danger: {
      text: {
        default: colors.accentRed,
      },
      surface: {
        default: colors.accentRed,
      },
      border: {
        default: colors.accentRed,
      },
    },
    palette: {
      blue: colors.accentBlue,
      navy: colors.accentNavy,
      red: colors.accentRed,
    },
  },
  filter: {
    neutral: {
      text: {
        default:
          "brightness(0) saturate(100%) invert(28%) sepia(15%) saturate(1004%) hue-rotate(207deg) brightness(91%) contrast(82%);",
        strong:
          "brightness(0) saturate(100%) invert(7%) sepia(32%) saturate(1177%) hue-rotate(201deg) brightness(95%) contrast(97%);",
      },
    },
    brand: {
      text: {
        default:
          "brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(45deg) brightness(103%) contrast(103%);",
        weak: "brightness(0) saturate(100%) invert(29%) sepia(66%) saturate(2882%) hue-rotate(201deg) brightness(103%) contrast(105%);",
      },
    },
  },
  ...typoSystem,
  ...objectStyles,
};

export const darkMode = {
  colorSystem: {
    neutral: {
      text: {
        weak: colors.grayScale500,
        default: colors.grayScale400,
        strong: colors.grayScale50,
      },
      surface: {
        default: colors.grayScale900,
        bold: colors.grayScale700,
        strong: colors.grayScale800,
      },
      border: {
        default: colors.grayScale600,
        defaultActive: colors.grayScale300,
      },
    },
    brand: {
      text: {
        weak: colors.accentBlue,
        default: colors.grayScale50,
      },
      surface: {
        weak: colors.grayScale900,
        default: colors.accentBlue,
      },
      border: {
        default: colors.accentBlue,
      },
    },
    danger: {
      text: {
        default: colors.accentRed,
      },
      surface: {
        default: colors.accentRed,
      },
      border: {
        default: colors.accentRed,
      },
    },
    palette: {
      blue: colors.accentBlue,
      navy: colors.accentNavy,
      red: colors.accentRed,
    },
  },
  filter: {
    neutral: {
      text: {
        default:
          "brightness(0) saturate(100%) invert(90%) sepia(4%) saturate(1252%) hue-rotate(196deg) brightness(89%) contrast(85%);",
        strong:
          "brightness(0) saturate(100%) invert(100%) sepia(2%) saturate(469%) hue-rotate(289deg) brightness(120%) contrast(99%);",
      },
    },
    brand: {
      text: {
        default:
          "brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(45deg) brightness(103%) contrast(103%);",
        weak: "brightness(0) saturate(100%) invert(39%) sepia(89%) saturate(4222%) hue-rotate(199deg) brightness(100%) contrast(112%);",
      },
    },
  },
  ...typoSystem,
  ...objectStyles,
};
