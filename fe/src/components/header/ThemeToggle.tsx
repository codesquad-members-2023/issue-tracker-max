import { ReactComponent as SunIcon } from '@assets/icons/sun.svg';
import { ReactComponent as MoonIcon } from '@assets/icons/moon.svg';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
};

export const ThemeToggle: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  return (
    <button css={{ cursor: 'pointer' }} onClick={toggleTheme}>
      {currentTheme === 'light' ? (
        <SunIcon fill="#333333" />
      ) : (
        <MoonIcon fill="#f5f5f5" />
      )}
    </button>
  );
};
