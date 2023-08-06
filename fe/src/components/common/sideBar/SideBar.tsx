import { css, useTheme } from '@emotion/react';

type Props = {
  children?: React.ReactNode;
};

export const SideBar: React.FC<Props> = ({ children }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '288px',
        height: 'fit-content',
        background: theme.neutral.surface.strong,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        borderRadius: theme.radius.l,
      }}
    >
      {children}
    </div>
  );
};
