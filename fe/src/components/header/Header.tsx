import { Outlet, useNavigate } from 'react-router-dom';
import { ReactComponent as MediumLogo } from '@assets/logos/mediumLogo.svg';
import { ReactComponent as UserImageLarge } from '@assets/icons/userImageLarge.svg';
import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';

type Props = {
  image: string;
  resetUserId?: () => void;
  resetAccessToken?: () => void;
  toggleTheme?: () => void;
};

export const Header: React.FC<Props> = ({ image }) => {
  const navigate = useNavigate();
  const theme = useTheme() as any;

  return (
    <>
      <header
        css={{
          height: '94px',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <MediumLogo
          fill={theme.neutral.text.strong}
          onClick={() => navigate('/')}
          css={{ cursor: 'pointer' }}
        />

        <div css={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Button typeVariant="ghost" size="S">
            로그아웃
          </Button>
          {image ? <img src={image} alt="프로필 사진" /> : <UserImageLarge />}
        </div>
      </header>

      <Outlet />
    </>
  );
};
