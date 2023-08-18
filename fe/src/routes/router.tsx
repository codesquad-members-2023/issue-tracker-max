import { createBrowserRouter } from "react-router-dom";

import { Layout } from "pages/Layout";
import { LoginPage } from "pages/Login";
import { SignUpPage } from "pages/SignUp";
import { IssuesPage } from "pages/Issues";
import { IssueDetailPage } from "pages/IssueDetail";
import { NewIssuePage } from "pages/NewIssue";
import { LabelsPage } from "pages/Labels";
import { MilestonesPage } from "pages/Milestones";
import { PageNotFound } from "pages/Error";
import { ProtectedLayout } from "pages/ProtectedLayout";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <PageNotFound />,
    children: [
      /* 필터된 결과 api 나오면 <IssuePage/> 관련 수정 */
      {
        path: "",
        element: <IssuesPage />,
      },
      {
        path: "issues/:status",
        element: <IssuesPage />,
      },
      {
        path: "login",
        element: <LoginPage />,
      },
      {
        path: "signup",
        element: <SignUpPage />,
      },
      {
        path: "",
        element: <ProtectedLayout />,
        children: [
          {
            path: "new",
            element: <NewIssuePage />,
          },
        ],
      },
      {
        path: "labels",
        element: <LabelsPage />,
        loader: async () => {
          const response = await fetch("http://43.200.169.143:8080/api/labels");
          const data = await response.json();
          return data;
        },
      },
      {
        path: "milestones/:status",
        element: <MilestonesPage />,
        loader: async ({ params }) => {
          const response = await fetch(
            `http://43.200.169.143:8080/api/milestones/${params.status}`,
          );
          const data = await response.json();
          return data;
        },
      },
      {
        path: "detail/:issuesId",
        element: <IssueDetailPage />,
      },
    ],
  },
]);

export default router;
