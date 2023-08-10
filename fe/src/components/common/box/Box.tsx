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
    backgroundColor: theme.neutral.surface.strong,

    '.box-header': {
      backgroundColor: theme.neutral.surface.default,
    },

    '& > :not(:last-child)': {
      borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
    },

    '& > :last-child': {
      borderRadius: `0px 0px ${theme.radius.l} ${theme.radius.l}`,
    },
  };

  return (
    <div css={boxStyle}>
      <BoxHeader>{header}</BoxHeader>

      {children}
    </div>
  );
};
