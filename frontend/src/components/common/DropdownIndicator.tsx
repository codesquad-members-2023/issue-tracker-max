import { styled } from 'styled-components';
import Icons from '../../design/Icons';
import DropdownPanel from './DropdownPanel';
import { useState } from 'react';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import ButtonComponent from './button/BaseButton';
import DropdownType from '../../constant/DropdownType';

export default function DropdownIndicator<T extends DropdownType>({
  type,
  text,
  label,
  elements,
  ...props
}: {
  type: T;
  text: string;
  label: string;
  elements: DropdownPanelElement<T>[];
}) {
  const [isOpenPanel, setIsOpenPanel] = useState(false);

  function submitHandler(e: React.FormEvent) {
    e.preventDefault();
    console.log(e);
  }

  return (
    <Container onSubmit={submitHandler} {...props}>
      <Button
        type="button"
        ghost
        flexible
        onClick={() => setIsOpenPanel((bool) => !bool)}>
        <Text>{text}</Text>
        <Icons.chevronDown />
      </Button>
      <Panel>
        {isOpenPanel && (
          <DropdownPanel {...{ type, label, baseElements: elements }} />
        )}
      </Panel>
    </Container>
  );
}

const Container = styled.form`
  position: relative;
  z-index: 1;
`;

const Button = styled(ButtonComponent)`
  &:hover {
    opacity: ${({ theme }) => theme.objectStyles.opacity.hover};
  }
  &:active {
    opacity: ${({ theme }) => theme.objectStyles.opacity.press};
  }
  &:disabled {
    opacity: ${({ theme }) => theme.objectStyles.opacity.disabled};
  }
`;

const Text = styled.span`
  ${({ theme }) => theme.font.available.medium[16]};
  color: ${({ theme }) => theme.color.neutral.text.default};
`;

const Panel = styled.div`
  position: absolute;
  left: -1px;
  top: calc(100% + 8px);
`;
