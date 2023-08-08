import { useTheme } from '@emotion/react';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';
import { Button } from '../Button';

type Props = {
  size: keyof typeof SIZE;
  indicator: string;
};

export const DropDownIndicator: React.FC<Props> = ({ size, indicator }) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
      }}
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
