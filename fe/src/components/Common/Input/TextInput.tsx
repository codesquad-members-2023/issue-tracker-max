import React, { InputHTMLAttributes, useState } from "react";
import styled, { DefaultTheme } from "styled-components";
import { Icon } from "components/Common/Icon/Icon";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  caption?: string;
  $labelText?: string;
  showLabel?: boolean;
  showCaption?: boolean;
  $heightSize?: "S" | "M";
  $state?: "enabled" | "active" | "disabled" | "error";
  placeholder?: string;
  helperText?: string;
  value?: string;
  onValueChange?: (value: string) => void;
}

export const TextInput: React.FC<InputProps> = ({
  $heightSize = "M",
  $state = "enabled",
  placeholder,
  helperText,
  $labelText,
  value = "",
  onValueChange,
}) => {
  const [inputValue, setInputValue] = useState(value);
  const [currentState, setCurrentState] = useState($state);
  const [typingState, setTypingState] = useState<
    "placeholder" | "onFocus" | "onTyping" | "typed"
  >("placeholder");

  const handleClear = () => {
    setInputValue("");
    setTypingState("placeholder");
    setCurrentState("enabled");
  };

  const handleFocus = () => {
    setCurrentState("active");
    setTypingState("onFocus");
  };

  const handleBlur = () => {
    if (inputValue) {
      setCurrentState("enabled");
      setTypingState("typed");
    } else {
      setCurrentState("enabled");
      setTypingState("placeholder");
    }
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
    if (inputValue) {
      onValueChange?.(event.target.value);
      setCurrentState("active");
      setTypingState("onTyping");
    } else {
      setTypingState("onFocus");
    }
  };

  return (
    <TextInputLayout>
      <InputLayout $heightSize={$heightSize} $state={currentState}>
        <InputArea>
          {$labelText && (
            <LabelText
              $heightSize={$heightSize}
              $typingState={typingState}
              $labelText={$labelText}
            >
              {$labelText}
            </LabelText>
          )}
          <Input
            onFocus={handleFocus}
            onBlur={handleBlur}
            onChange={handleChange}
            value={inputValue}
            placeholder={placeholder}
            disabled={$state === "disabled"}
            $typingState={typingState}
            $labelText={$labelText}
            $heightSize={$heightSize}
          />
        </InputArea>
        {inputValue && (
          <ClearButton onClick={handleClear}>
            <Icon icon="XSquare" stroke="nuetralTextWeak" />
          </ClearButton>
        )}
      </InputLayout>
      {$state === "error" && <HelperText>{helperText}</HelperText>}
    </TextInputLayout>
  );
};

const $stateStyle = (theme: DefaultTheme) => ({
  enabled: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceBold};
    border-color: ${theme.color.nuetralSurfaceBold};
  `,
  active: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceStrong};
    border-color: ${theme.color.nuetralBorderDefaultActive};
  `,
  disabled: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceBold};
    border-color: ${theme.color.nuetralSurfaceBold};
  `,
  error: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceStrong};
    border: ${theme.border.default};
    border-color: ${theme.color.dangerborderDefault};
  `,
});

const TextInputLayout = styled.div`
  width: 100%;
`;
const InputLayout = styled.div<{
  $heightSize: "S" | "M";
  $state: "enabled" | "active" | "disabled" | "error";
}>`
  ${({ theme, $state }) => $stateStyle(theme)[$state]}
  position: relative;
  flex-grow: 1;
  font: ${({ theme: { font } }) => font.displayM16};
  height: ${({ $heightSize }) => ($heightSize === "S" ? "40px" : "56px")};
  display: ${({ $heightSize }) => ($heightSize === "S" ? "flex" : "")};
  align-items: ${({ $heightSize }) => ($heightSize === "S" ? "center" : "")};
  padding: 0 16px;
  display: ${({ $heightSize }) => ($heightSize === "S" ? "" : "flex")};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const LabelText = styled.label<{
  $heightSize: "S" | "M";
  $typingState: string;
  $labelText?: string;
}>`
  pointer-events: none;
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  position: ${({ $heightSize }) =>
    $heightSize === "S" ? "static" : "absolute"};
  width: ${({ $heightSize }) => ($heightSize === "S" ? "64px" : "240px")};
  transform: ${({ $heightSize, $labelText, $typingState }) =>
    $heightSize === "M" && $labelText && $typingState !== "placeholder"
      ? "translate(0, -10px) scale(0.8)"
      : "translate(0, 0px) scale(1)"};
  left: 18px;
  transform-origin: top left;
  transition: 200ms cubic-bezier(0, 0, 0.2, 1) 0ms;
`;

const InputArea = styled.div`
  display: flex;
  align-items: center;
  width: 100%;

  :focus-within label {
    transform: translate(0, 12px) scale(0.8);
  }
`;

const Input = styled.input<{
  $typingState: "placeholder" | "onFocus" | "onTyping" | "typed";
  $labelText?: string;
  $heightSize: "S" | "M";
}>`
  border: none;
  position: relative;
  background-color: transparent;
  outline: none;
  width: 100%;
  position: relative;
  font: ${({ theme: { font } }) => font.displayM16};
  color: ${({ $typingState, theme: { color } }) =>
    $typingState === "typed"
      ? color.nuetralTextWeak
      : color.nuetralTextDefault};
  ${({ $heightSize, $labelText, $typingState }) =>
    $heightSize === "M" && $labelText && $typingState !== "placeholder"
      ? `position: relative; top: 8px;`
      : `position: static;`}
`;

const ClearButton = styled.button``;

const HelperText = styled.p`
  padding-left: 16px;
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.dangerTextDefault};
  margin: 4px 0;
`;
