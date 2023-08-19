import { css } from '@emotion/react';
import { radius } from '../styles/styles';

type Props = {
  url?: string | undefined;
  userImageUrl?: string;
  size: 'M' | 'S';
};

export default function UserImageIcon({ url, size }: Props) {
  const userImageUrl = url ? url : '../../src/assets/user/default.jpeg';

  return <div css={userImageIcon({ userImageUrl, size })} />;
}

const userImageIcon = ({ userImageUrl, size }: Props) => css`
  width: ${ICON_SIZE[size].width};
  height: ${ICON_SIZE[size].width};
  border-radius: ${radius.half};
  background-image: url(${userImageUrl});
  background-size: ${ICON_SIZE[size].width}, ${ICON_SIZE[size].width};
  background-repeat: no-repeat;
  background-position: center;
`;

const MEDIUM_WIDTH = '32px';
const MEDIUM_HEIGHT = '32px';
const SMALL_WIDTH = '20px';
const SMALL_HEIGHT = '20px';

const ICON_SIZE = {
  M: {
    width: MEDIUM_WIDTH,
    height: MEDIUM_HEIGHT,
  },
  S: {
    width: SMALL_WIDTH,
    height: SMALL_HEIGHT,
  },
};
