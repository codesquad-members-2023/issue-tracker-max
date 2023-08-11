import { useTheme } from '@emotion/react';
import { ButtonHTMLAttributes } from 'react';

type Props = {
  typeVariant?: 'contained' | 'outline' | 'ghost';
  size?: 'L' | 'M' | 'S' | 'defaultSize';
} & ButtonHTMLAttributes<HTMLButtonElement>;

export const Button: React.FC<Props> = ({
  typeVariant = 'contained',
  size = 'defaultSize',
  ...props
}) => {
  const theme = useTheme() as any;

  const TYPE_VARIANT = {
    contained: {
      backgroundColor: theme.brand.surface.default,
      color: theme.brand.text.default,
    },
    outline: {
      border: `${theme.border.default} ${theme.brand.border.default}`,
      color: theme.brand.text.weak,
    },
    ghost: {
      borderRadius: 0,
      color: theme.neutral.text.default,
    },
  };

  const SIZE = {
    L: {
      width: '240px',
      height: '56px',
      font: theme.fonts.availableMedium20,
      padding: '0px 24px',
    },
    M: {
      width: '184px',
      height: '48px',
      font: theme.fonts.availableMedium16,
      padding: '0px 24px',
    },
    S: {
      width: '128px',
      height: '40px',
      font: theme.fonts.availableMedium12,
      padding: '0px 16px',
    },
    defaultSize: {
      width: '100%',
      height: '100%',
    },
  };

  return (
    <button
      css={{
        cursor: 'pointer',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: '8px',
        borderRadius: theme.radius.m,

        '&:hover': {
          opacity: theme.opacity.hover,
        },

        '&:active': {
          opacity: theme.opacity.press,
        },

        '&:disabled': {
          opacity: theme.opacity.disabled,
        },

        ...TYPE_VARIANT[typeVariant],
        ...SIZE[size],
      }}
      {...props}
    >
      {props.children}
    </button>
  );
};
