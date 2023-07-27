import { useEffect, useState } from "react";
import styled from "styled-components";

type TextInputProps = {
  width?: number;
  size: "L" | "S";
  value?: string;
  label: string;
  caption?: string;
  disabled?: boolean;
  validator?: (value: string) => boolean;
};

type TextInputState = "Enabled" | "Active" | "Disabled" | "Error";

type InputProps = {
  $state: TextInputState;
};

type InputContainerProps = {
  $width: number;
  $size: "L" | "S";
  $state: TextInputState;
};

export function TextInput({
  width = 254,
  size,
  value: initialValue = "",
  label,
  caption,
  disabled = false,
  validator,
}: TextInputProps) {
  const [state, setState] = useState<TextInputState>(
    disabled ? "Disabled" : "Enabled",
  );
  const [inputValue, setInputValue] = useState(initialValue);

  useEffect(() => {
    if (validator) {
      setState(validator(inputValue) ? "Enabled" : "Error");
    }
  }, [inputValue, validator]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleInputFocus = () => {
    if (state !== "Error") {
      setState("Active");
    }
  };

  const handleInputBlur = () => {
    if (state !== "Error") {
      setState(disabled ? "Disabled" : "Enabled");
    }
  };

  return (
    <Div $state={state}>
      <InputContainer $width={width} $size={size} $state={state}>
        {inputValue && <StyledSpan>{label}</StyledSpan>}
        <Input
          placeholder={label}
          value={inputValue}
          onChange={handleInputChange}
          onFocus={handleInputFocus}
          onBlur={handleInputBlur}
          disabled={state === "Disabled"}
          $state={state}
        />
      </InputContainer>
      {caption && <Caption $state={state}>{caption}</Caption>}
    </Div>
  );
}

const Div = styled.div<{ $state: TextInputState }>`
  display: flex;
  width: 288px;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
  flex-shrink: 0;
  opacity: ${(props) => (props.$state === "Disabled" ? 0.2 : 1)};
  background: ${({ theme: { color } }) => color.grayScale50};
`;

const InputContainer = styled.div<InputContainerProps>`
  display: flex;
  width: ${({ $width }) => $width}px;
  height: ${({ $size }) => ($size === "L" ? "56px" : "40px")};
  padding: 0px 16px;
  justify-content: center;
  align-self: stretch;
  flex-direction: ${({ $size }) => ($size === "L" ? "column" : "")};
  align-items: ${({ $size }) => ($size === "L" ? "flex-start" : "center")};
  border-radius: ${({ theme }) => theme.radius.large};
  box-sizing: border-box;
  background: ${({ $state, theme }) => {
    switch ($state) {
      case "Enabled":
        return theme.color.neutralSurfaceBold;
      case "Active":
      case "Error":
        return theme.color.neutralSurfaceStrong;
      default:
        return theme.color.neutralSurfaceBold;
    }
  }};
  border: ${({ theme, $state }) => {
    switch ($state) {
      case "Active":
        return `1px solid ${theme.color.neutralBorderDefaultActive}`;
      case "Error":
        return `1px solid ${theme.color.dangerBorderDefault}`;
      default:
        return "";
    }
  }};
`;

const StyledSpan = styled.span`
  width: 64px;
  color: ${({ theme }) => theme.color.neutralTextWeak};
  font: ${({ theme }) => theme.font.displayMedium12};
`;

const Input = styled.input<InputProps>`
  overflow: hidden;
  text-overflow: ellipsis;
  border: none;
  &:focus {
    outline: none;
  }
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme, $state }) => {
    switch ($state) {
      case "Enabled":
      case "Disabled":
      case "Error":
        return theme.color.neutralTextDefault;
      case "Active":
        return theme.color.neutralTextStrong;
      default:
        return theme.color.neutralTextDefault;
    }
  }};
  background: none;
  &::placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &:-ms-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &::-ms-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &::-webkit-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;

const Caption = styled.span<{ $state: TextInputState }>`
  display: flex;
  padding-left: 0px;
  align-items: flex-start;
  align-self: stretch;
  padding-left: 16px;
  color: ${({ theme, $state }) =>
    $state === "Error"
      ? theme.color.dangerTextDefault
      : theme.color.neutralTextWeak};
  font: ${({ theme }) => theme.font.displayMedium12};
`;
