import { Navigate, Outlet, Route, Routes } from 'react-router-dom';
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
import { getAccessToken } from 'apis/localStorage';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
};

export const AppRoutes: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  return (
    <Routes>
      <Route path={PATH.SIGN_PAGE} element={<SignPage />} />
      <Route path={PATH.REGISTER_PAGE} element={<RegisterPage />} />
      <Route
        path={`/${PATH.OAUTH_LOADING_PAGE}/*`}
        element={<OAuthLoadingPage />}
      />
      <Route element={<Header {...{ currentTheme, toggleTheme }} />}>
        <Route path="/*" element={<NotFoundPage />} />
        <Route element={<PrivateRoute />}>
          <Route path={PATH.ISSUE_LIST_PAGE} element={<IssueListPage />} />
          <Route path={PATH.ADD_ISSUE_PAGE} element={<AddIssuePage />} />
          <Route
            path={`${PATH.ISSUE_DETAIL_PAGE}/:id`}
            element={<IssueDetailPage />}
          />
          <Route path={PATH.LABEL_LIST_PAGE} element={<LabelListPage />} />
          <Route path={PATH.MILESTONE_LIST_PAGE} element={<MileStoneListPage />} />
        </Route>
      </Route>
    </Routes>
  );
};

const PrivateRoute: React.FC = () => {
  const isLoggedIn = getAccessToken();

  if (!isLoggedIn) {
    alert('로그인이 필요한 페이지입니다.');

    return <Navigate to={PATH.SIGN_PAGE} />;
  }

  return <Outlet />;
};
