import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    neutral: {
      text: {
        weak: string;
        default: string;
        strong: string;
      };
      surface: {
        default: string;
        bold: string;
        strong: string;
      };
      border: {
        default: string;
        defaultActive: string;
      };
    };
    brand: {
      text: {
        weak: string;
        default: string;
      };
      surface: {
        weak: string;
        default: string;
      };
      border: {
        default: string;
      };
    };
    danger: {
      text: {
        default: string;
      };
      border: {
        default: string;
      };
    };
    palette: {
      blue: string;
      navy: string;
      red: string;
    };
    boxShadow: string;

    colors: Colors;
    border: Border;
    radius: Radius;
    opacity: Opacity;
    fonts: Fonts;
  }
}

type Colors = {
  $grayscale50: string;
  $grayscale100: string;
  $grayscale200: string;
  $grayscale300: string;
  $grayscale400: string;
  $grayscale500: string;
  $grayscale600: string;
  $grayscale700: string;
  $grayscale800: string;
  $grayscale900: string;
  $accentBlue: string;
  $accentNavy: string;
  $accentRed: string;
};

type Border = {
  default: string;
  icon: string;
  dash: string;
};

type Radius = {
  m: string;
  l: string;
  half: string;
};

type Opacity = {
  hover: number;
  press: number;
  disabled: number;
  transparent: number;
};

type Fonts = {
  displayBold32: string;
  displayBold20: string;
  displayBold16: string;
  displayBold12: string;
  displayMedium20: string;
  displayMedium16: string;
  displayMedium12: string;
  selectedBold20: string;
  selectedBold16: string;
  selectedBold12: string;

  availableMedium20: string;
  availableMedium16: string;

  availableMedium12: string;
};
