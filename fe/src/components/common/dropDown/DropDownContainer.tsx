import { useTheme } from '@emotion/react';
import { DropDownIndicator } from './DropDownIndicator';

type Props = {
  size: keyof typeof SIZE;
  indicator: string;
  isPanelOpen: boolean;
  children: React.ReactNode;
  onClick: () => void;
};

export const DropDownContainer: React.FC<Props> = ({
  size,
  indicator,
  isPanelOpen,
  children,
  onClick,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      className="dropdown-panel"
      css={{
        position: 'relative',
        // boxSizing: 'border-box',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        font: theme.fonts.availableMedium16,
        width: '100%',
      }}
    >
      <div onClick={onClick}>
        <DropDownIndicator indicator={indicator} size={size} />
      </div>
      {isPanelOpen && <>{children}</>}
    </div>
  );
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
} as const;
