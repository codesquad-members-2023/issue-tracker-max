import { Theme } from '@emotion/react';

export const Title: React.FC = () => {
  return <h2 css={titleStyle}>새로운 이슈 작성</h2>;
};

const titleStyle = (theme: Theme) => ({
  font: theme.fonts.displayBold32,
  color: theme.neutral.text.strong,
});
