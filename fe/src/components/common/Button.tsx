import { Theme, css, useTheme } from '@emotion/react';
import { font, radius } from '../../styles/styles';
import { ButtonHTMLAttributes } from 'react';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  children?: React.ReactNode;
}

export default function Button({ type, value, children }: Props) {
  const theme = useTheme();

  return (
    <button type={type} css={button(theme)}>
      {children}
      {value}
    </button>
  );
}

const button = (theme: Theme) => css`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  width: 128px;
  height: 40px;
  padding: 0px 16px;
  border-radius: ${radius.medium};
  font: ${font.availableMedium12};
  background-color: ${theme.brand.surfaceDefault};
  color: ${theme.brand.textDefault};

  & svg path {
    stroke: ${theme.brand.textDefault};
  }
`;
