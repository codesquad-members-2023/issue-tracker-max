import { Theme, css, useTheme } from '@emotion/react';
import { font } from '../../../styles/styles';

type Props = {
  valid: boolean;
  message: string;
};

export default function CheckValidInfo({ valid, message }: Props) {
  const theme = useTheme();

  return <div css={checkValid(theme)}>{!valid && message}</div>;
}

const checkValid = (theme: Theme) => css`
  width: 320px;
  height: 20px;
  margin: 5px 0 10px 0;
  padding-left: 16px;
  font: ${font.displayMedium12};
  color: ${theme.danger.textDefault};
`;
