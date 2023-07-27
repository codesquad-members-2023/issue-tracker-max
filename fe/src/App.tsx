import { Routes, Route, useLocation } from 'react-router-dom';
import Header from './components/Header/Header';
import SignIn from './components/Sign/SignIn';
import SignUp from './components/Sign/SignUp';
import IssueList from './components/Issue/IssueList/IssueList';
import IssueCreate from './components/Issue/IssueCreate/IssueCreate';
import IssueDetail from './components/Issue/IssueDetail/IssueDetail';
import Label from './components/Label/Label';
import Milestone from './components/Milestone/Milestone';

export default function App() {
  const location = useLocation();
  const hiddenHeaderRoutes = ['/', '/sign-in', '/sign-up'];
  const shouldShowHeader = !hiddenHeaderRoutes.includes(location.pathname);

  return (
    <>
      {shouldShowHeader && <Header />}
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/sign-in" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/issue" element={<IssueList />} />
        <Route path="/issue/create" element={<IssueCreate />} />
        <Route path="/issue/detail" element={<IssueDetail />} />
        <Route path="/label" element={<Label />} />
        <Route path="/milestone" element={<Milestone />} />
      </Routes>
    </>
  );
}
