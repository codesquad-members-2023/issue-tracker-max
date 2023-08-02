import React, { InputHTMLAttributes, useState } from "react";
import styled from "styled-components";
import { Icon } from "components/Icon/Icon";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  type?: "S" | "M";
  state?: "Enabled" | "Active" | "Disabled" | "Error";
  helpText?: string;
  $labelText?: string;
  placehoder?: string;
}

interface LabelProps extends InputProps {
  $active?: boolean;
  $value?: string;
}

export const Input: React.FC<InputProps> = ({
  type = "M",
  state = "Enabled",
  $labelText,
  placeholder,
  helpText,
  ...props
}) => {
  const [value, setValue] = useState("");
  const [isActive, setIsActive] = useState(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const handleClear = () => {
    setValue("");
  };

  const handleFocus = () => {
    setIsActive(true);
  };

  const handleBlur = () => {
    setIsActive(false);
  };

  return (
    <>
      <InputLayout
        $active={isActive}
        disabled={state === "Disabled"}
        state={state}
      >
        <InputWrapper>
          {$labelText && (
            <StyledLabel $active={isActive} $value={value}>
              {$labelText}
            </StyledLabel>
          )}
          <StyledInput
            value={value}
            placeholder={placeholder}
            onChange={handleChange}
            onFocus={handleFocus}
            onBlur={handleBlur}
            disabled={state === "Disabled"}
            type={type}
            state={state}
            $labelText={$labelText}
            {...props}
          />
        </InputWrapper>
        {value && (
          <ClearButton onClick={handleClear}>
            <Icon icon="XSquare" />
          </ClearButton>
        )}
      </InputLayout>
      {state === "Error" && <HelperText>{helpText}</HelperText>}
    </>
  );
};

const InputLayout = styled.div<InputProps & { $active?: boolean }>`
  :focus-within label {
    transform: translate(0, 12px) scale(0.8);
    color: ${({ theme: { color } }) => color.paletteBlue};
  }
  background-color: ${({ state, theme: { color } }) =>
    state === "Disabled"
      ? color.nuetralSurfaceBold
      : color.nuetralSurfaceStrong};
  position: relative;
  width: 100%;
  display: flex;
  border: 1px solid;
  border-radius: ${({ theme: { radius } }) => radius.large};
  border-color: ${({ state, $active, theme: { color } }) => {
    if ($active) {
      return color.nuetralBorderDefaultActive;
    }
    switch (state) {
      case "Disabled":
        return color.nuetralSurfaceBold;
      case "Error":
        return color.dangerborderDefault;
      default:
        return color.nuetralSurfaceBold;
    }
  }};
`;

const InputWrapper = styled.div<InputProps & { $active?: boolean }>`
  :focus-within label {
    transform: translate(0, 12px) scale(0.8);
    color: #0a53e4;
  }
  overflow: hidden;
  position: relative;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: ${({ theme: { radius } }) => radius.large};
  border-color: ${({ state, $active, theme: { color } }) => {
    if ($active) {
      return color.nuetralBorderDefaultActive;
    }
    switch (state) {
      case "Disabled":
        return color.nuetralSurfaceBold;
      case "Error":
        return color.dangerborderDefault;
      default:
        return color.nuetralSurfaceBold;
    }
  }};
`;

const StyledLabel = styled.label<LabelProps>`
  position: absolute;
  pointer-events: none;
  transform: ${({ $active, $value }) =>
    $active || $value !== ""
      ? "translate(0, 12px) scale(0.8)"
      : "translate(0, 23px) scale(1)"};
  transform-origin: top left;
  transition: 200ms cubic-bezier(0, 0, 0.2, 1) 0ms;
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  color: ${({ $active, $value, theme: color }) =>
    $active || $value !== "" ? color.nuetralTextWeak : color.paletteBlue};
  font-size: 16px;
  font: ${({ theme: { font } }) => font.displayM12};
  line-height: 1;
  left: 16px;
  z-index: 10;
`;

const StyledInput = styled.input<InputProps & { $active?: boolean }>`
  width: 100%;
  padding: 0px 16px;
  border: none;
  outline: none;
  font: ${({ theme: { font } }) => font.displayM16};
  height: ${({ type }) => (type === "M" ? "56px" : "40px")};
  position: ${({ $labelText }) => ($labelText ? "relative" : "static")};
  top: ${({ $active, $labelText }) => ($labelText && !$active ? "4px" : "0")};
  border-radius: ${({ theme: { radius } }) => radius.large};
  background-color: ${({ state, theme: { color } }) =>
    state === "Disabled"
      ? color.nuetralSurfaceBold
      : color.nuetralSurfaceStrong};
  color: ${({ state, theme: { color } }) =>
    state === "Disabled" ? color.nuetralTextWeak : color.nuetralTextStrong};
  ${({ state }) => state === "Disabled" && "cursor: not-allowed;"}
`;

const ClearButton = styled.button`
  background: none;
  border-radius: ${({ theme: { radius } }) => radius.large};
  border: none;
  cursor: pointer;
  padding-right: 16px;
  background-color: none;
`;

const HelperText = styled.span`
  padding: 0 16px;
  font: ${({ theme: { font } }) => font.displayM12};
  color: ${({ theme: { color } }) => color.dangerTextDefault};
  margin: 4px 0;
  display: block;
`;
