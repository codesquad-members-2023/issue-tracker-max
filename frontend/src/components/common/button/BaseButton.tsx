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
  onClick?: () => void;
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
    ...rest
  } = props;
  const hasIcon = iconName !== undefined;
  const Icon = Icons[iconName ?? 'default'];

  return (
    <RealButton
      type={type || 'button'}
      disabled={disabled}
      $flexible={flexible}
      $outline={outline}
      $ghost={ghost}
      {...rest}>
      {hasIcon && <Icon />}
      <TextLabel>{children}</TextLabel>
    </RealButton>
  );
}

type StyledButtonProps = {
  $flexible?: boolean;
  $outline?: boolean;
  $ghost?: boolean;
};

const RealButton = styled.button<StyledButtonProps>`
  width: ${({ $flexible }) => ($flexible ? 'auto' : '184px')};
  height: 48px;
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

  ${({ theme, $ghost, $flexible }) =>
    $ghost &&
    `
    padding: ${$flexible ? '0' : '0 16px'};
    background-color: transparent;
    border: none;
    border-radius: 0;
    color: ${theme.color.neutral.text.default};

    svg {
      path {
        stroke: ${theme.color.neutral.text.default};
      }

      rect {
        fill: ${theme.color.neutral.text.default};
      }
    }
    `}
`;

const TextLabel = styled.span`
  padding: 0 8px;
  text-align: center;
`;
