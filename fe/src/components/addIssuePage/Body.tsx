import { useTheme } from '@emotion/react';

type Props = {
  children?: React.ReactNode;
};

export const Body: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <main
      css={{
        display: 'flex',
        gap: '24px',
        borderTop: `${theme.border.default} ${theme.neutral.border.default}`,
        borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
        padding: '24px 0',
      }}
    >
      {children}
    </main>
  );
};
