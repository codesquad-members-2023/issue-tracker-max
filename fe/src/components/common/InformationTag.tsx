import { useTheme } from '@emotion/react';
type TextColor = string;

type Props = {
  size: 'L' | 'S';
  typeVariant: 'default' | 'filled';
  fillColor?: string;
  textColor?: 'light' | 'dark' | TextColor;
  children: React.ReactNode;
};

export const InformationTag: React.FC<Props> = ({
  size = 'L',
  typeVariant = 'default',
  fillColor,
  textColor,
  children,
}) => {
  const theme = useTheme() as any;

  const TYPE_VARIANT = {
    default: {
      backgroundColor: theme.neutral.surface.bold,
      border: `${theme.border.default} ${theme.neutral.border.default}`,
    },
    filled: {
      backgroundColor: fillColor || theme.neutral.surface.bold,
      border: `${theme.border.default} ${fillColor}`,
    },
  };

  const SIZE = {
    L: {
      height: '32px',
      padding: '0px 16px',
    },
    S: {
      height: '24px',
      padding: '0px 8px',
    },
  };

  return (
    <div
      css={{
        boxSizing: 'border-box',
        display: 'flex',
        gap: '4px',
        alignItems: 'center',
        justifyContent: 'center',
        width: 'fit-content',
        borderRadius: theme.radius.l,
        font: theme.fonts.displayMedium12,
        color:
          textColor === 'light'
            ? theme.brand.text.default
            : theme.neutral.text.weak,

        ...SIZE[size],
        ...TYPE_VARIANT[typeVariant],
      }}
    >
      {children}
    </div>
  );
};
