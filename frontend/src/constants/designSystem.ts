const font = {
  displayBold32: "700 32px/48px Pretendard, sans-serif",
  displayBold20: "700 20px/32px Pretendard, sans-serif",
  displayBold16: "700 16px/24px Pretendard, sans-serif",
  displayBold12: "700 12px/16px Pretendard, sans-serif",
  displayMedium20: "500 20px/32px Pretendard, sans-serif",
  displayMedium16: "500 16px/24px Pretendard, sans-serif",
  displayMedium12: "500 12px/16px Pretendard, sans-serif",
  selectedBold20: "700 20px/32px Pretendard, sans-serif",
  selectedBold16: "700 16px/24px Pretendard, sans-serif",
  selectedBold12: "700 16px/24px Pretendard, sans-serif",
  availableMedium20: "500 20px/32px Pretendard, sans-serif",
  availableMedium16: "500 16px/24px Pretendard, sans-serif",
  availableMedium12: "500 12px/16px Pretendard, sans-serif",
};

const color = {
  grayScale50: "#FEFEFE",
  grayScale100: "#F7F7FC",
  grayScale200: "#EFF0F6",
  grayScale300: "#D9DBE9",
  grayScale400: "#BEC1D5",
  grayScale500: "#A0A3BD",
  grayScale600: "#6E7191",
  grayScale700: "#4B4E6D",
  grayScale800: "#2A2A44",
  grayScale900: "#14142B",
  accentBlue: "#007AFF",
  accentNavy: "#0025E6",
  accentRed: "#FF3B30",
};

const radius = {
  half: "50%",
  medium: "12px",
  large: "16px",
};

const border = {
  default: "1px solid",
  icon: "1.6px solid",
  dash: "1px dashed",
};

const opacity = {
  default: "1",
  hover: "0.8",
  press: "0.64",
  disabled: "0.32",
};

const LIGHT = {
  color: {
    neutralTextWeak: color.grayScale600,
    neutralTextDefault: color.grayScale700,
    neutralTextStrong: color.grayScale900,
    neutralSurfaceDefault: color.grayScale100,
    neutralSurfaceBold: color.grayScale200,
    neutralSurfaceStrong: color.grayScale50,
    neutralBorderDefault: color.grayScale300,
    neutralBorderDefaultActive: color.grayScale900,

    brandTextWeak: color.accentBlue,
    brandTextDefault: color.grayScale50,
    brandSurfaceWeak: color.grayScale50,
    brandSurfaceDefault: color.accentBlue,
    brandBorderDefault: color.accentBlue,

    dangerTextDefault: color.accentRed,
    dangerSurfaceDefault: color.accentRed,
    dangerBorderDefault: color.accentRed,

    paletteBlue: color.accentBlue,
    paletteNavy: color.accentNavy,
    paletteRed: color.accentRed,
  },
  dropShadow: "0 0 8px 0 rgba(20, 20, 43, 0.04)"
};

const DARK = {
  color: {
    neutralTextWeak: color.grayScale500,
    neutralTextDefault: color.grayScale400,
    neutralTextStrong: color.grayScale50,
    neutralSurfaceDefault: color.grayScale900,
    neutralSurfaceBold: color.grayScale700,
    neutralSurfaceStrong: color.grayScale800,
    neutralBorderDefault: color.grayScale600,
    neutralBorderDefaultActive: color.grayScale300,

    brandTextWeak: color.accentBlue,
    brandTextDefault: color.grayScale50,
    brandSurfaceWeak: color.grayScale900,
    brandSurfaceDefault: color.accentBlue,
    brandBorderDefault: color.accentBlue,

    dangerTextDefault: color.accentRed,
    dangerSurfaceDefault: color.accentRed,
    dangerBorderDefault: color.accentRed,

    paletteBlue: color.accentBlue,
    paletteNavy: color.accentNavy,
    paletteRed: color.accentRed,
  },
  dropShadow: "0 0 16px 0 rgba(20, 20, 43, 0.8)"
};

export const designSystem = {
  LIGHT: {
    font,
    radius,
    border,
    opacity,
    ...LIGHT,
  },
  DARK: {
    font,
    radius,
    border,
    opacity,
    ...DARK,
  },
};
