import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as DarkModeIcon } from '../assets/icon/darkMode.svg';
import { ReactComponent as LightModeIcon } from '../assets/icon/lightMode.svg';
import { useContext } from 'react';
import { UserContext } from './Context/UserContext';
import { radius } from '../styles/styles';

export default function ThemeToggle() {
  const theme = useTheme();
  const { ...context } = useContext(UserContext);

  return (
    <button
      type="button"
      onClick={context.onToggleTheme}
      css={toggle(theme, !!context.darkMode)}
    >
      <div>{context.darkMode ? <DarkModeIcon /> : <LightModeIcon />}</div>
    </button>
  );
}

const toggle = (theme: Theme, isDark: boolean) => css`
  width: 80px;
  height: 40px;
  padding: 5px 10px;
  box-sizing: border-box;
  background-color: ${theme.neutral.borderDefault};
  border-radius: ${radius.XLarge};

  div {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: ${theme.neutral.surfaceBold};
    transition: transform 0.3s;
    transform: translateX(
      ${isDark ? `${DARK_TRANSLATE_X}` : `${LIGHT_TRANSLATE_X}`}
    );

    & svg path {
      stroke: ${theme.palette.orange};
      fill: ${theme.palette.orange};
    }
  }
`;

const DARK_TRANSLATE_X = '30px';
const LIGHT_TRANSLATE_X = '0';
