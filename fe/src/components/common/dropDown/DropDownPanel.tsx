import { useTheme } from '@emotion/react';
import { DropDownHeader } from './DropDownHeader';

type Props = {
  position: keyof typeof POSITION;
  panelHeader: string;
  children: React.ReactNode;
};

export const DropDownPanel: React.FC<Props> = ({
  position,
  panelHeader,
  children,
}) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        position: 'absolute',
        top: '100%',
        zIndex: 50,
        width: '240px',
        borderRadius: theme.radius.l,
        border: `${theme.border.default} ${theme.neutral.border.default}`,
        background: theme.neutral.surface.default,
        ...POSITION[position],
      }}
    >
      <DropDownHeader panelHeader={panelHeader} />
      <ul
        css={{
          boxSizing: 'border-box',
          maxHeight: '288px',
          overflowY: 'auto',
        }}
      >
        {children}
      </ul>
    </div>
  );
};

const POSITION = {
  left: {
    left: 0,
  },

  right: {
    right: 0,
  },

  center: {
    left: 'calc(-120px + 50%)',
  },
};
