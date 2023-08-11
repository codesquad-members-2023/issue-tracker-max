import { useLoaderData } from "react-router-dom";
import { IssueHeader } from "components/Header/IssueHeader";
import { IssueTable } from "components/Table/IssueTable";

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
  issues: IssueItem[] | null;
}

export const IssuesPage = () => {
  const data: IssuesPageData = useLoaderData() as IssuesPageData;

  const { labelCount, milestoneCount, ...issueTableData } = data;

  return (
    <>
      <IssueHeader labelCount={labelCount} milestoneCount={milestoneCount} />
      <IssueTable tableData={issueTableData} />
    </>
  );
};
