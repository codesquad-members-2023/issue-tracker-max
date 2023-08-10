import { styled } from 'styled-components';
import Button from './BaseButton';

const ButtonLarge = styled(Button)`
  width: ${({ flexible }) => (flexible ? 'auto' : '240px')};
  height: 56px;
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
