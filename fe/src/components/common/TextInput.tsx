import { useState } from "react";
// import { color } from "../../constants/colors";
// import { fonts } from "../util/Txt";
import { ColorScheme } from "../../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { fonts } from "../../constants/fonts";

type InputState = "enabled" | "active" | "disabled" | "error";

const textInputContainer = (
  color: ColorScheme,
  inputState: string,
  height: number
) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: start;
  position: relative;
  width: 320px;
  height: ${height}px;
  border: ${inputState === "active"
    ? `1px solid ${color.neutral.border.defaultActive}`
    : inputState === "error"
    ? `1px solid ${color.danger.border.default}`
    : "none"};
  padding: 0 16px;
  border-radius: 16px;
  box-sizing: border-box;
  background-color: ${inputState === "enabled"
    ? color.neutral.surface.bold
    : inputState === "disabled"
    ? color.neutral.surface.bold
    : color.neutral.surface.strong};
`;

const input = (color: ColorScheme, inputState: string) => css`
  ...${fonts.medium16};
  outline: none;
  width: 288px;
  height: 24px;
  color: ${
    inputState === "enabled"
      ? color.neutral.text.default
      : inputState === "disabled"
      ? color.neutral.text.weak
      : color.neutral.text.strong
  };
  border: none;
  background-color: transparent;
`;

export function TextInput({
  isPassword,
  height,
  placeholder,
  onChange,
}: {
  isPassword?: boolean;
  height: number;
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}) {
  const [inputState, setInputState] = useState<InputState>("enabled");

  const color = useTheme() as ColorScheme;

  const onFocus = () => {
    setInputState("active");
  };

  const onBlur = () => {
    setInputState("enabled");
  };

  return (
    <div css={textInputContainer(color, inputState, height)}>
      {inputState === "active" && (
        <div
          css={{
            ...fonts.medium12,
            color: color.neutral.text.weak,
          }}>
          {placeholder}
        </div>
      )}
      <input
        onFocus={onFocus}
        onBlur={onBlur}
        onChange={onChange}
        placeholder={placeholder}
        css={input(color, inputState)}
        type={isPassword ? "password" : "text"}
      />
    </div>
  );
}
