import { Theme, css } from '@emotion/react';
import { OAuthButton } from '@components/signPage/OAuthButton';
import { LoginForm } from '@components/signPage/LoginForm';
import { ReactComponent as LargeLogo } from '@assets/logos/largeLogo.svg';
import { Button } from '@components/common/Button';
import { useNavigate } from 'react-router-dom';
import { PATH } from 'constants/PATH';

export const SignPage: React.FC = () => {
  const navigate = useNavigate();
  // const [isSignError, setIsSignError] = useState(false);

  return (
    <div css={signPageStyle}>
      <LargeLogo className="logo" />

      <OAuthButton />

      <span className="form-text">or</span>

      <LoginForm />

      {/* {isSignError && (
          <span css={{ color: theme.danger.text.default }}>
            실패사유 다시 시도해 주세요
          </span>
        )} */}

      <Button
        className="sign-up-button"
        typeVariant="ghost"
        size="L"
        onClick={() => {
          navigate(`/${PATH.REGISTER_PAGE}`);
        }}
      >
        회원가입
      </Button>
    </div>
  );
};

const signPageStyle = (theme: Theme) => css`
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;

  .logo {
    margin-bottom: 48px;
    fill: ${theme.neutral.text.strong};
  }

  .form-text {
    font: ${theme.fonts.displayMedium16};
    color: ${theme.neutral.text.weak};
  }

  .sign-up-button {
    width: 100%;
    border-radius: ${theme.radius.l};
  }
`;
