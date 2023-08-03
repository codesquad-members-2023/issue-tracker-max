import {
  ChangeEvent,
  TextareaHTMLAttributes,
  useEffect,
  useRef,
  useState,
} from "react";
import { keyframes, styled, useTheme } from "styled-components";
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
  maxLength,
  onChange,
}: TextAreaProps) {
  const [state, setState] = useState<TextAreaState>(
    disabled ? "Disabled" : "Enabled",
  );
  const [inputValue, setInputValue] = useState(value);
  const [countHidden, setCountHidden] = useState(true);
  const textArea = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    let timer: ReturnType<typeof setTimeout>;

    if (!countHidden) {
      timer = setTimeout(() => {
        setCountHidden(true);
      }, 2000);
    }

    return () => clearTimeout(timer);
  }, [countHidden]);

  const onFocus = () => {
    setState("Active");
  };

  const onBlur = () => {
    setState("Enabled");
  };

  const onTextChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setInputValue(e.target.value);
    setCountHidden(false);
    onChange && onChange(e);
  };

  const onFileInputChange = async (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file) {
      // TODO: 이미지 확장자 검증

      const data = await fetchImageText(file);

      setInputValue((i) => {
        const start = textArea.current?.selectionStart;
        const end = textArea.current?.selectionEnd;

        if (start === undefined || end === undefined) {
          return i;
        }

        const textBefore = i.slice(0, start);
        const textAfter = i.slice(end);

        return `${textBefore}![image](${data})${textAfter}`;
      });
    }
  };

  const fetchImageText = async (file: File) => {
    const formData = new FormData();
    formData.append("image", file as File);

    const response = await fetch(
      "https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io/api/images",
      {
        method: "POST",
        body: formData,
      },
    );
    const data = await response.text();

    return data;
  };

  const theme = useTheme();
  const iconColor = theme.color.neutralTextDefault;
  const gripColor = theme.color.neutralTextWeak;

  return (
    <Div $state={state} onFocus={onFocus} onBlur={onBlur}>
      <InputContainer>
        {inputValue && label && <Label>{label}</Label>}
        <ResizableDiv>
          <Input
            placeholder={placeholder}
            value={inputValue}
            maxLength={maxLength}
            onChange={onTextChange}
            disabled={disabled}
            ref={textArea}
          />
          <TextCount $hidden={countHidden}>
            띄어쓰기 포함 {inputValue.length}글자
          </TextCount>
          <Icon name="grip" fill={gripColor} stroke={gripColor} />
        </ResizableDiv>
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
  min-height: 200px;
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

const ResizableDiv = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  resize: vertical;
  overflow: hidden;

  &::-webkit-resizer {
    display: none;
  }

  & svg {
    position: absolute;
    bottom: 0;
    right: 0;
    cursor: grab;
  }
`;

const Input = styled.textarea`
  box-sizing: border-box;
  width: 100%;
  height: 461px;
  resize: none;
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

const TextCount = styled.span<{ $hidden: boolean }>`
  align-self: flex-end;
  margin-right: 28px;
  font: ${({ theme }) => theme.font.displayMedium12};
  opacity: ${({ $hidden }) => ($hidden ? 0 : 1)};
  animation: ${({ $hidden }) => ($hidden ? fadeOut : fadeIn)} 0.2s ease-in-out;
`;

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
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
