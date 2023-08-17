import { Route, Routes } from 'react-router-dom';
import { SignPage } from '@pages/SignPage';
import { RegisterPage } from '@pages/RegisterPage';
import { Header } from '@components/header/Header';
import { IssueListPage } from '@pages/IssueListPage';
import { IssueDetailPage } from '@pages/IssueDetailPage';
import { AddIssuePage } from '@pages/AddIssuePage';
import { LabelListPage } from '@pages/LabelListPage';
import { MileStoneListPage } from '@pages/MileStoneListPage';
import { NotFoundPage } from '@pages/NotFoundPage';
import { OAuthLoadingPage } from '@pages/OAuthLoadingPage';
import { PATH } from 'constants/PATH';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
};

export const AppRoutes: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  return (
    <Routes>
      <Route index element={<SignPage />} />
      <Route path={PATH.REGISTER_PAGE} element={<RegisterPage />} />
      <Route element={<Header {...{ currentTheme, toggleTheme }} />}>
        <Route path={PATH.ISSUE_LIST_PAGE} element={<IssueListPage />} />
        <Route path={PATH.ADD_ISSUE_PAGE} element={<AddIssuePage />} />
        <Route
          path={`${PATH.ISSUE_DETAIL_PAGE}/:id`}
          element={<IssueDetailPage />}
        />
        <Route path={PATH.LABEL_LIST_PAGE} element={<LabelListPage />} />
        <Route
          path={PATH.MILESTONE_LIST_PAGE}
          element={<MileStoneListPage />}
        />
        <Route
          path={`/${PATH.OAUTH_LOADING_PAGE}/*`}
          element={<OAuthLoadingPage />}
        />
        <Route path="/*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
};
