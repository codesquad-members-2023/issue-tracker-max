import { useState } from 'react';
import { styled } from 'styled-components';

type TextInputProps = React.HTMLAttributes<HTMLInputElement> & {
  size: 'tall' | 'short';
  labelName: string;
  disabled?: boolean;
  placeholder?: string;
  helpText?: string;
};

export default function TextInput(props: TextInputProps) {
  const [focused, setFocused] = useState<boolean>(false);
  const [inputValue, setInputValue] = useState<string>('');

  const { size, labelName, disabled, placeholder, helpText } = props;
  const hasHelpText = !!helpText;

  const handleBlur = () => {
    setFocused(false);
  };

  const handleFocus = () => {
    setFocused(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  return (
    <InputContainer $size={size} $disabled={disabled}>
      {(focused || inputValue) && <Label $size={size}>{labelName}</Label>}
      <StyledTextInput
        type="text"
        placeholder={placeholder}
        value={inputValue}
        disabled={disabled}
        onFocus={handleFocus}
        onBlur={handleBlur}
        onChange={handleChange}></StyledTextInput>
      {hasHelpText && <Caption>{helpText}</Caption>}
    </InputContainer>
  );
}

type InputContainerProps = {
  $size: 'tall' | 'short';
  $focused?: boolean;
  $disabled?: boolean;
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
  border: 1px solid transparent;
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

type LabelProps = {
  $size: 'tall' | 'short';
};

const Label = styled.label<LabelProps>`
  width: ${({ $size }) => ($size === 'short' ? '64px' : 'auto')};
  ${({ theme }) => theme.font.display.medium[12]}
`;

type StyledTextInputProps = {
  $focused?: boolean;
};

const StyledTextInput = styled.input<StyledTextInputProps>`
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

const Caption = styled.span`
  position: absolute;
  bottom: -18px;
  left: 16px;
  ${({ theme }) => theme.font.display.medium[12]}
`;
