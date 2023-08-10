import { styled } from 'styled-components';
import Button from './BaseButton';

const ButtonSmall = styled(Button)`
  width: ${({ flexible }) => (flexible ? 'auto' : '128px')};
  height: 40px;
  ${({ theme, ghost, selected }) =>
    selected && ghost
      ? theme.font.selected.bold[12]
      : theme.font.available.medium[12]};
`;

export default ButtonSmall;
