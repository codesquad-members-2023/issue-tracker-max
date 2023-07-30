import { styled } from 'styled-components';
import Button from './BaseButton';

const ButtonSmall = styled(Button)`
  width: ${({ flexible }) => (flexible ? 'auto' : '128px')};
  height: 40px;
`;

export default ButtonSmall;
