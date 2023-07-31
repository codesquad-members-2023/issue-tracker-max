import { styled } from 'styled-components';
import { useState } from 'react';
import Button from './button/BaseButton';

enum ButtonActive {
  Default,
  Left,
  Right,
}

const { Default, Left, Right } = ButtonActive;

export default function TabButton() {
  const [buttonActive, setButtonActive] = useState<ButtonActive>(Default);
  return (
    <Tab $buttonActive={buttonActive}>
      <Button
        type="button"
        flexible
        ghost={true}
        iconName="plus"
        onClick={() => setButtonActive(Left)}>
        BUTTON
      </Button>
      <Vertical />
      <Button
        type="button"
        ghost={true}
        flexible
        iconName="plus"
        onClick={() => setButtonActive(Right)}>
        BUTTON
      </Button>
    </Tab>
  );
}

const Tab = styled.div<{ $buttonActive: ButtonActive }>`
  display: inline-flex;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  overflow: hidden;

  button {
    box-sizing: content-box;
  }
  button:first-child {
    background-color: ${({ theme, $buttonActive }) => {
      return $buttonActive === Left
        ? theme.color.neutral.surface.bold
        : theme.color.neutral.surface.default;
    }};
  }
  button:last-child {
    background-color: ${({ theme, $buttonActive }) => {
      return $buttonActive === Right
        ? theme.color.neutral.surface.bold
        : theme.color.neutral.surface.default;
    }};
  }
`;

const Vertical = styled.div`
  width: 1px;
  background-color: ${({ theme }) => theme.color.neutral.border.default};
`;
