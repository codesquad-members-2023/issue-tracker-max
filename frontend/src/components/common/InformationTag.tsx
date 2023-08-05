import { styled } from 'styled-components';
import Icons from '../../design/Icons';

enum Size {
  small = 'small',
  medium = 'medium',
}

export default function InformationTag({
  size,
  iconName,
  children,
}: {
  size: keyof typeof Size;
  iconName?: keyof typeof Icons;
  children?: React.ReactNode;
}) {
  const Icon = iconName && Icons[iconName];

  return (
    <Container $size={size}>
      {Icon && <Icon />}
      <Text>{children}</Text>
    </Container>
  );
}

const Container = styled.span<{ $size: keyof typeof Size }>`
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  border-radius: ${({ theme, $size }) =>
    $size === Size.small
      ? theme.objectStyles.radius.medium
      : theme.objectStyles.radius.large};
  padding: 0px ${({ $size }) => ($size === Size.small ? '8px' : '16px')};
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: ${({ $size }) => ($size === Size.small ? '24px' : '32px')};
  color: ${({ theme }) => theme.color.neutral.text.weak};

  & > svg {
    stroke: ${({ theme }) => theme.color.neutral.text.weak};
  }
`;

const Text = styled.span`
  padding: 0 4px;
  ${({ theme }) => theme.font.display.medium[12]}
`;
