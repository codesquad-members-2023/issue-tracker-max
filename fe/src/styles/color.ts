const COLOR_BASE = {
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

export const color = {
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
  },
};
