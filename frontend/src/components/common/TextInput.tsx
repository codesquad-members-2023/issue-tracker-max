import { styled } from 'styled-components';

type TextInputProps = React.HTMLAttributes<HTMLInputElement> & {
  labelName: string;
  placeholder?: string;
  helpText?: string;
};

export default function TextInput(props: TextInputProps) {
  const { labelName, placeholder, helpText } = props;

  const hasHelpText = !!helpText;

  return (
    <InputContainer>
      <Label>{labelName}</Label>
      <StyledTextInput type="text" placeholder={placeholder}></StyledTextInput>
      {hasHelpText && <Caption>{helpText}</Caption>}
    </InputContainer>
  );
}

const InputContainer = styled.div`
  width: 288px;
  height: 56px;
  padding: 0 16px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  gap: 4px;
  position: relative;
  border: none;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  background: ${({ theme }) => theme.color.neutral.surface.bold};
  color: ${({ theme }) => theme.color.neutral.text.weak};

  input {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
  }
`;

const Label = styled.label`
  ${({ theme }) => theme.font.display.medium[12]}
`;

const StyledTextInput = styled.input`
  padding: 0;
  border: none;
  outline: none;
  background: transparent;
  caret-color: ${({ theme }) => theme.color.palette.blue};
  color: ${({ theme }) => theme.color.neutral.text.strong};
  ${({ theme }) => theme.font.display.medium[16]}
`;

const Caption = styled.span`
  position: absolute;
  top: 58px;
  left: 16px;
  ${({ theme }) => theme.font.display.medium[12]}
`;
