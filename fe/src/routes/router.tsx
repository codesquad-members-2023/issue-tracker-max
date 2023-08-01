import { createBrowserRouter } from "react-router-dom";

import { Layout } from "pages/Layout";
import { LoginPage } from "pages/Login";
import { MainPage } from "pages/Main";
import { NewIssuePage } from "pages/NewIssue";
import { LabelsPage } from "pages/Labels";
import { MilestonesPage } from "pages/Milestones";
import { PageNotFound } from "pages/Error";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <PageNotFound />,
    children: [
      {
        path: "",
        element: <MainPage />,
      },
      {
        path: "login",
        element: <LoginPage />,
      },
      {
        path: "new",
        element: <NewIssuePage />,
      },
      {
        path: "Labels",
        element: <LabelsPage />,
      },
      {
        path: "milestones",
        element: <MilestonesPage />,
      },
      // {
      //   path: 'issues/:issuesId',
      //   element: <issuesDetail />,
      // },
    ],
  },
]);

export default router;
