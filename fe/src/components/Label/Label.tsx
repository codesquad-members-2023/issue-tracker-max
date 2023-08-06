import { Theme, css, useTheme } from '@emotion/react';
import { border, radius, font } from '../../styles/styles';

export default function Label(label: LabelType) {
  const theme = useTheme();

  return (
    <div css={labelStyle(theme, label.textColor, label.backgroundColor)}>
      {label.name}
    </div>
  );
}

const labelStyle = (
  theme: Theme,
  textColor: string,
  backgroundColor: string
) => css`
  display: inline-block;
  height: 24px;
  padding: 0 12px;
  border-radius: ${radius.large};
  border: ${backgroundColor === '#FEFEFE'
    ? `${border.default} ${theme.neutral.borderDefault}`
    : 'none'};
  font: ${font.displayMedium12};
  color: ${textColor};
  background-color: ${backgroundColor};
  line-height: 24px;
`;
