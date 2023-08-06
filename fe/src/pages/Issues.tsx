import { useState, useEffect } from "react";
import { IssueTableHeader } from "components/Table/IssueTableHeader";
import { IssueListHeader } from "components/Header/IssueListHeader";
import { IssueTableList } from "components/Table/IssueTableList";

export const IssuesPage = () => {
  const [isChecked, setIsChecked] = useState(false);

  const handleClickCheckBox = () => {
    setIsChecked(!isChecked);
  };

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
      <IssueTableHeader
        isChecked={isChecked}
        onClickCheckBox={handleClickCheckBox}
      />
      <IssueTableList />
    </>
  );
};
