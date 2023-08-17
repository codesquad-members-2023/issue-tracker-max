import { styled } from 'styled-components';
import Icons from '../../../design/Icons';

type ButtonProps = React.HTMLAttributes<HTMLButtonElement> & {
  type: 'button' | 'submit' | 'reset';
  iconName?: keyof typeof Icons;
  children?: React.ReactNode;
  disabled?: boolean;
  flexible?: boolean;
  outline?: boolean;
  ghost?: boolean;
  selected?: boolean;
};

export default function Button(props: ButtonProps) {
  const {
    type,
    iconName,
    children,
    disabled,
    flexible,
    outline,
    ghost,
    selected,
    ...rest
  } = props;
  const hasIcon = iconName !== undefined;
  const Icon = Icons[iconName ?? 'default'] as React.FunctionComponent<
    React.SVGProps<SVGSVGElement>
  >;

  return (
    <RealButton
      type={type || 'button'}
      disabled={disabled}
      $flexible={flexible}
      $outline={outline}
      $ghost={ghost}
      $selected={selected}
      {...rest}>
      {hasIcon && <Icon />}
      <TextLabel $ghost={ghost}>{children}</TextLabel>
    </RealButton>
  );
}

type StyledButtonProps = {
  $flexible?: boolean;
  $outline?: boolean;
  $ghost?: boolean;
  $selected?: boolean;
};

const RealButton = styled.button<StyledButtonProps>`
  width: ${({ $flexible }) => ($flexible ? 'auto' : '184px')};
  min-height: 48px;
  padding: 0 16px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  background: ${({ theme }) => theme.color.brand.surface.default};
  color: ${({ theme }) => theme.color.brand.text.default};
  ${({ theme }) => theme.font.available.medium[16]}
  cursor: pointer;

  svg {
    width: 16px;
    height: 16px;
    path {
      stroke: ${({ theme }) => theme.color.brand.text.default};
    }

    rect {
      fill: ${({ theme }) => theme.color.brand.text.default};
    }
  }

  &:hover {
    opacity: ${({ theme }) => theme.objectStyles.opacity.hover};
  }

  &:active {
    opacity: ${({ theme }) => theme.objectStyles.opacity.press};
  }

  &:disabled {
    opacity: ${({ theme }) => theme.objectStyles.opacity.disabled};
  }

  ${({ theme, $outline }) =>
    $outline &&
    `
    background: transparent;
    border: 1px solid ${theme.color.brand.border.default};
    color: ${theme.color.brand.text.weak};

    svg {
      path {
        stroke: ${theme.color.brand.text.weak};
      }

      rect {
        fill: ${theme.color.brand.text.weak};
      }
    }
    `}

  ${({ theme, $ghost, $flexible, $selected }) =>
    $ghost &&
    `
    min-height: 32px;
    padding: ${$flexible ? '0' : '0 16px'};
    gap: 4px;
    background-color: transparent;
    border: none;
    border-radius: 0;
    color: ${
      $selected
        ? theme.color.neutral.text.strong
        : theme.color.neutral.text.default
    };
    ${$selected && theme.font.selected.bold[16]};

    svg {
      path {
        stroke: ${
          $selected
            ? theme.color.neutral.text.strong
            : theme.color.neutral.text.default
        };
      }

      rect {
        fill: ${
          $selected
            ? theme.color.neutral.text.strong
            : theme.color.neutral.text.default
        };
      }
    }
    `}
`;

const TextLabel = styled.span<{ $ghost?: boolean }>`
  text-align: center;
  ${({ $ghost }) => {
    if (!$ghost) {
      return 'padding: 0 8px;';
    }
    return 'padding: 0;';
  }}
`;
