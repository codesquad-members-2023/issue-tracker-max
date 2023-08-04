import {useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {styled, ThemeProvider} from "styled-components";
import {Header} from "./components/Header";
import {designSystem} from "./constants/designSystem";
import {Error404} from "./page/Error404";
import {Label} from "./page/label/Label";
import {Main} from "./page/main/Main";

export default function App() {
  const [themeMode, setThemeMode] = useState<"LIGHT" | "DARK">("LIGHT");

  return (
      <Div>
        <ThemeProvider theme={designSystem[themeMode]}>
          <Router>
            <Header/>
            <Routes>
              <Route path="/" element={<Main/>}/>
              <Route path="/label" element={<Label/>}/>
              <Route path="*" element={<Error404/>}/>
            </Routes>
          </Router>
        </ThemeProvider>
      </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
