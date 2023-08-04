import { css, useTheme } from '@emotion/react';

type Props = {
  children?: React.ReactNode;
};

export const ButtonContainer: React.FC<Props> = ({ children }) => {
  return (
    <div
      css={{
        display: 'flex',
        justifyContent: 'flex-end',
        alignItems: 'center',
      }}
    >
      {children}
    </div>
  );
};
