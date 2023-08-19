import { css } from '@emotion/react';
import { font, radius } from '../../styles/styles';
import { ButtonHTMLAttributes } from 'react';

type ButtonSize = 'M' | 'S' | 'XS';
type fontSize = 'M' | 'S';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  icon?: React.ReactNode;
  size?: ButtonSize;
  fontSize?: fontSize;
  color: string;
  backgroundColor?: string;
  border?: string;
}

export default function Button({
  type = 'button',
  value,
  icon,
  onClick,
  size = 'M',
  fontSize = 'S',
  color,
  backgroundColor = 'inherit',
  border = 'none',
  disabled,
}: Props) {
  return (
    <button
      type={type}
      css={button(color, backgroundColor, border, size, fontSize)}
      onClick={onClick}
      disabled={disabled}
    >
      {icon}
      {value}
    </button>
  );
}

const button = (
  color: string,
  backgroundColor: string,
  border: string,
  size: 'M' | 'S' | 'XS',
  fontSize: 'M' | 'S'
) => css`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  width: ${BUTTON_STYLE[size].width};
  height: ${BUTTON_STYLE[size].height};
  border-radius: ${radius.medium};
  font: ${FONT_STYLE[fontSize].font};
  background-color: ${backgroundColor};
  color: ${color};
  border: ${border};

  & svg path {
    stroke: ${color} !important;
  }
`;

const MEDIUM_WIDTH = '128px';
const MEDIUM_HEIGHT = '40px';
const SMALL_WIDTH = '80px';
const SMALL_HEIGHT = '32px';
const X_SMALL_WIDTH = '41px';
const X_SMALL_HEIGHT = '32px';
const MEDIUM_FONT = font.availableMedium16;
const SMALL_FONT = font.availableMedium12;

const BUTTON_STYLE = {
  M: {
    width: MEDIUM_WIDTH,
    height: MEDIUM_HEIGHT,
  },
  S: {
    width: SMALL_WIDTH,
    height: SMALL_HEIGHT,
  },
  XS: {
    width: X_SMALL_WIDTH,
    height: X_SMALL_HEIGHT,
  },
};

const FONT_STYLE = {
  M: {
    font: MEDIUM_FONT,
  },
  S: {
    font: SMALL_FONT,
  },
};
