import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';

export const OAuthButton: React.FC = () => {
  const theme = useTheme() as any;

  return (
    <>
      <Button
        typeVariant="outline"
        size="L"
        css={{
          width: '320px',
          borderRadius: theme.radius.l,
        }}
      >
        GitHub 계정으로 로그인
      </Button>
    </>
  );
};
