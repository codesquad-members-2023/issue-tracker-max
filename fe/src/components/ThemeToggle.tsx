import { Theme, css, useTheme } from '@emotion/react';
import { ReactComponent as DarkModeIcon } from '../assets/icon/darkMode.svg';
import { ReactComponent as LightModeIcon } from '../assets/icon/lightMode.svg';

type Props = {
  onClick: () => void;
  isDark: boolean;
};

export default function ThemeToggle({ onClick, isDark }: Props) {
  const theme = useTheme();

  return (
    <button type="button" css={toggle(theme)} onClick={onClick}>
      {isDark ? <LightModeIcon /> : <DarkModeIcon />}
    </button>
  );
}

const toggle = (theme: Theme) => css`
  position: fixed;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: ${theme.neutral.borderDefault};
  bottom: 3rem;
  right: 3rem;
  padding: 5px;

  & svg path {
    stroke: ${theme.palette.orange};
    fill: ${theme.palette.orange};
  }
`;
