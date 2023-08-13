import { styled } from "styled-components";
import Input from "../Input/Input";
import { useState } from "react";

type Props = {
  id: string;
  inputType?: "text" | "password";
  direction?: "row" | "column";
  size?: "large" | "small";
  label: string;
  hasCaption?: boolean;
  caption?: string;
  captionType?: "normal" | "error";
  onChange?(e: React.ChangeEvent<HTMLInputElement>): void;
};

export default function TextInput({
  id,
  inputType,
  direction = "column",
  size = "large",
  label,
  hasCaption = false,
  caption,
  captionType = "normal",
  onChange,
}: Props) {
  const [isFocus, setIsFocus] = useState<boolean>(false);

  const handleFocus = () => {
    setIsFocus(true);
  };

  const handleBlur = () => {
    setIsFocus(false);
  };

  return (
    <Container>
      <InputWrapper isFocus={isFocus} $size={size} $direction={direction}>
        {isFocus && (
          <Label htmlFor={id} $direction={direction}>
            {label}
          </Label>
        )}
        <Input
          id={id}
          inputType={inputType}
          placeholder={label}
          handleFocus={handleFocus}
          handleBlur={handleBlur}
          onChange={onChange}
        />
      </InputWrapper>
      {hasCaption && <Caption $captionType={captionType}>{caption}</Caption>}
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
  width: 100%;
`;

const InputWrapper = styled.div<{
  isFocus: boolean;
  $size: string;
  $direction: "row" | "column";
}>`
  padding: 8px 16px;
  display: flex;
  justify-content: center;
  align-items: ${({ $direction }) => ($direction === "row" ? "center" : "")};
  flex-direction: ${({ $direction }) =>
    $direction === "column" ? "column" : "row"};
  width: 100%;
  height: ${({ $size }) => ($size === "large" ? "56px" : "40px")};
  background-color: ${({ isFocus, theme }) =>
    isFocus
      ? theme.colorSystem.neutral.surface.strong
      : theme.colorSystem.neutral.surface.bold};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const Label = styled.label<{ $direction: "row" | "column" }>`
  width: ${({ $direction }) => ($direction === "row" ? "64px" : "")};
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Caption = styled.p<{ $captionType: "normal" | "error" }>`
  width: 100%;
  padding-left: 16px;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ $captionType, theme }) =>
    $captionType === "normal"
      ? theme.colorSystem.neutral.text.weak
      : theme.colorSystem.danger.text.default};
`;
