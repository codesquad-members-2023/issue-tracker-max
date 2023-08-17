import {
  ChangeEvent,
  ReactNode,
  TextareaHTMLAttributes,
  useEffect,
  useRef,
  useState,
} from "react";
import { keyframes, styled } from "styled-components";
import { getAccessToken } from "../utils/localStorage";
import { Button } from "./Button";
import { MarkdownViewer } from "./MarkdownViewer";
import { Icon } from "./icon/Icon";

type TextAreaProps = {
  value?: string;
  label?: string;
  width?: string | number;
  height?: string | number;
  children?: ReactNode;
  isEditing?: boolean;
  errorDescription?: string;
  onChange?: (value: string) => void;
  onTextAreaFocus?: () => void;
} & Omit<TextareaHTMLAttributes<HTMLTextAreaElement>, "onChange">;

type TextAreaState = "Active" | "Enabled" | "Disabled";

export function TextArea({
  value = ``,
  label,
  width,
  height,
  children,
  isEditing = false,
  disabled,
  placeholder,
  maxLength,
  errorDescription,
  onChange,
  onTextAreaFocus,
}: TextAreaProps) {
  const [state, setState] = useState<TextAreaState>(
    disabled ? "Disabled" : isEditing ? "Active" : "Enabled",
  );
  const [inputValue, setInputValue] = useState(value);
  const [countHidden, setCountHidden] = useState(true);
  const componentRef = useRef<HTMLDivElement>(null);
  const textAreaRef = useRef<HTMLTextAreaElement>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setInputValue(value);
  }, [value]);

  useEffect(() => {
    setState(disabled ? "Disabled" : isEditing ? "Active" : "Enabled");
  }, [disabled, isEditing]);

  useEffect(() => {
    if (state === "Active" && textAreaRef.current) {
      textAreaRef.current.selectionStart = textAreaRef.current.value.length;
      textAreaRef.current.selectionStart = textAreaRef.current.value.length;
      textAreaRef.current.focus();
    }
  }, [state]);

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
    onTextAreaFocus?.();
  };

  const onBlur = () => {
    if (isEditing) {
      return;
    }
    setState("Enabled");
  };

  const onTextChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setInputValue(e.target.value);
    setCountHidden(false);
    onChange?.(e.target.value);
  };

  const onFileInputClick = () => {
    fileInputRef.current?.click();
  };

  const onFileInputChange = async (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file && isImageTypeFile(file)) {
      const data = await fetchImageText(file);

      const dataIntoTextArea = (i: string) => {
        const start = textAreaRef.current?.selectionStart;
        const end = textAreaRef.current?.selectionEnd;

        if (start === undefined || end === undefined) {
          return i;
        }

        const textBefore = i.slice(0, start);
        const textAfter = i.slice(end);

        return `${textBefore}![image](${data})${textAfter}`;
      };

      onFocus();
      setInputValue(dataIntoTextArea);
      setCountHidden(false);
      onChange?.(dataIntoTextArea(inputValue));
    } else {
      alert("이미지 파일만 업로드 가능합니다.");
    }

    e.target.value = "";
  };

  const isImageTypeFile = (file: File) => {
    const imageTypes = [
      "image/apng",
      "image/avif",
      "image/gif",
      "image/jpeg",
      "image/png",
      "image/svg+xml",
      "image/webp",
      "image/bmp",
      "image/x-icon",
      "image/tiff",
    ];

    return imageTypes.includes(file.type);
  };

  const fetchImageText = async (file: File) => {
    const formData = new FormData();
    formData.append("image", file as File);

    const response = await fetch("/api/images", {
      method: "POST",
      credentials: "include",
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: formData,
    });
    const { code, message, data } = await response.json();

    if (code === 201) {
      return data.url;
    }

    alert(`[이미지 저장 실패!]\n${message}`);
    throw new Error(message);
  };

  return (
    <Wrapper $width={width}>
      <Div
        $state={state}
        $children={children}
        onBlur={onBlur}
        ref={componentRef}
      >
        {children && <Header>{children}</Header>}
        {children && state !== "Active" ? (
          <MarkdownViewer markdown={value} />
        ) : (
          <>
            <InputContainer onFocus={onFocus}>
              {inputValue && label && <Label>{label}</Label>}
              <ResizableDiv $children={children}>
                <Input
                  $children={children}
                  $height={height}
                  placeholder={placeholder}
                  value={inputValue}
                  maxLength={maxLength}
                  onChange={onTextChange}
                  disabled={disabled}
                  ref={textAreaRef}
                />
                <TextCount $hidden={countHidden}>
                  띄어쓰기 포함 {inputValue.length}글자
                </TextCount>
                <Icon name="Grip" color="neutralTextWeak" pointer={false} />
              </ResizableDiv>
            </InputContainer>
            <Footer>
              <Button
                size="S"
                buttonType="Ghost"
                icon="PaperClip"
                onClick={onFileInputClick}
              >
                파일 첨부하기
              </Button>
              <input
                type="file"
                accept="image/*"
                onChange={onFileInputChange}
                ref={fileInputRef}
                hidden
              />
            </Footer>
          </>
        )}
      </Div>
      {isEditing && errorDescription && (
        <ErrorDescription>{errorDescription}</ErrorDescription>
      )}
    </Wrapper>
  );
}

const Wrapper = styled.div<{ $width?: string | number }>`
  display: flex;
  flex-direction: column;
  width: ${({ $width }) =>
    typeof $width === "number" ? `${$width}px` : $width};
`;

const Div = styled.div<{ $state: TextAreaState; $children: ReactNode }>`
  display: flex;
  min-width: 340px;
  flex-direction: column;
  border: ${({ theme, $state, $children }) =>
    `${theme.border.default} ${
      $state === "Active"
        ? theme.color.neutralBorderDefaultActive
        : $children
        ? theme.color.neutralBorderDefault
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

const Header = styled.div`
  align-self: stretch;
  display: flex;
  padding: 16px 24px;
  justify-content: space-between;
  align-items: center;
  border-radius: ${({ theme }) =>
    `${theme.radius.large} ${theme.radius.large} 0px 0px`};
  background-color: ${({ theme }) => theme.color.neutralSurfaceDefault};
`;

const InputContainer = styled.div`
  align-self: stretch;
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

const ResizableDiv = styled.div<{ $children: ReactNode }>`
  position: relative;
  min-height: ${({ $children }) => ($children ? "120px" : "200px")};
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
  }
`;

const Input = styled.textarea<{
  $children: ReactNode;
  $height?: string | number;
}>`
  box-sizing: border-box;
  width: 100%;
  height: ${({ $children, $height }) =>
    typeof $height === "number"
      ? `${Math.min($height, 461)}px`
      : $children
      ? "200px"
      : "461px"};
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
  color: ${({ theme }) => theme.color.neutralTextWeak};
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
  align-items: center;
  gap: 8px;
  align-self: stretch;
`;

const ErrorDescription = styled.span`
  display: flex;
  padding-left: 0px;
  align-items: flex-start;
  align-self: stretch;
  padding-left: 16px;
  margin: 8px;
  color: ${({ theme }) => theme.color.dangerTextDefault};
  font: ${({ theme }) => theme.font.displayMedium12};
`;
