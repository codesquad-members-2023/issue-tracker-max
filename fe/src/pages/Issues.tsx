import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { IssueHeader } from "components/Header/IssueHeader";
import { IssueTable } from "components/Table/IssueTable";
import { FilterProvider } from "contexts/FilterProvider";

interface IssueItem {
  id: number;
  title: string;
  author: string;
  assigneeProfiles?: string[];
  milestone?: string;
  createdAt: string;
  labels?: { name: string; backgroundColor: string; textColor: string }[];
}

interface IssuesPageData {
  labelCount: number;
  milestoneCount: number;
  openIssueCount: number;
  closedIssueCount: number;
  filter: string;
  issues: IssueItem[] | null;
}

export const IssuesPage = () => {
  const location = useLocation();
  const [issuesData, setIssuesData] = useState<IssuesPageData>();

  useEffect(() => {
    const fetchData = async () => {
      const queryParams = new URLSearchParams(location.search);
      const q = queryParams.get("q") || "is:open";

      const response = await fetch(
        `http://43.200.169.143:8080/api/issues?q=${q}`,
      );
      const data = await response.json();

      setIssuesData(data);
    };

    fetchData();
  }, [location.search]);

  if (!issuesData) {
    return <p>Loading...</p>;
  }

  const { labelCount, milestoneCount, ...issueTableData } = issuesData;

  return (
    <FilterProvider>
      {issuesData ? (
        <>
          <IssueHeader
            labelCount={labelCount}
            milestoneCount={milestoneCount}
            filterText={filter}
          />
          <IssueTable tableData={issueTableData} />
        </>
      ) : (
        <p>Loading...</p>
      )}
    </FilterProvider>
  );
};
