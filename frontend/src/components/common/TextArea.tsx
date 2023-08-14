import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import ButtonSmall from './button/ButtonSmall';
import React from 'react';

type TextAreaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement> & {
  labelName?: string;
};

export default function TextArea(props: TextAreaProps) {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [isTyping, setIsTyping] = useState<boolean>(false);
  const { value, labelName, placeholder, disabled, ...rest } = props;

  useEffect(() => {
    if (isFocused && !isTyping) {
      setIsTyping(true);
      setTimeout(() => {
        setIsTyping(false);
      }, 2000);
    }
  }, [value]);

  const handleBlur = () => {
    setIsFocused(false);
  };

  const handleFocus = () => {
    setIsFocused(true);
  };

  return (
    <Wrapper $isFocused={isFocused} onFocus={handleFocus} onBlur={handleBlur}>
      <Section>
        {labelName && isFocused && <Label>{labelName}</Label>}
        <StyledTextArea
          value={value}
          placeholder={placeholder}
          disabled={disabled}
          spellCheck="false"
          $isFocused={isFocused}
          {...rest}></StyledTextArea>
      </Section>
      <Bottom>
        {isTyping && typeof value === 'string' && (
          <TextCounter>띄어쓰기 포함 {value.length}자</TextCounter>
        )}
        <ButtonSmall type="button" ghost flexible iconName="paperClip">
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
