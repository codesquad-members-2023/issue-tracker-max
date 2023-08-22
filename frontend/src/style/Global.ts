import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
  }

  body {
    background: ${({ theme }) => theme.color.neutral.surface.default};
  }

  progress {
    appearance: none;
    -webkit-appearance: none;
    border: none;
    color: #6b6b6b;
    height: 8px;
    width: 100%;
  }

  ::-webkit-progress-bar {
      background-color: #EFF0F6;
      border-radius: 4px;
      box-shadow: none;
  }

  ::-webkit-progress-value {
      background-color: #007AFF;
      border-radius: 4px;
  }

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
