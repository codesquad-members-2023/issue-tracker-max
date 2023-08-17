import { useTheme } from '@emotion/react';

type Props = {
  label?: string;
  height?: 56 | 40;
  disabled?: boolean;
  isError?: boolean;
  radius?: 'm' | 'l';
  hasborderColor?: boolean;
  children?: React.ReactNode;
};

export const InputContainer: React.FC<Props> = ({
  label,
  height,
  disabled,
  isError,
  radius = 'l',
  hasborderColor = false,
  children,
}) => {
  const theme = useTheme() as any;

  return (
    <label
      htmlFor={label}
      css={{
        boxSizing: 'border-box',
        width: '100%',
        display: 'flex',
        flexDirection: height === 56 ? 'column' : 'row',
        alignItems: height === 56 ? '' : 'center',
        justifyContent: 'center',
        height: `${height}px`,
        borderRadius: theme.radius[radius],
        cursor: disabled ? 'default' : 'text',
        backgroundColor: theme.neutral.surface.bold,
        gap: height === 56 ? '' : '8px',

        border: isError
          ? `${theme.border.default} ${theme.danger.border.default}`
          : hasborderColor
          ? `${theme.border.default} ${theme.neutral.border.default}`
          : `${theme.border.default} ${theme.neutral.surface.bold}`,

        '&:focus-within': {
          backgroundColor: theme.neutral.surface.strong,
          border: isError
            ? `${theme.border.default} ${theme.danger.border.default}`
            : `${theme.border.default} ${theme.neutral.border.defaultActive}`,
        },
      }}
    >
      {children}
    </label>
  );
};
