import { styled } from "styled-components";
import Input from "../Input/Input";
import { useState } from "react";

type Props = {
  id: string;
  direction?: "row" | "column";
  size?: "large" | "small";
  label: string;
  caption?: string;
  captionType?: "normal" | "error";
};

export default function TextInput({
  id,
  direction = "column",
  size = "large",
  label,
  caption,
  captionType = "normal",
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
        {isFocus && <Label htmlFor={id}>{label}</Label>}
        <Input
          id={id}
          placeholder={label}
          handleFocus={handleFocus}
          handleBlur={handleBlur}
        />
      </InputWrapper>
      {caption && <Caption $captionType={captionType}>{caption}</Caption>}
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

const Label = styled.label`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Caption = styled.p<{ $captionType: "normal" | "error" }>`
  width: 100%;
  padding-left: 16px;
`;
