import { useTheme } from '@emotion/react';
import React, { useState } from 'react';

export const Title: React.FC = () => {
  const theme = useTheme() as any;
  return (
    <h2
      css={{
        font: theme.fonts.displayBold32,
        color: theme.neutral.text.strong,
      }}
    >
      새로운 이슈 작성
    </h2>
  );
};
