import { useState } from 'react';
import { styled } from 'styled-components';

type TextInputProps = React.HTMLAttributes<HTMLInputElement> & {
  size: 'tall' | 'short';
  id?: string;
  name: string;
  value: string;
  labelName?: string;
  type?: string;
  disabled?: boolean;
  placeholder?: string;
  helpText?: string;
  hasError?: boolean;
};

export default function TextInput(props: TextInputProps) {
  const [isFocused, setIsFocused] = useState<boolean>(false);

  const {
    id,
    name,
    size,
    type,
    value,
    labelName,
    disabled,
    placeholder,
    helpText,
    hasError,
    ...rest
  } = props;
  const hasHelpText = !!helpText;

  const handleBlur = () => {
    setIsFocused(false);
  };

  const handleFocus = () => {
    setIsFocused(true);
  };

  return (
    <InputContainer $size={size} $disabled={disabled} $hasError={hasError}>
      {labelName && isFocused && <Label $size={size}>{labelName}</Label>}
      <StyledTextInput
        type={type ? type : 'text'}
        id={id}
        name={name}
        placeholder={placeholder}
        disabled={disabled}
        onFocus={handleFocus}
        onBlur={handleBlur}
        {...rest}></StyledTextInput>
      {hasHelpText && (
        <Caption $isFocused={isFocused} $hasError={hasError}>
          {helpText}
        </Caption>
      )}
    </InputContainer>
  );
}

type InputContainerProps = {
  $size: 'tall' | 'short';
  $disabled?: boolean;
  $hasError?: boolean;
};

const InputContainer = styled.fieldset<InputContainerProps>`
  height: ${({ $size }) => ($size === 'short' ? '40px' : '56px')};
  padding: 0 16px;
  display: flex;
  flex-direction: ${({ $size }) => ($size === 'short' ? 'row' : 'column')};
  justify-content: ${({ $size }) =>
    $size === 'short' ? 'flex-start' : 'center'};
  align-items: ${({ $size }) => ($size === 'short' ? 'center' : 'flex-start')};
  gap: 4px;
  position: relative;
  border: 1px solid
    ${({ theme, $hasError }) =>
      $hasError ? theme.color.danger.text.default : 'transparent'};
  border-radius: ${({ theme, $size }) =>
    $size === 'short'
      ? theme.objectStyles.radius.medium
      : theme.objectStyles.radius.large};
  background: ${({ theme }) => theme.color.neutral.surface.bold};
  color: ${({ theme }) => theme.color.neutral.text.weak};

  input {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
  }

  &:focus-within {
    border: 1px solid ${({ theme }) => theme.color.neutral.border.active};
    background: ${({ theme }) => theme.color.neutral.surface.strong};
  }

  ${({ theme, $disabled }) =>
    $disabled &&
    `
      opacity: ${theme.objectStyles.opacity.disabled};
    `}
`;

const Label = styled.label<{ $size: 'tall' | 'short' }>`
  width: ${({ $size }) => ($size === 'short' ? '64px' : 'auto')};
  ${({ theme }) => theme.font.display.medium[12]}
`;

const StyledTextInput = styled.input`
  height: inherit;
  padding: 0;
  border: none;
  outline: none;
  background: transparent;
  caret-color: ${({ theme }) => theme.color.palette.blue};
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]}

  &:focus {
    color: ${({ theme }) => theme.color.neutral.text.strong};
  }
`;

const Caption = styled.span<{ $isFocused: boolean; $hasError?: boolean }>`
  position: absolute;
  bottom: -18px;
  left: 16px;
  color: ${({ theme, $isFocused, $hasError }) =>
    !$isFocused && $hasError
      ? theme.color.danger.text.default
      : theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[12]}
`;
