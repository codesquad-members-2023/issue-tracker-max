import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  .blind {
    width: 1px;
    height: 1px;
    margin: -1px;
    position: absolute;
    overflow: hidden;
    clip-path: polygon(0 0, 0 0, 0 0, 0 0);
  }
`;

export default GlobalStyle;
