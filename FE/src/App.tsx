import theme from "./styles/Theme.ts";
import { ThemeProvider } from "styled-components";

function App() {
  return <ThemeProvider theme={theme}></ThemeProvider>;
}

export default App;
