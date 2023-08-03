import { useTheme } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@components/common/Button';

export const NotFoundPage: React.FC = () => {
  const theme = useTheme() as any;
  const navigate = useNavigate();
  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100%',
        width: '100%',
        gap: '32px',
      }}
    >
      <span
        css={{
          font: theme.fonts.displayBold32,
          fontSize: '100px',
          color: theme.neutral.text.strong,
        }}
      >
        404
      </span>
      <span
        css={{
          font: theme.fonts.displayBold32,
          color: theme.neutral.text.strong,
        }}
      >
        페이지를 찾을 수 없습니다요
      </span>
      <Button
        typeVariant="outline"
        size="M"
        onClick={() => {
          navigate('/');
        }}
      >
        메인으로 돌아갈까요
      </Button>
    </div>
  );
};
