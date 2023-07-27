import Typography from "./design/Typography";
import Color from "./design/Color";
import Accent from "./constant/Accent";

const { blue, navy, red } = Accent;

const baseTheme = {
  font: {
    display: {
      bold: {
        32: Typography.$bold[32],
        20: Typography.$bold[20],
        16: Typography.$bold[16],
        12: Typography.$bold[12],
      },
      medium: {
        20: Typography.$medium[20],
        16: Typography.$medium[16],
        12: Typography.$medium[12],
      },
    },
    selected: {
      bold: {
        20: Typography.$bold[20],
        16: Typography.$bold[16],
        12: Typography.$bold[12],
      },
    },
    available: {
      medium: {
        20: Typography.$medium[20],
        16: Typography.$medium[16],
        12: Typography.$medium[12],
      },
    },
  },

  objectStyles: {
    radius: {
      half: "50%",
      medium: "12px",
      large: "16px",
    },
    border: {
      default: "1px solid",
      icon: "1.6px solid",
      dash: "1px dashed",
    },
    opacity: {
      hover: 0.8,
      press: 0.64,
      disabled: 0.32,
    },
    dropShadow: {
      lightMode: "0px 0px 8px 0px rgba(20, 20, 43, 0.04);",
      darkMode: "0px 0px 16px 0px rgba(20, 20, 43, 0.80);",
    },
  },
};

const lightColor = {
  neutral: {
    text: {
      weak: Color.$grayScale[600],
      default: Color.$grayScale[700],
      strong: Color.$grayScale[900],
    },
    surface: {
      default: Color.$grayScale[100],
      bold: Color.$grayScale[200],
      strong: Color.$grayScale[50],
    },
    border: {
      default: Color.$grayScale[300],
      active: Color.$grayScale[900],
    },
  },
  brand: {
    text: {
      weak: Color.$accent[blue],
      default: Color.$grayScale[50],
    },
    surface: {
      weak: Color.$grayScale[50],
      default: Color.$accent[blue],
    },
    border: {
      default: Color.$accent[blue],
    },
  },
  danger: {
    text: {
      default: Color.$accent[red],
    },
    border: {
      default: Color.$accent[red],
    },
  },
  palette: {
    blue: Color.$accent[blue],
    navy: Color.$accent[navy],
    red: Color.$accent[red],
  },
};

const darkColor = {
  neutral: {
    text: {
      weak: Color.$grayScale[500],
      default: Color.$grayScale[400],
      strong: Color.$grayScale[50],
    },
    surface: {
      default: Color.$grayScale[900],
      bold: Color.$grayScale[700],
      strong: Color.$grayScale[800],
    },
    border: {
      default: Color.$grayScale[600],
      active: Color.$grayScale[300],
    },
  },
  brand: {
    text: {
      weak: Color.$accent[blue],
      default: Color.$grayScale[50],
    },
    surface: {
      weak: Color.$grayScale[900],
      default: Color.$accent[blue],
    },
    border: {
      default: Color.$accent[blue],
    },
  },
  danger: {
    text: {
      default: Color.$accent[red],
    },
    border: {
      default: Color.$accent[red],
    },
  },
  palette: {
    blue: Color.$accent[blue],
    navy: Color.$accent[navy],
    red: Color.$accent[red],
  },
};

export const lightTheme = { ...baseTheme, color: lightColor };
export const darkTheme = { ...baseTheme, color: darkColor };
