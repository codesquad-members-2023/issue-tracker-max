import { useTheme } from '@emotion/react';

type Props = {
  children: React.ReactNode;
};

export const BoxHeader: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        minHeight: '64px',
        borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
        borderRadius: `${theme.radius.l} ${theme.radius.l} 0px 0px`,
        backgroundColor: theme.neutral.surface.default,
        display: 'flex',
        alignItems: 'center',
      }}
    >
      {children}
    </div>
  );
};
