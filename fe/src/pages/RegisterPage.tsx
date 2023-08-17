import { Theme, css } from '@emotion/react';
import { ReactComponent as LargeLogo } from '@assets/logos/largeLogo.svg';
import { RegisterForm } from '@components/signPage/RegisterForm';
import { Link } from 'react-router-dom';

export const RegisterPage: React.FC = () => {
  return (
    <div css={registerPageStyle}>
      <LargeLogo className="logo" />

      <RegisterForm />
      {/* {isRegisterError && (
          <span css={{ color: theme.danger.text.default }}>
            {'실패사유'} 다시 시도해 주세요
          </span>
        )} */}
      <Link to="/sign" className="sign-link">
        이미 가입한 아이디가 있나요?
      </Link>
    </div>
  );
};

const registerPageStyle = (theme: Theme) => css`
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;

  .logo {
    fill: ${theme.neutral.text.strong};
    margin-bottom: 64px;
  }

  .sign-link {
    text-decoration: none;
  }
`;
