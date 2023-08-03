import { useEffect } from "react";
import {
  IssueTableHeader,
  // ChangeStateHeader,
} from "components/Header/IssueTableHeader";
import { IssueListHeader } from "components/Header/IssueListHeader";
import { IssuesTable } from "components/Table/IssuesTable";

export const IssuesPage = () => {
  useEffect(() => {
    fetch("http://3.39.194.109:8080/api/issues/open")
      .then((response) => response.json())
      .then((data) => console.log(data))
      .catch((error) => {
        console.error("Error:", error);
      });
  }, []);
  return (
    <>
      <IssueListHeader />
      <IssueTableHeader />
      {/* <ChangeStateHeader /> */}
      <IssuesTable />
    </>
  );
};
