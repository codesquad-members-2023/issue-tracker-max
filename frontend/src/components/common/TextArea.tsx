import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import ButtonSmall from './button/ButtonSmall';
import React from 'react';

type TextAreaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement> & {
  labelName?: string;
  placeholder?: string;
  disabled?: boolean;
};

export default function TextArea(props: TextAreaProps) {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [textValue, setTextValue] = useState<string>('');
  const [isTyping, setIsTyping] = useState<boolean>(false);
  const { labelName, placeholder, disabled } = props;

  useEffect(() => {
    if (isFocused && !isTyping) {
      setIsTyping(true);
      setTimeout(() => {
        setIsTyping(false);
      }, 2000);
    }
  }, [textValue]);

  const handleBlur = () => {
    setIsFocused(false);
  };

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setTextValue(e.target.value);
  };

  return (
    <Wrapper $isFocused={isFocused} onFocus={handleFocus} onBlur={handleBlur}>
      <Section>
        {(isFocused || textValue) && <Label>{labelName}</Label>}
        <StyledTextArea
          placeholder={placeholder}
          value={textValue}
          disabled={disabled}
          spellCheck="false"
          $isFocused={isFocused}
          onChange={handleChange}></StyledTextArea>
      </Section>
      <Bottom>
        {isTyping && (
          <TextCounter>띄어쓰기 포함 {textValue.length}자</TextCounter>
        )}
        <ButtonSmall type="submit" ghost flexible iconName="paperClip">
          파일 첨부하기
        </ButtonSmall>
      </Bottom>
    </Wrapper>
  );
}

const Wrapper = styled.div<{ $isFocused: boolean }>`
  min-height: 184px;
  display: flex;
  flex-direction: column;
  background: ${({ theme, $isFocused }) =>
    $isFocused
      ? theme.color.neutral.surface.strong
      : theme.color.neutral.surface.bold};
  border: ${({ theme }) => theme.objectStyles.border.default};
  border-color: ${({ theme, $isFocused }) =>
    $isFocused ? theme.color.neutral.border.active : 'transparent'};
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  color: ${({ theme }) => theme.color.neutral.text.weak};
`;

const Section = styled.div`
  min-height: 132px;
  margin: 16px;
  display: flex;
  flex-direction: column;
  flex: 1;
`;

const Label = styled.label`
  ${({ theme }) => theme.font.display.medium[12]}
`;

const StyledTextArea = styled.textarea<{ $isFocused: boolean }>`
  margin-bottom: 16px;
  border: none;
  outline: none;
  caret-color: ${({ theme }) => theme.color.palette.blue};
  background: ${({ theme, $isFocused }) =>
    $isFocused
      ? theme.color.neutral.surface.strong
      : theme.color.neutral.surface.bold};
  color: ${({ theme, $isFocused }) =>
    $isFocused
      ? theme.color.neutral.text.strong
      : theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]};
  resize: none;
  flex: 1;
`;

const Bottom = styled.div`
  padding: 0 16px;
  height: 52px;
  position: relative;
  display: flex;
  align-items: center;
  border-top: ${({ theme }) => theme.objectStyles.border.dash};
  ${({ theme }) => theme.color.neutral.border.default};
`;

const TextCounter = styled.span`
  ${({ theme }) => theme.font.display.medium[12]};
  position: absolute;
  top: -32px;
  right: 30px;
`;
