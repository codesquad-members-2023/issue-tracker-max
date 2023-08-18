import { ReactNode } from "react";
import { RouterProvider } from "react-router-dom";
import router from "./routes/router";

import { ThemeProvider } from "styled-components";
import { GlobalStyles } from "styles/GlobalStyles";
import { theme } from "styles/Theme";
import { ThemeModeProvider, useThemeMode } from "contexts/ThemeModeContext";
import { AuthProvider } from "contexts/AuthContext";
import { Loading } from "pages/Loading";

interface ThemedAppProps {
  children: ReactNode;
}

const ThemedApp: React.FC<ThemedAppProps> = ({ children }) => {
  const { mode } = useThemeMode();
  return <ThemeProvider theme={theme[mode]}>{children}</ThemeProvider>;
};

export const App = () => {
  return (
    <ThemeModeProvider>
      <AuthProvider>
        <ThemedApp>
          <GlobalStyles />
          <RouterProvider router={router} fallbackElement={<Loading />} />
        </ThemedApp>
      </AuthProvider>
    </ThemeModeProvider>
  );
};
