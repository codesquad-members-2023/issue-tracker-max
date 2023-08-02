import {
  ChangeEvent,
  TextareaHTMLAttributes,
  useEffect,
  useState,
} from "react";
import { styled, useTheme } from "styled-components";
import { Icon } from "./Icon";

type TextAreaProps = {
  value?: string;
  label?: string;
} & TextareaHTMLAttributes<HTMLTextAreaElement>;

type TextAreaState = "Active" | "Enabled" | "Disabled";

export function TextArea({
  value = "",
  label,
  disabled,
  placeholder,
}: TextAreaProps) {
  const [state, setState] = useState<TextAreaState>(
    disabled ? "Disabled" : "Enabled",
  );
  const [inputValue, setInputValue] = useState(value);
  const [showCount, setShowCount] = useState(false);

  useEffect(() => {
    let timer: ReturnType<typeof setTimeout>;

    if (showCount) {
      timer = setTimeout(() => {
        setShowCount(false);
      }, 2000);
    }

    return () => clearTimeout(timer);
  }, [showCount]);

  const onFocus = () => {
    setState("Active");
  };

  const onBlur = () => {
    setState("Enabled");
  };

  const onTextChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setInputValue(e.target.value);
    setShowCount(true);
  };

  const onFileInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    console.log("upload file", file);
  };

  const theme = useTheme();
  const iconColor = theme.color.neutralTextDefault;

  return (
    <Div $state={state} onFocus={onFocus} onBlur={onBlur}>
      <InputContainer>
        {inputValue && label && <Label>{label}</Label>}
        <TextInput
          placeholder={placeholder}
          value={inputValue}
          onChange={onTextChange}
          disabled={disabled}
        />
        {showCount && (
          <TextCount>띄어쓰기 포함 {inputValue.length}글자</TextCount>
        )}
      </InputContainer>
      <Footer>
        <UploadButton htmlFor="imageUpload">
          <Icon name="paperclip" fill={iconColor} stroke={iconColor} />
          <span>파일 첨부하기</span>
          <input
            id="imageUpload"
            type="file"
            accept="image/*"
            onChange={onFileInputChange}
          />
        </UploadButton>
      </Footer>
    </Div>
  );
}

const Div = styled.div<{ $state: TextAreaState }>`
  display: flex;
  min-width: 340px;
  min-height: 184px;
  flex-direction: column;
  align-items: flex-start;
  flex-shrink: 0;
  border: ${({ theme, $state }) =>
    `${theme.border.default} ${
      $state === "Active"
        ? theme.color.neutralBorderDefaultActive
        : "transparent"
    }`};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme, $state }) =>
    $state === "Active"
      ? theme.color.neutralSurfaceStrong
      : theme.color.neutralSurfaceBold};
  opacity: ${({ theme, $state }) =>
    $state === "Disabled" ? theme.opacity.disabled : theme.opacity.default};
  pointer-events: ${({ $state }) => $state === "Disabled" && "none"};
`;

const InputContainer = styled.div`
  align-self: stretch;
  flex-grow: 1;
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 16px;
  border-bottom: ${({ theme }) =>
    `${theme.border.dash} ${theme.color.neutralBorderDefault}`};
`;

const Label = styled.span`
  flex-shrink: 0;
  display: inline-block;
  width: 100%;
  color: ${({ theme }) => theme.color.neutralTextWeak};
  font: ${({ theme }) => theme.font.displayMedium12};
`;

const TextInput = styled.textarea`
  box-sizing: border-box;
  flex-grow: 1;
  width: 100%;
  min-width: 100%;
  border: none;
  background: none;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextWeak};
  overflow: auto;

  &::placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }

  &:focus {
    outline: none;
  }
`;

const TextCount = styled.span`
  position: absolute;
  bottom: 16px;
  right: 32px;
  font: ${({ theme }) => theme.font.displayMedium12};
`;

const Footer = styled.div`
  flex-shrink: 0;
  display: flex;
  height: 52px;
  padding: 0px 16px;
  align-items: stretch;
  gap: 8px;
  align-self: stretch;
`;

const UploadButton = styled.label`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  font: ${({ theme }) => theme.font.availableMedium12};

  &:hover {
    cursor: pointer;
    opacity: ${({ theme }) => theme.opacity.hover};
  }

  & input {
    display: none;
  }
`;
