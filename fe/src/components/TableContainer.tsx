import { css } from '@emotion/react';
import { font } from '../styles/styles';
import { ReactNode } from 'react';

type Props = {
  children?: ReactNode;
};

export default function TableContainer({ children }: Props) {
  return <div css={tableContainer}>{children}</div>;
}

const tableContainer = css`
  width: 1280px;
  margin: 0 auto;
  padding: 0 40px;
  font: ${font.availableMedium16};
`;
