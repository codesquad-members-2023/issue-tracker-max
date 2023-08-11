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

    '& > :not(:last-child)': {
      borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
    },

    '& > :last-child': {
      borderRadius: `0px 0px ${theme.radius.l} ${theme.radius.l}`,
    },
  };

  const boxBodyStyle = {
    '& > ul > :not(:last-child)': {
      borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
    },
  };

  return (
    <div css={boxStyle}>
      <BoxHeader>{header}</BoxHeader>

      <div css={boxBodyStyle}>{children}</div>
    </div>
  );
};
