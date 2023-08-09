import GripIcon from "@assets/icon/grip.svg";
import { debounce } from "lodash";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Label } from "../Label.style";

export default function TextAreaWrapper({
  value,
  ...props
}: {
  value: string;
} & React.TextareaHTMLAttributes<HTMLTextAreaElement>) {
  const [isCharCountShown, setIsCharCountShown] = useState(false);

  useEffect(() => {
    const DEBOUNCE_TIME = 500;
    const debouncedHandleCharCountShown = debounce(
      handleCharCountShown,
      DEBOUNCE_TIME
    );

    if (value) {
      debouncedHandleCharCountShown();
    }

    return () => debouncedHandleCharCountShown.cancel();
  }, [value]);

  const charCountMessage = value && `띄어쓰기 포함 ${value.length}자`;

  const handleCharCountShown = () => {
    const CHAR_SHOW_TIME = 2000;
    setIsCharCountShown(true);

    setTimeout(() => {
      setIsCharCountShown(false);
    }, CHAR_SHOW_TIME);
  };

  return (
    <StyledTextAreaWrapper>
      {value && <Label htmlFor={props.name}>{props.placeholder}</Label>}
      <TextArea id={props.name} value={value} {...props} />
      <div className="caption">
        {isCharCountShown && <CharCountText>{charCountMessage}</CharCountText>}
        <img src={GripIcon} alt="grip-icon" />
      </div>
    </StyledTextAreaWrapper>
  );
}

const StyledTextAreaWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 8px;
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.dashed} ${neutral.border.default}`};

  .caption {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
  }
`;

const TextArea = styled.textarea`
  display: flex;
  color: ${({ theme: { neutral } }) => neutral.text.default};
  font: ${({ theme: { font } }) => font.displayMD16};
  caret-color: ${({ theme: { palette } }) => palette.blue};

  &::placeholder {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }

  &:focus {
    color: ${({ theme: { neutral } }) => neutral.text.strong};
  }

  &::-webkit-scrollbar {
    width: 14px;
    height: 14px;
  }

  &::-webkit-scrollbar-thumb {
    outline: none;
    border-radius: 10px;
    border: 4px solid transparent;
    box-shadow: ${({ theme: { neutral } }) =>
      `inset 6px 6px 0 ${neutral.border.default}`};
  }

  &::-webkit-scrollbar-thumb:hover {
    border: 4px solid transparent;
    box-shadow: ${({ theme: { neutral } }) =>
      `inset 6px 6px 0 ${neutral.border.defaultActive}`};
  }

  &::-webkit-scrollbar-track {
    box-shadow: none;
    background-color: transparent;
  }
`;

const CharCountText = styled.span`
  display: flex;
  justify-content: flex-end;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD12};
`;
