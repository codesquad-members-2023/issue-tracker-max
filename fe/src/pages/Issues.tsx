import { useState, useEffect } from "react";
import { IssueTableHeader } from "components/Table/IssueTableHeader";
import { IssueListHeader } from "components/Header/IssueListHeader";
import { IssueTableList } from "components/Table/IssueTableList";

interface IssueItem {
  id: number;
  title: string;
  author: string;
  assigneeProfiles?: string[];
  milestone?: string;
  createdAt: string;
  labels?: { name: string; backgroundColor: string; textColor: string }[];
}

export const IssuesPage = () => {
  const [isChecked, setIsChecked] = useState(false);
  const [labelCount, setLabelCount] = useState(0);
  const [milestoneCount, setMilestoneCount] = useState(0);
  const [openIssueCount, setOpenIssueCount] = useState(0);
  const [closedIssueCount, setClosedIssueCount] = useState(0);
  const [issuesListData, setIssuesListData] = useState<IssueItem[]>();

  useEffect(() => {
    fetch("http://43.200.169.143:8080/api/issues/open")
      .then((response) => response.json())
      .then((data) => {
        const {
          labelCount,
          milestoneCount,
          openIssueCount,
          closedIssueCount,
          ...issuesListData
        } = data;

        setLabelCount(labelCount);
        setMilestoneCount(milestoneCount);
        setOpenIssueCount(openIssueCount);
        setClosedIssueCount(closedIssueCount);
        setIssuesListData(issuesListData.issues);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }, []);

  // useEffect(() => {
  //   fetch("/profile/nag")
  //     .then((response) => response.text())
  //     .then((data) => {
  //       console.log(data);
  //     })
  //     .catch((error) => {
  //       console.error("Error:", error);
  //     });
  // }, []);

  const handleClickCheckBox = () => {
    setIsChecked(!isChecked);
  };

  if (!issuesListData) {
    return;
  }

  return (
    <>
      <IssueListHeader
        labelCount={labelCount}
        milestoneCount={milestoneCount}
      />
      <IssueTableHeader
        isChecked={isChecked}
        onClickCheckBox={handleClickCheckBox}
        openIssueCount={openIssueCount}
        closedIssueCount={closedIssueCount}
      />
      <IssueTableList issuesListData={issuesListData} />
    </>
  );
};
