import { useTheme } from '@emotion/react';
// import { useState } from 'react';

import { OAuthButton } from '@components/signPage/OAuthButton';
import { LoginForm } from '@components/signPage/LoginForm';
import { ReactComponent as LargeLogo } from '@assets/logos/largeLogo.svg';

type Props = {};

export const SignPage: React.FC<Props> = () => {
  const theme = useTheme() as any;
  // const [isSignError, setIsSignError] = useState(false);

  return (
    <>
      <div
        css={{
          border: '1px solid black',
          width: '100%',
          height: '100vh',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          gap: '16px',
        }}
      >
        <div css={{ height: 'fit-content', marginBottom: '48px' }}>
          <LargeLogo fill={theme.neutral.text.strong} />
        </div>
        <OAuthButton />
        <span
          css={{
            font: theme.fonts.displayMedium16,
            color: theme.neutral.text.weak,
          }}
        >
          or
        </span>
        <LoginForm />
        {/* {isSignError && (
          <span css={{ color: theme.danger.text.default }}>
            실패사유 다시 시도해 주세요
          </span>
        )} */}
      </div>
    </>
  );
};
