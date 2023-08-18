import { css } from '@emotion/react';
import { border, radius, font } from '../../styles/styles';
import { LabelType } from '../../type/label.type';

export default function Label({
  title,
  textColor,
  backgroundColor,
}: LabelType) {
  return (
    <div css={labelStyle(textColor, backgroundColor)}>
      {title ? title : 'Label'}
    </div>
  );
}

const labelStyle = (textColor: string, backgroundColor: string) => css`
  display: inline-block;
  min-width: 30px;
  height: 24px;
  padding: 0 12px;
  border-radius: ${radius.large};
  border: ${border.default} ${textColor};
  font: ${font.displayMedium12};
  color: ${textColor};
  background-color: ${backgroundColor};
  line-height: 24px;
  cursor: default;
`;
