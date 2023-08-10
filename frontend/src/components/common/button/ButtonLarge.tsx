import { styled } from 'styled-components';
import Button from './BaseButton';

const ButtonLarge = styled(Button)<{ ghost?: boolean; selected?: boolean }>`
  width: ${({ flexible }) => (flexible ? 'auto' : '240px')};
  min-height: 56px;
  ${({ theme }) => theme.font.available.medium[20]}
  ${({ ghost }) =>
    ghost
      ? 'min-height: 32px;'
      : ''}
  ${({ theme, ghost, selected }) =>
    selected && ghost
      ? theme.font.selected.bold[20]
      : theme.font.available.medium[20]};

  svg {
    width: 24px;
    height: 24px;
  }
`;

export default ButtonLarge;
