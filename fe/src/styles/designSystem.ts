import { decToHex } from '@utils/decToHex';

const colors = {
  $grayscale50: '#FEFEFE',
  $grayscale100: '#F7F7FC',
  $grayscale200: '#EFF0F6',
  $grayscale300: '#D9DBE9',
  $grayscale400: '#BEC1D5',
  $grayscale500: '#A0A3BD',
  $grayscale600: '#6E7191',
  $grayscale700: '#4E4B66',
  $grayscale800: '#2A2A44',
  $grayscale900: '#14142B',
  $accentBlue: '#007AFF',
  $accentNavy: '#0025E6',
  $accentRed: '#FF3B30',
};

const typographyDefaultValue = {
  $bold: {
    '32': '700 32px/48px Pretendard, sans-serif',
    '24': '700 24px/36px Pretendard, sans-serif',
    '20': '700 20px/32px Pretendard, sans-serif',
    '16': '700 16px/24px Pretendard, sans-serif',
    '12': '700 12px/16px Pretendard, sans-serif',
  },

  $medium: {
    '32': '500 32px/48px Pretendard, sans-serif',
    '24': '500 24px/36px Pretendard, sans-serif',
    '20': '500 20px/32px Pretendard, sans-serif',
    '16': '500 16px/24px Pretendard, sans-serif',
    '12': '500 12px/16px Pretendard, sans-serif',
  },
};

const fonts = {
  displayBold32: typographyDefaultValue.$bold[32],
  displayBold20: typographyDefaultValue.$bold[20],
  displayBold16: typographyDefaultValue.$bold[16],
  displayBold12: typographyDefaultValue.$bold[12],

  displayMedium20: typographyDefaultValue.$medium[20],
  displayMedium16: typographyDefaultValue.$medium[16],
  displayMedium12: typographyDefaultValue.$medium[12],

  selectedBold20: typographyDefaultValue.$bold[20],
  selectedBold16: typographyDefaultValue.$bold[16],
  selectedBold12: typographyDefaultValue.$bold[12],

  availableMedium20: typographyDefaultValue.$medium[20],
  availableMedium16: typographyDefaultValue.$medium[16],
  availableMedium12: typographyDefaultValue.$medium[12],
};

const opacity = {
  hover: 0.8,
  press: 0.64,
  disabled: 0.32,
  transparent: 0.04,
};

const radius = {
  m: '12px',
  l: '16px',
  half: '50%',
};

const border = {
  default: `1px solid`,
  icon: `1.6px solid`,
  dash: `1px dash`,
};

export const lightMode = {
  neutral: {
    text: {
      weak: colors.$grayscale600,
      default: colors.$grayscale700,
      strong: colors.$grayscale900,
    },
    surface: {
      default: colors.$grayscale100,
      bold: colors.$grayscale200,
      strong: colors.$grayscale50,
    },
    border: {
      default: colors.$grayscale300,
      defaultActive: colors.$grayscale900,
    },
  },
  brand: {
    text: {
      weak: colors.$accentBlue,
      default: colors.$grayscale50,
    },
    surface: {
      weak: colors.$grayscale50,
      default: colors.$accentBlue,
    },
    border: {
      default: colors.$accentBlue,
    },
  },
  danger: {
    text: {
      default: colors.$accentRed,
    },
    border: {
      default: colors.$accentRed,
    },
  },
  palette: {
    blue: colors.$accentBlue,
    navy: colors.$accentNavy,
    red: colors.$accentRed,
  },
  boxShadow: `0px 0px 8px 0px ${colors.$grayscale900}${decToHex(
    opacity.transparent,
  )}`,
  colors,
  border,
  radius,
  opacity,
  fonts,
};

export const darkMode = {
  neutral: {
    text: {
      weak: colors.$grayscale500,
      default: colors.$grayscale400,
      strong: colors.$grayscale50,
    },
    surface: {
      default: colors.$grayscale900,
      bold: colors.$grayscale700,
      strong: colors.$grayscale800,
    },
    border: {
      default: colors.$grayscale600,
      defaultActive: colors.$grayscale300,
    },
  },
  brand: {
    text: {
      weak: colors.$accentBlue,
      default: colors.$grayscale50,
    },
    surface: {
      weak: colors.$grayscale900,
      default: colors.$accentBlue,
    },
    border: {
      default: colors.$accentBlue,
    },
  },
  danger: {
    text: {
      default: colors.$accentRed,
    },
    border: {
      default: colors.$accentRed,
    },
  },
  palette: {
    blue: colors.$accentBlue,
    navy: colors.$accentNavy,
    red: colors.$accentRed,
  },
  boxShadow: `0px 0px 16px 0px ${colors.$grayscale900}${decToHex(
    opacity.hover,
  )}`,
  colors,
  border,
  radius,
  opacity,
  fonts,
};
