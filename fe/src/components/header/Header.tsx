import { Outlet, useNavigate } from 'react-router-dom';
import { ReactComponent as MediumLogo } from '@assets/logos/mediumLogo.svg';
import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ThemeToggle } from './ThemeToggle';

type Props = {
  image?: string;
  currentTheme: ThemeType;
  toggleTheme: () => void;
  resetUserId?: () => void;
  resetAccessToken?: () => void;
};

export const Header: React.FC<Props> = ({
  image,
  currentTheme,
  toggleTheme,
}) => {
  const navigate = useNavigate();
  const theme = useTheme() as any;

  return (
    <>
      <header
        css={{
          height: '94px',
          marginBottom: '32px',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <MediumLogo
          fill={theme.neutral.text.strong}
          onClick={() => {
            navigate('/issues');
          }}
          css={{ cursor: 'pointer' }}
        />

        <div css={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <ThemeToggle {...{ currentTheme, toggleTheme }} />

          <Button typeVariant="ghost" size="S">
            로그아웃
          </Button>

          <img
            src={image || 'src/assets/icons/basic-profile.jpeg'}
            alt="프로필 사진"
            css={{ width: '32px', borderRadius: theme.radius.half }}
          />
        </div>
      </header>

      <Outlet />
    </>
  );
};
