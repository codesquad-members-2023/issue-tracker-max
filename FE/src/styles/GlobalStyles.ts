import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

const GlobalStyles = createGlobalStyle`
    ${reset}
    a{
        text-decoration: none;
        color: inherit;
    }
    * {
    box-sizing: border-box;
    }
    html, body, div, span, h1, h2, h3, h4, h5, h6, p, 
    a, dl, dt, dd, ol, ul, li, form, label, table {
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
    }
    ol, ul{
        list-style: none;
    }
    button {
        padding: 0;
        border: 0;
        background: transparent;
        cursor: pointer;
    }
    input {
        padding: 0;
        margin: 0;
        border: 0;
    }
`;

export default GlobalStyles;
