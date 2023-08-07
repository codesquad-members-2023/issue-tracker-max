import { useTheme } from '@emotion/react';
import { BoxHeader } from './BoxHeader';

type Props = {
  header: React.ReactNode;
  children: React.ReactNode;
  customStyle?: any;
};

export const Box: React.FC<Props> = ({ header, children, customStyle }) => {
  const theme = useTheme() as any;

  const boxStyle = {
    ...customStyle,
    borderRadius: theme.radius.l,
    border: `${theme.border.default} ${theme.neutral.border.default}`,
    overflow: 'hidden',

    li: {
      backgroundColor: theme.neutral.surface.strong,
    },

    'li:not(:last-child)': {
      borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
    },
  };

  return (
    <div css={boxStyle}>
      <BoxHeader>{header}</BoxHeader>

      <ul>{children}</ul>
    </div>
  );
};
