import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    neutral: {
      textWeak: string;
      textDefault: string;
      textStrong: string;
      surfaceDefault: string;
      surfaceBold: string;
      surfaceStrong: string;
      borderDefault: string;
      borderDefaultActive: string;
    };
    brand: {
      textWeak: string;
      textDefault: string;
      surfaceWeak: string;
      surfaceDefault: string;
      borderDefault: string;
    };
    danger: {
      textDefault: string;
      surfaceDefault: string;
      borderDefault: string;
    };
    palette: {
      blue: string;
      navy: string;
      red: string;
      orange: string;
      offWhite: string;
    };
  }
}
