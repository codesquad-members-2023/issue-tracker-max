import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Icon } from "components/Common/Icon/Icon";
import { IssueHeader } from "components/Header/IssueHeader";
import { IssueTable } from "components/Table/IssueTable";
import { Button } from "components/Common/Button/Button";
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
  const navigate = useNavigate();
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

  const { labelCount, milestoneCount, filter, ...issueTableData } = issuesData;

  const isFiltered = location.pathname.includes("/issues/filtered");

  const handleClearFilters = () => {
    navigate("/");
    // filter 리셋 해야함
  };

  return (
    <FilterProvider>
      {issuesData ? (
        <>
          <IssueHeader
            labelCount={labelCount}
            milestoneCount={milestoneCount}
            filterText={filter}
          />
          {isFiltered && (
            <Button variant="ghost" size="M" onClick={handleClearFilters}>
              <Icon icon="XSquare" />
              현재의 검색 필터 및 정렬 지우기
            </Button>
          )}
          <IssueTable tableData={issueTableData} />
        </>
      ) : (
        <p>Loading...</p>
      )}
    </FilterProvider>
  );
};
