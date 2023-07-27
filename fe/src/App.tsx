import { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import styled, { ThemeProvider } from 'styled-components';
import { GlobalStyles } from 'styles/GlobalStyles';
import { theme } from 'styles/Theme';

import { LoginPage } from 'pages/Login';
import { MainPage } from 'pages/Main';
import { NewIssuePage } from 'pages/NewIssue';
import { LabelsPage } from 'pages/Labels';
import { MilestonesPage } from 'pages/Milestones';
import { PageNotFound } from 'pages/404';

type ThemeMode = 'light' | 'dark';

export const App = () => {
  /* 라이트모드/다크모드 테스트용 */
  const [currentMode, setCurrentMode] = useState<ThemeMode>('light');
  console.log(setCurrentMode);

  return (
    <>
      <ThemeProvider theme={theme[currentMode]}>
        <GlobalStyles />
        {/* 라우터 테스트용 */}
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
