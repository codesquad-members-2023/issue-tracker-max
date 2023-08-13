import { Theme, css, useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { ReactComponent as MediumLogo } from '../../assets/logo/mediumLogo.svg';
import { ReactComponent as UserImageLargeIcon } from '../../assets/icon/userImageLarge.svg';
import { font } from '../../styles/styles';
import Button from '../common/Button';

export default function Header() {
  const theme = useTheme();
  const navigate = useNavigate();

  const onLogoClick = () => {
    navigate('/issue');
  };

  const onSignOutClick = () => {
    navigate('/sign-in');

    localStorage.clear();
  };

  return (
    <header css={header(theme)}>
      <MediumLogo className="logo" onClick={onLogoClick} />

      <div className="nav">
        <Button
          color={theme.neutral.textStrong}
          backgroundColor="inherit"
          value="로그아웃"
          size="S"
          fontSize="S"
          onClick={onSignOutClick}
        />
        <UserImageLargeIcon />
      </div>
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

  .logo {
    cursor: pointer;
  }

  .logo path {
    fill: ${theme.neutral.textStrong};
    stroke: none;
  }

  .nav {
    display: flex;
    align-items: center;
    gap: 32px;

    .sign-out {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 70px;
      height: 40px;
      font: ${font.displayBold12};
      color: ${theme.neutral.textStrong};
      background-color: inherit;
      cursor: pointer;
    }
  }
`;
