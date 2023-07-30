import { Header } from '@components/header/Header';
import { ThemeProvider } from '@emotion/react';
import { AddIssuePage } from '@pages/AddIssuePage';
import { IssueDetailPage } from '@pages/IssueDetailPage';
import { IssueListPage } from '@pages/IssueListPage';
import { LabelListPage } from '@pages/LabelListPage';
import { MileStoneListPage } from '@pages/MileStoneListPage';
import { SignPage } from '@pages/SignPage';
import { darkMode, lightMode } from '@styles/designSystem';
import { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

export const App: React.FC = () => {
  const [userId, setUserId] = useState();
  const [accessToken, setAccessToken] = useState();
  const [currentTheme, setCurrentTheme] = useState(true);
  const theme = currentTheme ? lightMode : darkMode;

  const resetUserId = () => {};
  const resetAccessToken = () => {};
  const toggleCurrentTheme = () => {};

  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <Routes>
          <Route path="/sign" element={<SignPage />} />
          <Route path="/" element={<Header />}>
            <Route index element={<IssueListPage />} />
            <Route path="add" element={<AddIssuePage />} />
            <Route path="issue/:id" element={<IssueDetailPage />} />
            <Route path="label" element={<LabelListPage />} />
            <Route path="milestone" element={<MileStoneListPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};
