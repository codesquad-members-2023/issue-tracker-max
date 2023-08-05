import { styled } from 'styled-components';
import Icons from '../../design/Icons';

export default function DropdownIndicator({ text, ...rest }: { text: string }) {
  const Icon = Icons['chevronDown'];
  return (
    <Container {...rest}>
      <Text>{text}</Text>
      <Icon />
    </Container>
  );
}

const Container = styled.button`
  height: 32px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: none;
  background: transparent;
  gap: 4px;
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
