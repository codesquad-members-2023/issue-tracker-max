import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { IssueTableBody } from "../../components/issue/IssueTableBody";
import { IssueTableHeader } from "../../components/issue/IssueTableHeader";
import { MainHeader } from "./MainHeader";

export type SingleFilterData = {
  id: number;
  name: string;
  conditions: String[];
  selected: boolean;
};

type IssueDataState = {
  closedIssueCount: number;
  openedIssueCount: number;
  issues: IssueData[];
  multiFilters: Object;
  singleFilters: SingleFilterData[];
};

type LabelData = {
  name: string;
  background: string;
  color: string;
  description: string;
};

type MilestoneData = {
  id: number;
  name: string;
};

type UserData = {
  id: number;
  name: string;
  avatarUrl: string;
};

export type IssueData = {
  id: number;
  title: string;
  status: boolean;
  createdAt: Date;
  modifiedAt: Date | null;
  statusModifiedAt: Date;
  labels: LabelData[];
  milestone: MilestoneData;
  writer: UserData;
  assignees: UserData;
  commentCount: number;
};

export function Main() {
  const [issueData, setIssueData] = useState<IssueDataState>();

  useEffect(() => {
    const fetchData = async () => {
      const res = await fetch(
        "https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io/api/issues",
      );

      setIssueData(await res.json());
    };

    fetchData();
  }, []);

  return (
    <Div>
      {issueData && (
        <>
          <MainHeader
            singleFilters={issueData.singleFilters}
            milestoneCount={3}
            labelCount={2}
          />
          <MainBody>
            <IssueTableHeader
              openedIssueCount={issueData.openedIssueCount}
              closedIssueCount={issueData.closedIssueCount}
              multiFilters={issueData.multiFilters}
            />
            <IssueTableBody issues={issueData.issues} />
          </MainBody>
        </>
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  padding: 0px 80px;
`;

const MainBody = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
`;
