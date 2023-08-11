import { Routes, Route, useLocation } from 'react-router-dom';
import { useState } from 'react';
import { Theme, ThemeProvider, css } from '@emotion/react';
import { themes } from './styles/styles';
import Header from './components/Header/Header';
import SignIn from './components/Sign/SignIn';
import SignUp from './components/Sign/SignUp';
import IssueList from './components/Issue/IssueList/IssueList';
import IssueCreate from './components/Issue/IssueCreate/IssueCreate';
import IssueDetail from './components/Issue/IssueDetail/IssueDetail';
import LabelList from './components/Label/LabelList';
import MilestoneList from './components/Milestone/MilestoneList';
import ThemeToggle from './components/ThemeToggle';

export default function App() {
  const location = useLocation();
  const hiddenHeaderRoutes = ['/sign-in', '/sign-up'];
  const shouldShowHeader = !hiddenHeaderRoutes.includes(location.pathname);

  const [darkMode, setDarkMode] = useState(false);
  const theme = darkMode ? themes.dark : themes.light;

  const onThemeToggleClick = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  return (
    <ThemeProvider theme={theme}>
      <div css={body(theme)}>
        {shouldShowHeader && <Header />}
        <Routes>
          <Route path="/" element={<IssueList />} />
          <Route path="/sign-in" element={<SignIn />} />
          <Route path="/sign-up" element={<SignUp />} />
          <Route path="/issue" element={<IssueList />} />
          <Route path="/issue-create" element={<IssueCreate />} />
          <Route path="/issue-detail/:id" element={<IssueDetail />} />
          <Route path="/label" element={<LabelList />} />
          <Route path="/milestone" element={<MilestoneList />} />
        </Routes>
        <ThemeToggle onClick={onThemeToggleClick} isDark={darkMode} />
      </div>
    </ThemeProvider>
  );
}

const body = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  height: 100vh;

  background-color: ${theme.neutral.surfaceDefault};

  & svg path {
    stroke: ${theme.neutral.textDefault};
  }
`;
