import { useTheme } from '@emotion/react';

type Props = {
  size?: 'defaultSize' | 'S';
  isDisabled?: boolean;
  children: React.ReactNode;
};

export const TextAreaContainer: React.FC<Props> = ({
  size = 'defaultSize',
  isDisabled = false,
  children,
}) => {
  const theme = useTheme() as any;
  const SIZE = {
    S: {
      height: '184px',
    },
    defaultSize: {
      height: '552px',
    },
  };

  return (
    <div
      css={{
        width: '912px',
        display: 'flex',
        flexDirection: 'column',
        boxSizing: 'border-box',
        borderRadius: theme.radius.l,
        paddingTop: '16px',
        overflow: 'hidden',
        border: `${theme.border.default} ${theme.neutral.surface.bold}`,
        background: theme.neutral.surface.bold,
        opacity: isDisabled ? theme.opacity.disabled : 1,
        '&:focus-within': {
          background: theme.neutral.surface.strong,
          border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
        },
        ...SIZE[size],
      }}
    >
      {children}
    </div>
  );
};
