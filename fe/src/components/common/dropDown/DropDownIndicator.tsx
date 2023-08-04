import { useTheme } from '@emotion/react';
import { css } from '@emotion/react';
import React, { useState } from 'react';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';

import { Button } from '../Button';

type Props = {
  indicator: string;
  size: 'L' | 'M' | 'defaultSize';
  children: React.ReactNode;
};

export const DropDownIndicator: React.FC<Props> = ({
  size,
  indicator,
  children,
}) => {
  const theme = useTheme() as any;
  const [isPanelOpen, setIsPanelOpen] = useState(false);

  const onPanelOpen = () => {
    setIsPanelOpen(true);
  };

  const onPanelClose = (e: React.MouseEvent) => {
    e.stopPropagation();
    setIsPanelOpen(false);
  };

  const SIZE = {
    L: {
      width: '224px',
      height: '24px',
    },
    M: {
      width: '80px',
      height: '32px',
    },
    defaultSize: {
      width: 'fit-content',
      height: '32px',
    },
  };

  return (
    <>
      <div
        css={{
          position: 'relative',
          boxSizing: 'border-box',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          font: theme.fonts.availableMedium16,
          ...SIZE[size],
        }}
        onClick={onPanelOpen}
      >
        <Button
          typeVariant="ghost"
          css={{
            display: 'flex',
            justifyContent: 'space-between',
            font: theme.fonts.availableMedium16,
            width: '100%',
          }}
        >
          {indicator}
          {size === 'L' ? (
            <Plus stroke={theme.neutral.text.default} />
          ) : (
            <ChevronDown stroke={theme.neutral.text.default} />
          )}
        </Button>
        {isPanelOpen && (
          <>
            <div css={dim} onClick={onPanelClose}></div>
            {children}
          </>
        )}
      </div>
    </>
  );
};

const dim = css`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
  width: 100vw;
  height: 100vh;
`;
