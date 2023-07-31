import { useState } from 'react';
import { styled } from 'styled-components';

type TextInputProps = React.HTMLAttributes<HTMLInputElement> & {
  size: 'tall' | 'short';
  labelName: string;
  disabled?: boolean;
  placeholder?: string;
  helpText?: string;
  validationFunc?: (value: string) => boolean;
};

export default function TextInput(props: TextInputProps) {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [isError, setIsError] = useState<boolean>(false);
  const [inputValue, setInputValue] = useState<string>('');

  const { size, labelName, disabled, placeholder, helpText, validationFunc } =
    props;
  const hasHelpText = !!helpText;

  const handleBlur = () => {
    setIsFocused(false);

    if (validationFunc && !validationFunc(inputValue) && inputValue) {
      setIsError(true);
    } else {
      setIsError(false);
    }
  };

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  return (
    <InputContainer $size={size} $disabled={disabled} $hasError={isError}>
      {(isFocused || inputValue) && <Label $size={size}>{labelName}</Label>}
      <StyledTextInput
        type="text"
        placeholder={placeholder}
        value={inputValue}
        disabled={disabled}
        onFocus={handleFocus}
        onBlur={handleBlur}
        onChange={handleChange}></StyledTextInput>
      {hasHelpText && <Caption $hasError={isError}>{helpText}</Caption>}
    </InputContainer>
  );
}

type InputContainerProps = {
  $size: 'tall' | 'short';
  $focused?: boolean;
  $disabled?: boolean;
  $hasError?: boolean;
};

const InputContainer = styled.fieldset<InputContainerProps>`
  width: 288px;
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
      $hasError
        ? theme.color.danger.text.default
        : theme.color.neutral.border.default};
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

const StyledTextInput = styled.input<{ $focused?: boolean }>`
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

const Caption = styled.span<{ $hasError: boolean }>`
  position: absolute;
  bottom: -18px;
  left: 16px;
  color: ${({ theme, $hasError }) =>
    $hasError
      ? theme.color.danger.text.default
      : theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[12]}
`;
