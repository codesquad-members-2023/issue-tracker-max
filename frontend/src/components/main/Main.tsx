import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { IssueTableBody } from "../issue/IssueTableBody";
import { IssueTableHeader } from "../issue/IssueTableHeader";
import { MainHeader } from "./MainHeader";

export type SingleFilter = {
  id: number;
  name: string;
  conditions: String[];
  selected: boolean;
};

type IssueDataType = {
  closedIssueCount: number;
  openedIssueCount: number;
  issues: IssueType[];
  multiFilters: Object;
  singleFilters: SingleFilter[];
};

type LabelType = {
  name: string;
  background: string;
  color: string;
  description: string;
};

type MilestoneType = {
  id: number;
  name: string;
};

type UserType = {
  id: number;
  name: string;
  avatarUrl: string;
};

export type IssueType = {
  id: number;
  title: string;
  status: boolean;
  createdAt: Date;
  modifiedAt: Date | null;
  statusModifiedAt: Date;
  labels: LabelType[];
  milestone: MilestoneType;
  writer: UserType;
  assignees: UserType[];
  commentCount: number;
};

export function Main() {
  const [issueData, setIssueData] = useState<IssueDataType>();

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
