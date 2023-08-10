import { styled } from 'styled-components';
import Button from './BaseButton';

const ButtonSmall = styled(Button)<{ ghost?: boolean; selected?: boolean }>`
  width: ${({ flexible }) => (flexible ? 'auto' : '128px')};
  min-height: 40px;
  ${({ theme }) => theme.font.available.medium[12]}
  ${({ ghost }) => (ghost ? `min-height: 32px;` : '')}
  ${({ theme, ghost, selected }) =>
    selected && ghost
      ? theme.font.selected.bold[12]
      : theme.font.available.medium[12]};
`;

export default ButtonSmall;
