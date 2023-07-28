import { css } from '@emotion/react';
import { font } from '../../styles/font';
import { ReactNode } from 'react';

type Props = {
  children: ReactNode;
};

export default function MainWrapper({ children }: Props) {
  return <div css={mainWrapper}>{children}</div>;
}

const mainWrapper = css`
  width: 1280px;
  margin: 0 auto;
  padding: 0 40px;
  font: ${font.availableMedium16};
`;
