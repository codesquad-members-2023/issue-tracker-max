const colorDefault = {
  grayscale50: '#FEFEFE',
  grayscale100: '#F7F7FC',
  grayscale200: '#EFF0F6',
  grayscale300: '#D9DBE9',
  grayscale400: '#BEC1D5',
  grayscale500: '#A0A3BD',
  grayscale600: '#6E7191',
  grayscale700: '#4E4B66',
  grayscale800: '#2A2A44',
  grayscale900: '#14142B',
  accentBlue: '#007AFF',
  accentNavy: '#0025E6',
  accentRed: '#FF3B30',
};

const lightMode = {
  color: {
    textWeak: colorDefault.grayscale600,
    textDefault: colorDefault.grayscale700,
    textStrong: colorDefault.grayscale900,

    surfaceDefault: colorDefault.grayscale100,
    surfaceBold: colorDefault.grayscale200,
    surfaceStrong: colorDefault.grayscale50,

    borderDefault: colorDefault.grayscale300,
    borderDefaultActive: colorDefault.grayscale900,

    brandTextWeak: colorDefault.accentBlue,
    brandTextDefault: colorDefault.grayscale50,
    brandSurfaceWeak: colorDefault.grayscale50,
    brandSurfaceDefault: colorDefault.accentBlue,
    brandBorderDefault: colorDefault.accentBlue,

    dangerTextDefault: colorDefault.accentRed,
    dangerSurfaceDefault: colorDefault.accentRed,
    dangerborderDefault: colorDefault.accentRed,

    paletteBlue: colorDefault.accentBlue,
    paletteNavy: colorDefault.accentNavy,
    paletteRed: colorDefault.accentRed,
  },

  dropshadow: '0 0 8px rgba(20, 20, 43, 0.04)',
};

const darkMode = {
  color: {
    textWeak: colorDefault.grayscale500,
    textDefault: colorDefault.grayscale400,
    textStrong: colorDefault.grayscale50,

    surfaceDefault: colorDefault.grayscale900,
    surfaceBold: colorDefault.grayscale700,
    surfaceStrong: colorDefault.grayscale800,

    borderDefault: colorDefault.grayscale600,
    borderDefaultActive: colorDefault.grayscale300,

    brandTextWeak: colorDefault.accentBlue,
    brandTextDefault: colorDefault.grayscale50,
    brandSurfaceWeak: colorDefault.grayscale900,
    brandSurfaceDefault: colorDefault.accentBlue,
    brandBorderDefault: colorDefault.accentBlue,

    dangerTextDefault: colorDefault.accentRed,
    dangerSurfaceDefault: colorDefault.accentRed,
    dangerborderDefault: colorDefault.accentRed,

    paletteBlue: colorDefault.accentBlue,
    paletteNavy: colorDefault.accentNavy,
    paletteRed: colorDefault.accentRed,
  },

  dropshadow: '0 0 16px rgba(20, 20, 43, 0.8)',
};

const commonTheme = {
  font: {
    displayB32: "700 32px/48px 'Pretendard Variable', Pretendard",
    displayB20: "700 20px/32px 'Pretendard Variable', Pretendard",
    displayB16: "700 16px/24px 'Pretendard Variable', Pretendard",
    displayB12: "700 12px/16px 'Pretendard Variable', Pretendard",
    displayM20: "500 20px/32px 'Pretendard Variable', Pretendard",
    displayM16: "500 16px/24px 'Pretendard Variable', Pretendard",
    displayM12: "500 12px/16px 'Pretendard Variable', Pretendard",
    selectedB20: "700 20px/32px 'Pretendard Variable', Pretendard",
    selectedB16: "700 16px/24px 'Pretendard Variable', Pretendard",
    selectedB12: "700 12px/16px 'Pretendard Variable', Pretendard",
    availableM20: "500 20px/32px 'Pretendard Variable', Pretendard",
    availableM16: "500 16px/24px 'Pretendard Variable', Pretendard",
    availableM12: "500 12px/16px 'Pretendard Variable', Pretendard",
  },

  radius: {
    half: '50%',
    medium: '12px',
    large: '16px',
  },

  border: {
    default: '1px solid',
    icon: '1.6px solid',
    dash: '1px dash',
  },

  opacity: {
    default: '100%',
    hover: '80%',
    press: '64%',
    disabled: '32%',
  },
};

export const theme = {
  light: { ...commonTheme, ...lightMode },
  dark: { ...commonTheme, ...darkMode },
};
