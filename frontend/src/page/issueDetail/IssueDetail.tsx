import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { styled } from "styled-components";
import { getAccessToken } from "../../utils/localStorage";
import { IssueDetailBody } from "./IssueDetailBody";
import { IssueDetailHeader } from "./IssueDetailHeader";

export type IssueData = {
  id: number;
  title: string;
  content: string;
  status: "OPENED" | "CLOSED";
  statusModifiedAt: Date;
  createdAt: Date;
  modifiedAt: Date | null;
  reactions: {
    unicode: string;
    users: string[];
    selectedUserReactionId: number;
  }[];
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  assignees: {
    id: number;
    name: string;
    avatarUrl: string;
  }[];
  labels: {
    id: number;
    name: string;
    color: "LIGHT" | "DARK";
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
    reactions: {
      unicode: string;
      users: string[];
      selectedUserReactionId: number;
    }[];
  }[];
};

export function IssueDetail() {
  const navigatte = useNavigate();
  const { issueId } = useParams();
  const [issue, setIssue] = useState<IssueData>({
    id: 0,
    title: "",
    content: "",
    status: "OPENED",
    statusModifiedAt: new Date(),
    createdAt: new Date(),
    modifiedAt: null,
    reactions: [
      {
        unicode: "",
        users: [],
        selectedUserReactionId: 0,
      },
    ],
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
    const response = await fetch(`/api/issues/${issueId}`, {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const result = await response.json();

    if (result.code === 200) {
      setIssue(result.data);
      return;
    }
    if (result.code === 404) {
      navigatte("/404", { replace: true });
      return;
    }

    throw new Error(result.message);
  }, [issueId, navigatte]);

  useEffect(() => {
    fetchIssue();
  }, [fetchIssue]);

  return (
    <Div>
      <IssueDetailHeader fetchIssue={fetchIssue} {...issue} />
      <Line />
      <IssueDetailBody fetchIssue={fetchIssue} {...issue} />
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

const Line = styled.hr`
  width: 100%;
  border: none;
  border-top: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  margin: 0;
`;
