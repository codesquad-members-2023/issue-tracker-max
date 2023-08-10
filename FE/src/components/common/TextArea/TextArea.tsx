import { useEffect, useState } from "react";
import { styled } from "styled-components";

type Props = {
  type?: "small" | "large";
  inputValue: string;
  onChange(e: React.ChangeEvent<HTMLTextAreaElement>): void;
};

export default function TextArea({
  type = "large",
  inputValue,
  onChange,
}: Props) {
  const [isFocus, setIsFocus] = useState<boolean>(false);
  const [showCounter, setShowCounter] = useState<boolean>(false);

  const onFucusInput = () => {
    setIsFocus(true);
  };

  const onBlurInput = () => {
    setIsFocus(false);
  };

  useEffect(() => {
    let timer: NodeJS.Timeout | null = null;

    if (inputValue) {
      setShowCounter(true);
      timer = setTimeout(() => {
        setShowCounter(false);
      }, 2000);
    }

    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [inputValue]);

  return (
    <Container $isFocus={isFocus}>
      <InputWrapper $type={type}>
        {inputValue && <Label htmlFor={"textarea"}>코멘트를 입력하세요</Label>}
        <Text
          id={"textarea"}
          placeholder={"코멘트를 입력하세요"}
          onFocus={onFucusInput}
          onBlur={onBlurInput}
          value={inputValue}
          onChange={onChange}
          $type={type}
        ></Text>
      </InputWrapper>
      <CountWrapper>
        {showCounter && <Counter>띄어쓰기 포함 {inputValue.length}자</Counter>}
        <IconImg src={"/icons/grip.svg"} />
      </CountWrapper>
      <AddFileWrapper>
        <AddFileInput type={"file"} />
      </AddFileWrapper>
    </Container>
  );
}

const Container = styled.div<{ $isFocus: boolean }>`
  width: 100%;
  height: min-content;
  border: ${({ $isFocus, theme }) =>
    $isFocus
      ? `${theme.border.default} ${theme.colorSystem.neutral.border.defaultActive}`
      : "none"};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ $isFocus, theme }) =>
    $isFocus
      ? theme.colorSystem.neutral.surface.strong
      : theme.colorSystem.neutral.surface.bold};
`;

const Label = styled.label`
  width: 100%;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Text = styled.textarea<{ $type: "small" | "large" }>`
  width: 100%;
  height: ${({ $type }) => ($type === "large" ? "410px" : "64px")};
  vertical-align: top;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
  background-color: transparent;
  resize: none;
  border: none;
  outline: none;
  &::placeholder {
    color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  }
  &:-moz-placeholder {
    color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  }
`;

const CountWrapper = styled.div`
  display: flex;
  justify-content: end;
  align-items: center;
  gap: 8px;
  width: 100%;
  height: 52px;
  padding: 16px;
`;

const Counter = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const IconImg = styled.img`
  width: 20px;
  height: 20px;
`;

const AddFileWrapper = styled.div`
  padding: 10px 16px;
  display: flex;
  align-items: center;
  width: 100%;
  height: 52px;
  border-top: ${({ theme }) =>
    `${theme.border.dash} ${theme.colorSystem.neutral.border.default}`};
`;

const AddFileInput = styled.input``;

const InputWrapper = styled.div<{ $type: "small" | "large" }>`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  height: ${({ $type }) => ($type === "large" ? "450px" : "84px")};
  padding: 16px 16px 0px 16px;
`;
