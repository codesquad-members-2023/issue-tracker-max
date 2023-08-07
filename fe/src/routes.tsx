import { Route, Routes } from 'react-router-dom';
import { Header } from '@components/header/Header';
import { AddIssuePage } from '@pages/AddIssuePage';
import { IssueDetailPage } from '@pages/IssueDetailPage';
import { IssueListPage } from '@pages/IssueListPage';
import { LabelListPage } from '@pages/LabelListPage';
import { MileStoneListPage } from '@pages/MileStoneListPage';
import { SignPage } from '@pages/SignPage';
import { NotFoundPage } from '@pages/NotFoundPage';

type Props = {
  currentTheme: ThemeType;
  toggleTheme: () => void;
};

export const AppRoutes: React.FC<Props> = ({ currentTheme, toggleTheme }) => {
  return (
    <Routes>
      <Route path="/sign" element={<SignPage />} />
      <Route path="/" element={<Header {...{ currentTheme, toggleTheme }} />}>
        <Route index element={<IssueListPage />} />
        <Route path=":query" element={<IssueListPage />} />
        <Route path="add" element={<AddIssuePage />} />
        <Route path="issue/:id" element={<IssueDetailPage />} />
        <Route path="label" element={<LabelListPage />} />
        <Route path="milestone" element={<MileStoneListPage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
};
