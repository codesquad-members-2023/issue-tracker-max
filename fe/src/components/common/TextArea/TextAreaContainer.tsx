import styled from "styled-components";
import FileUploadArea from "./FileUploadArea";
import TextAreaWrapper from "./TextAreaWrapper";

export default function TextAreaContainer({
  value,
  appendContent,
  ...props
}: {
  value: string;
  appendContent: (content: string) => void;
} & React.TextareaHTMLAttributes<HTMLTextAreaElement>) {
  return (
    <StyledTextAreaContainer>
      <TextAreaWrapper {...{ value, ...props }} />
      <FileUploadArea {...{ appendContent }} />
    </StyledTextAreaContainer>
  );
}

const StyledTextAreaContainer = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  justify-content: "center";
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  border-radius: ${({ theme: { radius } }) => `${radius.l}`};
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  &:focus-within {
    border: ${({ theme: { neutral, border } }) =>
      `${border.default} ${neutral.border.defaultActive}`};
    background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  }
  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;
