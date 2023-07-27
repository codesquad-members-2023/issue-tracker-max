import Accent from '../constant/Accent';

const { blue, navy, red } = Accent;

export const Color = {
  $grayScale: {
    50: '#FEFEFE',
    100: '#F7F7FC',
    200: '#EFF0F6',
    300: '#D9DBE9',
    400: '#BEC1D5',
    500: '#A0A3BD',
    600: '#6E7191',
    700: '#4E4B66',
    800: '#2A2A44',
    900: '#14142B',
  },
  $accent: {
    [blue]: '#007AFF',
    [navy]: '#0025E6',
    [red]: '#FF3B30',
  },
};

export default Color;
