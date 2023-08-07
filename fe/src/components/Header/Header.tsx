import { Theme, css, useTheme } from '@emotion/react';
import { Link } from 'react-router-dom';
import { ReactComponent as MediumLogo } from '/src/assets/logo/mediumLogo.svg';
import { ReactComponent as UserImageLargeIcon } from '/src/assets/icon/userImageLarge.svg';

export default function Header() {
  const theme = useTheme();

  return (
    <header css={header(theme)}>
      <Link to={'/issue'}>
        <MediumLogo className="logo" />
      </Link>
      <UserImageLargeIcon />
    </header>
  );
}

const header = (theme: Theme) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1280px;
  height: 94px;
  margin: 0 auto;
  padding: 0 40px;

  .logo path {
    fill: ${theme.neutral.textStrong};
    stroke: none;
  }
`;
