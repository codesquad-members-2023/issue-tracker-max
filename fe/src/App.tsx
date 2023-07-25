import { useState } from 'react';
import { BrowserRouter, Routes, Route, Outlet, Link } from 'react-router-dom';
import styled, { ThemeProvider } from 'styled-components';
import { GlobalStyles } from './styles/GlobalStyles.ts';
import { theme } from './styles/Theme';

import { LoginPage } from './pages/Login/index.tsx';
import { MainPage } from './pages/Main/index.tsx';
import { NewIssuePage } from './pages/NewIssue/index.tsx';
import { LabelsPage } from './pages/Labels/index.tsx';
import { MilestonesPage } from './pages/Milestones/index.tsx';
import { PageNotFound } from './pages/404.tsx';

type ThemeMode = 'light' | 'dark';

export const App = () => {
  /* 라이트모드/다크모드 테스트용 */
  const [currentMode, setCurrentMode] = useState<ThemeMode>('light');

  return (
    <>
      <ThemeProvider theme={theme[currentMode]}>
        <GlobalStyles />
        <BrowserRouter>
          <Routes>
            <Route>
              <Route path="/" element={<LoginPage />} />
              <Route path="/issues" element={<MainPage />} />
              <Route path="/issues/new" element={<NewIssuePage />} />
              <Route path="/milestones" element={<MilestonesPage />} />
              <Route path="/labels" element={<LabelsPage />} />
              <Route path="*" element={<PageNotFound />} />
            </Route>
          </Routes>
        </BrowserRouter>
        <StyledTest>환경 만들기</StyledTest>
      </ThemeProvider>
    </>
  );
};

const StyledTest = styled.p`
  font: ${({ theme: { font } }) => font.displayB32};
  color: ${({ theme: { color } }) => color.textWeak};
`;
