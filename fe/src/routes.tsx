import { Route, Routes } from 'react-router-dom';
import { SignPage } from '@pages/SignPage';
import { RegisterPage } from '@pages/RegisterPage';
import { Header } from '@components/header/Header';
import { IssueListPage } from '@pages/IssueListPage';
import { IssueDetailPage } from '@pages/IssueDetailPage';
import { AddIssuePage } from '@pages/AddIssuePage';
import { LabelListPage } from '@pages/LabelListPage';
import { MileStoneListPage } from '@pages/MileStoneListPage';
// import { SignPage } from '@pages/SignPage';
import { NotFoundPage } from '@pages/NotFoundPage';
import {
  ADD_ISSUE_PAGE,
  ISSUE_DETAIL_PAGE,
  ISSUE_LIST_PAGE,
  LABEL_LIST_PAGE,
  MILESTONE_LIST_PAGE,
} from 'constants/PATH';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
};

export const AppRoutes: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  return (
    <Routes>
      <Route path="/sign" element={<SignPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<Header {...{ currentTheme, toggleTheme }} />}>
        <Route path={ISSUE_LIST_PAGE} element={<IssueListPage />} />
        <Route path={`${ISSUE_LIST_PAGE}/:query`} element={<IssueListPage />} />
        <Route path={ADD_ISSUE_PAGE} element={<AddIssuePage />} />
        <Route
          path={`${ISSUE_DETAIL_PAGE}/:id`}
          element={<IssueDetailPage />}
        />
        <Route path={LABEL_LIST_PAGE} element={<LabelListPage />} />
        <Route path={MILESTONE_LIST_PAGE} element={<MileStoneListPage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
};
