import { colors } from "./base/Colors";
import { typoSystem } from "./base/Typo";
import { objectStyles } from "./base/Object";

export const lightMode = {
  colorSystem: {
    nuetral: {
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
  ...typoSystem,
  ...objectStyles,
};

export const darkMode = {
  colorSystem: {
    nuetral: {
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
  ...typoSystem,
  ...objectStyles,
};
