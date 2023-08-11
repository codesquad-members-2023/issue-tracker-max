import { useTheme } from '@emotion/react';

type Props = { children: React.ReactNode };

export const TabContainer: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        height: '40px',
        display: 'flex',
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        borderRadius: theme.radius.m,
        backgroundColor: theme.neutral.surface.default,
        overflow: 'hidden',
      }}
    >
      {children}
    </div>
  );
};
