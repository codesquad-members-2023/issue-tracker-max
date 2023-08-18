import { Routes, Route, useLocation } from 'react-router-dom';
import Header from './components/Header/Header';
import SignIn from './components/Sign/SignIn';
import SignUp from './components/Sign/SignUp';
import IssueList from './components/Issue/IssueList/IssueList';
import IssueCreate from './components/Issue/IssueCreate/IssueCreate';
import IssueDetail from './components/Issue/IssueDetail/IssueDetail';
import LabelList from './components/Label/LabelList';
import MilestoneList from './components/Milestone/MilestoneList';
import UserProvider from './components/Context/UserContext';
import ApiTest from './components/ApiTest';
import Footer from './components/Footer/Footer';

export default function App() {
  const location = useLocation();
  const hiddenHeaderRoutes = ['/sign-in', '/sign-up'];
  const withHeaderAndFooter = !hiddenHeaderRoutes.includes(location.pathname);

  return (
    <UserProvider>
      {withHeaderAndFooter && <Header />}
      <Routes>
        <Route path="/" element={<IssueList />} />
        <Route path="/sign-in" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/issue" element={<IssueList />} />
        <Route path="/issue-create" element={<IssueCreate />} />
        <Route path="/issue/:id" element={<IssueDetail />} />
        <Route path="/label" element={<LabelList />} />
        <Route path="/milestone" element={<MilestoneList />} />
        <Route path="/test" element={<ApiTest />} />
      </Routes>
      {withHeaderAndFooter && <Footer />}
    </UserProvider>
  );
}
