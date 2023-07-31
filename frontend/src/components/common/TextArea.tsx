import { useState } from 'react';
import { styled } from 'styled-components';
import ButtonSmall from './button/ButtonSmall';
import React from 'react';

type TextAreaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement> & {
  labelName: string;
  placeholder?: string;
  disabled?: boolean;
};

export default function TextArea(props: TextAreaProps) {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [textValue, setTextValue] = useState<string>('');
  const { labelName, placeholder, disabled } = props;

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
    <Wrapper onFocus={handleFocus} onBlur={handleBlur}>
      <Section>
        {(isFocused || textValue) && <Label>{labelName}</Label>}
        <StyledTextArea
          placeholder={placeholder}
          value={textValue}
          disabled={disabled}
          onChange={handleChange}></StyledTextArea>
      </Section>
      <Bottom>
        <TextCounter>띄어쓰기 포함 {textValue.length}자</TextCounter>
        <ButtonSmall type="submit" ghost flexible iconName="paperClip">
          파일 첨부하기
        </ButtonSmall>
      </Bottom>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 340px;
  min-height: 184px;
  display: flex;
  flex-direction: column;
  background: ${({ theme }) => theme.color.neutral.surface.bold};
  border: ${({ theme }) => theme.objectStyles.border.default};
  border-color: transparent;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  color: ${({ theme }) => theme.color.neutral.text.weak};

  &:focus-within {
    border-color: ${({ theme }) => theme.color.neutral.border.active};
    background: ${({ theme }) => theme.color.neutral.surface.strong};
`;

const Section = styled.div`
  min-height: 132px;
  margin: 16px;
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  ${({ theme }) => theme.font.display.medium[12]}
`;

const StyledTextArea = styled.textarea`
  border: none;
  outline: none;
  caret-color: ${({ theme }) => theme.color.palette.blue};
  background: ${({ theme }) => theme.color.neutral.surface.bold};
  color: ${({ theme }) => theme.color.neutral.text.weak};
  ${({ theme }) => theme.font.display.medium[16]};
  resize: none;

  &:focus {
    background: ${({ theme }) => theme.color.neutral.surface.strong};
    color: ${({ theme }) => theme.color.neutral.text.strong};
  }
`;

const Bottom = styled.div`
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
