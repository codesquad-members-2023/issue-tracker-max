import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';

export const OAuthButton: React.FC = () => {
  return (
    <Button
      css={oAuthButtonStyle}
      typeVariant="outline"
      size="L"
      onClick={() => {
        window.location.href =
          'https://github.com/login/oauth/authorize?client_id=9531d38d8aa9bd9a4602';
      }}
    >
      GitHub 계정으로 로그인
    </Button>
  );
};

const oAuthButtonStyle = (theme: Theme) => css`
  min-height: 54px;
  min-width: 320px;
  border-radius: ${theme.radius.l};
`;
