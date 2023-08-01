import { css, useTheme } from '@emotion/react';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { Button } from '../Button';

type Props = {
  size: 'L' | 'M' | 'defaultSize';
  onPanelOpen?: () => void;
  children: React.ReactNode;
};

export const DropDownIndicator: React.FC<Props> = ({
  size,
  onPanelOpen,
  children,
}) => {
  const theme = useTheme() as any;

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
    <div
      css={{
        boxSizing: 'border-box',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        font: theme.fonts.availableMedium16,
        ...SIZE[size],
      }}
      onClick={onPanelOpen}
    >
      {children}
    </div>
  );
};
