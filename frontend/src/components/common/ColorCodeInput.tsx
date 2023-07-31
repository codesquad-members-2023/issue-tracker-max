import { styled } from 'styled-components';
import Icons from '../../design/Icons';
import { useState } from 'react';

export default function ColorCodeInput({ label }: { label: string }) {
  const [colorCode, setColorCode] = useState<string>(getRandomColor());
  const [key, setKey] = useState<number>(Date.now());

  const submitHandler = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as typeof e.target & {
      'color-code': { value: string };
    };
    const colorCode = form['color-code'].value;
    if (isValidColorCode(colorCode)) {
      setColorCode(colorCode);
    }
  }

  const refresh = () => {
    setKey(Date.now());
  }

  const refreshBtnHandler = () => {
    setColorCode(getRandomColor());
    refresh();
  };

  return (
    <Container onSubmit={submitHandler}>
      <Label htmlFor="color-code">{label}</Label>
      <TypingStates>
        <input
          key={key}
          type="text"
          id="color-code"
          name="color-code"
          defaultValue={colorCode}
          pattern="^#+([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$"
          onInvalid={refresh}
          onBlur={refresh}
          required
        />
      </TypingStates>
      <Refresh type="button" onClick={refreshBtnHandler}>
        <Icons.refresh />
      </Refresh>
    </Container>
  );
}

const Container = styled.form`
  height: 40px;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 0 16px;
  text-align: left;
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  background-color: ${({ theme }) => theme.color.neutral.surface.bold};
  &:active {
    background-color: ${({ theme }) => theme.color.neutral.surface.strong};
    border: ${({ theme }) => theme.objectStyles.border.default};
    border-color: ${({ theme }) => theme.color.neutral.border.active};
  }
`;

const Label = styled.label`
  ${({ theme }) => theme.font.display.medium[12]}
  color: ${({ theme }) => theme.color.neutral.text.weak};
`;

const TypingStates = styled.fieldset`
  width: 112px;
  input {
    width: 100%;
    ${({ theme }) => theme.font.display.medium[16]}
    color: ${({ theme }) => theme.color.neutral.text.strong};
    border: none;
    background-color: transparent;
  }
`;

const Refresh = styled.button`
  background-color: transparent;
  border: none;
  padding: 0;
`;

function getRandomColor(): string {
  const letters: string = '0123456789ABCDEF';
  let color: string = '#';
  for (let i: number = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

function isValidColorCode(colorCode: string): boolean {
  const regex: RegExp = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/;
  return regex.test(colorCode);
}
