import React, { InputHTMLAttributes, useState } from "react";
import styled, { DefaultTheme } from "styled-components";
import { Icon } from "components/Common/Icon/Icon";
import { Button } from "../Button/Button";

interface TextAreaProps extends InputHTMLAttributes<HTMLTextAreaElement> {
  labelText?: string;
}

type TextAreaStates = "enabled" | "active" | "disabled" | "typing" | "typed";

export const TextArea: React.FC<TextAreaProps> = ({ labelText }) => {
  const [inputValue, setInputValue] = useState("");
  const [currentState, setCurrentState] = useState<TextAreaStates>("enabled");

  const handleFocus = () => {
    setCurrentState("active");
  };

  const handleBlur = () => {
    if (inputValue) {
      setCurrentState("typed");
    } else {
      setCurrentState("enabled");
    }
  };

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputValue(event.target.value);

    if (inputValue) {
      setCurrentState("typing");
    }
  };

  return (
    <Layout $currentState={currentState}>
      <TextAreaBox $currentState={currentState}>
        <label htmlFor="">{labelText}</label>
        <textarea
          name=""
          id=""
          onFocus={handleFocus}
          onBlur={handleBlur}
          onChange={handleChange}
          disabled={currentState === "disabled"}
          value={inputValue}
        />
      </TextAreaBox>
      <FileButton size="S" variant="ghost">
        <Icon icon="Paperclip" size="S" stroke="nuetralTextDefault" />
        <p>파일 첨부하기</p>
      </FileButton>
    </Layout>
  );
};

/* 이거랑 typing 스타일도 어찌저찌 잘 정리하면
  input에 사용하는 스타일이랑 같아서 잘 정리하면 파일에 분리할 수 있을 듯 */
const stateStyle = (theme: DefaultTheme) => ({
  enabled: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceBold};
    border-color: ${theme.color.nuetralSurfaceBold};
  `,
  active: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceStrong};
    border-color: ${theme.color.nuetralBorderDefaultActive};
  `,
  disabled: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceBold};
    border-color: ${theme.color.nuetralSurfaceBold};
  `,
  typing: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceStrong};
    border-color: ${theme.color.nuetralBorderDefaultActive};
  `,
  typed: `
    border: ${theme.border.default};
    background-color: ${theme.color.nuetralSurfaceBold};
    border-color: ${theme.color.nuetralSurfaceBold};
  `,
});

const Layout = styled.div<{
  $currentState: TextAreaStates;
}>`
  ${({ theme, $currentState }) => stateStyle(theme)[$currentState]}
  width: 100%;
  height: 100%;
  position: relative;
  border-radius: ${({ theme }) => theme.radius.large};
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;

  :focus-within label {
    transform: translate(0, 0) scale(0.8);
  }
`;

const TextAreaBox = styled.div<{
  $currentState: TextAreaStates;
}>`
  flex: 1;
  display: flex;
  flex-direction: column;

  label {
    padding: 16px 16px 0 20px;
    font: ${({ theme: { font } }) => font.displayM16};
    color: ${({ theme: { color } }) => color.nuetralTextWeak};
    pointer-events: none;
    transform-origin: top left;
    transition: 200ms cubic-bezier(0, 0, 0.2, 1) 0ms;
    transform: ${({ $currentState }) =>
      $currentState !== "enabled" && $currentState !== "active"
        ? "translate(0, 0px) scale(0.8)"
        : "translate(0, 0px) scale(1)"};
  }

  textarea {
    height: 100%;
    color: ${({ $currentState, theme: { color } }) =>
      $currentState === "typing" || $currentState === "active"
        ? color.nuetralTextStrong
        : color.nuetralTextWeak};
    background-color: transparent;
    width: 100%;
    border: none;
    outline: none;
    padding: 0 16px 40px 16px;
    resize: none;
    flex: 1;
    transition: all 0.3s;

    &::-webkit-scrollbar {
      width: 5px;
      background-color: transparent;
    }

    &::-webkit-scrollbar-track {
      background-color: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background-color: ${({ theme: { color } }) => color.nuetralTextDefault};
      border-radius: ${({ theme }) => theme.radius.large};
    }

    &::-webkit-scrollbar-button {
      display: none;
    }

    &:not(:focus)::-webkit-scrollbar {
      display: none;
    }
  }
`;

const FileButton = styled(Button)`
  border-top: ${({ theme: { border } }) => border.dash};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  padding: 10px 16px;
  height: 52px;
  display: flex;
  justify-content: flex-start;
`;
