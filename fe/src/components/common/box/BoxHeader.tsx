import { useTheme } from '@emotion/react';

type Props = {
  children: React.ReactNode;
};

export const BoxHeader: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <div
      className="box-header"
      css={{
        minHeight: '64px',
        borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
        display: 'flex',
        alignItems: 'center',
      }}
    >
      {children}
    </div>
  );
};
