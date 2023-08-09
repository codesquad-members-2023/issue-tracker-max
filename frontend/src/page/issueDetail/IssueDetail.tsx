import { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { styled } from "styled-components";
import { IssueDetailHeader } from "./IssueDetailHeader";

type IssueData = {
  id: number;
  title: string;
  content: string;
  status: "OPENED" | "CLOSED";
  statusModifiedAt: Date;
  modifiedAt: Date | null;
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  assignees: {
    id: number;
    loginId: string;
    avatarUrl: string;
  }[];
  labels: {
    id: number;
    name: string;
    color: string;
    background: string;
  }[];
  milestone: {
    id: number;
    name: string;
    issues: {
      openedIssueCount: number;
      closedIssueCount: number;
    };
  } | null;
  comments: {
    id: number;
    userId: string;
    avatarUrl: string;
    content: string;
    createdAt: Date;
    modifiedAt: Date | null;
  }[];
};

export function IssueDetail() {
  const { issueId } = useParams();
  const [issue, setIssue] = useState<IssueData>({
    id: 0,
    title: "",
    content: "",
    status: "OPENED",
    statusModifiedAt: new Date(),
    modifiedAt: null,
    writer: {
      id: 0,
      name: "",
      avatarUrl: "",
    },
    assignees: [],
    labels: [],
    milestone: {
      id: 0,
      name: "",
      issues: {
        openedIssueCount: 0,
        closedIssueCount: 0,
      },
    },
    comments: [],
  });

  const fetchIssue = useCallback(async () => {
    const response = await fetch(`/api/issues/${issueId}`);
    const result = await response.json();

    setIssue(result.data);
  }, [issueId]);

  useEffect(() => {
    fetchIssue();
  }, [fetchIssue]);

  return (
    <Div>
      <IssueDetailHeader fetchIssue={fetchIssue} {...issue} />
      {/* <IssueDetailBody /> */}
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 24px;
`;
