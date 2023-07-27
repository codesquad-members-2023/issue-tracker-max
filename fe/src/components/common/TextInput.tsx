import { useState } from "react";
import styled from "styled-components";

type TextInputProps = {
  name: string;
  variant: "tall" | "short";
  placeholderText?: string;
  hasError?: boolean;
  helpText?: string;
};

export default function TextInput(props: TextInputProps) {
  const { name, variant, placeholderText, hasError, helpText } = props;

  const [content, setContent] = useState("");
  const [isFocused, setIsFocused] = useState(false);

  const isTallType = variant === "tall";
  const textInputState = isFocused ? "active" : hasError ? "error" : "enabled";
  const typingState = isFocused ? "onTyping" : "typed";

  const onInputChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    setContent(e.target.value);
  };

  return (
    <StyledTextInput>
      <InputContainer $variant={variant} $state={textInputState}>
        {isTallType && content && <Label htmlFor={name}>{name}</Label>}
        {!isTallType && <Label htmlFor={name}>{name}</Label>}
        <Input
          id={name}
          type="text"
          value={content}
          $typingState={typingState}
          placeholder={placeholderText || name}
          onChange={onInputChanged}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
        />
      </InputContainer>
      {helpText && (
        <HelpTextArea $state={textInputState}>{helpText}</HelpTextArea>
      )}
    </StyledTextInput>
  );
}

const INPUT_HEIGHT = {
  tall: 56,
  short: 40,
};

const StyledTextInput = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

const InputContainer = styled.div<{
  $variant: "tall" | "short";
  $state: "enabled" | "active" | "disabled" | "error";
}>`
  display: flex;
  flex-direction: ${({ $variant }) => ($variant === "tall" ? "column" : "row")};
  width: 100%;
  justify-content: ${({ $variant }) =>
    $variant === "tall" ? "center" : "space-between"};
  padding: 0px 16px;
  gap: 8px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  height: ${({ $variant }) => INPUT_HEIGHT[$variant]}px;
  border: ${({ $state, theme: { neutral, border, danger } }) => {
    const type = {
      active: `${border.default} ${neutral.border.defaultActive}`,
      error: `${border.default} ${danger.border.default}`,
      disabled: "none",
      enabled: "none",
    };
    return type[$state];
  }};
  border-radius: ${({ $variant, theme: { radius } }) =>
    $variant === "tall" ? `${radius.l}` : `${radius.m}`};
  background-color: ${({ $state, theme: { neutral } }) => {
    const type = {
      active: neutral.surface.strong,
      error: neutral.surface.strong,
      disabled: neutral.surface.bold,
      enabled: neutral.surface.bold,
    };
    return type[$state];
  }};
  opacity: ${({ $state, theme: { opacity } }) =>
    $state === "disabled" && opacity.disabled};
`;

const Label = styled.label`
  display: flex;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayMD12};
`;

const Input = styled.input<{
  $typingState: "onTyping" | "typed";
}>`
  display: flex;
  width: 80%;
  color: ${({ $typingState, theme: { neutral } }) => {
    const type = {
      onTyping: neutral.text.strong,
      typed: neutral.text.default,
    };
    return type[$typingState];
  }};
  font: ${({ theme: { font } }) => font.displayMD16};
  caret-color: ${({ theme: { palette } }) => palette.blue};
  &::placeholder {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const HelpTextArea = styled.span<{
  $state: "enabled" | "active" | "disabled" | "error";
}>`
  display: flex;
  color: ${({ $state, theme: { neutral, danger } }) => {
    const textColor = {
      active: neutral.text.weak,
      error: danger.text.default,
      disabled: neutral.text.weak,
      enabled: neutral.text.weak,
    };
    return textColor[$state];
  }};
  font: ${({ theme: { font } }) => font.displayMD12};
  padding: 0px 16px;
`;
