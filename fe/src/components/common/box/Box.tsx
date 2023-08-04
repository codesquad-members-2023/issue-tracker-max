import { useTheme } from '@emotion/react';

type Props = {
  children: React.ReactNode;
};

export const Box: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        overflow: 'hidden',

        li: {
          backgroundColor: theme.neutral.surface.strong,
        },

        'li:not(:last-child)': {
          borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
        },
      }}
    >
      {children}
    </div>
  );
};
