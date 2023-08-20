import { css } from '@emotion/react';

type Props = {
  children?: React.ReactNode;
};

export const InputContainer: React.FC<Props> = ({ children }) => {
  return <div css={InputContainerStyle}>{children}</div>;
};

const InputContainerStyle = css`
  display: flex;
  flex-direction: column;
  width: 100%;
`;
