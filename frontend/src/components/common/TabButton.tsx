import { styled } from 'styled-components';
import { useState } from 'react';
import Button from './button/BaseButton';
import { v4 as uuidV4 } from 'uuid';
import Icons from '../../design/Icons';

export default function TabButton({
  tabs,
}: {
  tabs: {
    iconName: keyof typeof Icons;
    text: string;
    event: () => void;
  }[];
}) {
  const [buttonActive, setButtonActive] = useState<number>(1);
  return (
    <Tab $buttonActive={buttonActive}>
      {tabs.map(({ iconName, text, event }, index) => (
        <Button
          key={uuidV4()}
          type="button"
          flexible
          ghost
          iconName={iconName}
          selected={index + 1 === buttonActive}
          onClick={() => {
            setButtonActive(index + 1);
            event();
          }}>
          {text}
        </Button>
      ))}
    </Tab>
  );
}

const Tab = styled.div<{ $buttonActive: number | undefined }>`
  display: inline-flex;
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme }) => theme.objectStyles.radius.medium};
  overflow: hidden;
  width: 320px;

  button {
    width: 100%;
    box-sizing: content-box;
    border-right: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  }

  button:last-child {
    border: 0;
  }

  ${({ theme, $buttonActive }) => {
    if (!$buttonActive) {
      return ``;
    }
    return `
      button:nth-child(${$buttonActive}) {
        background: ${theme.color.neutral.surface.bold};
      }
    `;
  }}
`;
