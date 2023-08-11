import { useTheme } from '@emotion/react';
// import { useState } from 'react';

import { ReactComponent as LargeLogo } from '@assets/logos/largeLogo.svg';
import { RegisterForm } from '@components/signPage/RegisterForm';

type Props = {};

export const RegisterPage: React.FC<Props> = () => {
  const theme = useTheme() as any;
  // const [isRegisterError, setIsRegisterError] = useState(false);

  return (
    <>
      <div
        css={{
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

        <RegisterForm />
        {/* {isRegisterError && (
          <span css={{ color: theme.danger.text.default }}>
            {'실패사유'} 다시 시도해 주세요
          </span>
        )} */}
      </div>
    </>
  );
};
