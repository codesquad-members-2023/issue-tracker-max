import { ReactNode, useEffect} from "react";
import { RouterProvider } from "react-router-dom";
import router from "./routes/router";

import { ThemeProvider } from "styled-components";
import { GlobalStyles } from "styles/GlobalStyles";
import { theme } from "styles/Theme";
import { ThemeModeProvider, useThemeMode } from "contexts/ThemeModeContext.tsx";

// import { ComponentTest } from "./ComponentTest";

interface ThemedAppProps {
  children: ReactNode;
}

const ThemedApp: React.FC<ThemedAppProps> = ({ children }) => {
  const { mode } = useThemeMode();
  return <ThemeProvider theme={theme[mode]}>{children}</ThemeProvider>;
};

export const App = () => {
  useEffect(() => {
    fetch("http://localhost:8080/api/issues/closed")
    .then((response) => response.json())
    .then((data) => console.log(data))
    .catch((error) => {
    console.error("Error:", error);
    });
    }, []);
  return (
    <ThemeModeProvider>
      <ThemedApp>
        <GlobalStyles />
        <RouterProvider router={router} />
        {/* <ComponentTest></ComponentTest> */}
      </ThemedApp>
    </ThemeModeProvider>
  );
};
