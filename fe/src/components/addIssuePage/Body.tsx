import { Theme } from '@emotion/react';

type Props = {
  children?: React.ReactNode;
};

export const Body: React.FC<Props> = ({ children }) => {
  return <main css={mainStyle}>{children}</main>;
};

const mainStyle = (theme: Theme) => ({
  display: 'flex',
  gap: '24px',
  borderTop: `${theme.border.default} ${theme.neutral.border.default}`,
  borderBottom: `${theme.border.default} ${theme.neutral.border.default}`,
  padding: '24px 0',
});
