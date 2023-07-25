import { ThemeProvider } from "styled-components";
import { designSystem } from "./constants/designSystem";

export default function App() {
  return (
    <ThemeProvider theme={designSystem}>
      <div>hello world</div>
    </ThemeProvider>
  );
}
