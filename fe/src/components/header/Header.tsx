import { Outlet, useNavigate } from 'react-router-dom';
import { ReactComponent as MediumLogo } from '@assets/logos/mediumLogo.svg';
import { Theme, css, useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ThemeToggle } from './ThemeToggle';
import { PATH } from 'constants/PATH';
import { getLocalStorageImage } from 'apis/localStorage';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
  resetUserId?: () => void;
  resetAccessToken?: () => void;
};

export const Header: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  const navigate = useNavigate();
  const theme = useTheme() as any;

  const onLogoutClick = () => {
    localStorage.clear();
    navigate(PATH.SIGN_PAGE);
  };

  const image = getLocalStorageImage();

  return (
    <>
      <header css={headerStyle}>
        <MediumLogo
          className="logo"
          fill={theme.neutral.text.strong}
          onClick={() => {
            navigate(PATH.ISSUE_LIST_PAGE);
          }}
        />

        <div className="header-right">
          <ThemeToggle {...{ currentTheme, toggleTheme }} />

          <Button typeVariant="ghost" size="S" onClick={onLogoutClick}>
            로그아웃
          </Button>

          <img
            src={image || 'basic-profile.jpeg'}
            alt="프로필 사진"
            css={{ width: '32px', borderRadius: theme.radius.half }}
          />
        </div>
      </header>

      <Outlet />
    </>
  );
};

const headerStyle = (theme: Theme) => css`
  height: 94px;
  margin-bottom: 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .logo {
    cursor: pointer;
  }

  .header-right {
    display: flex;
    align-items: center;
    gap: 8px;
  }
`;
