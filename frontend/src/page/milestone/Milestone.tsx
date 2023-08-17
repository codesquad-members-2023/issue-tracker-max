import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { styled } from "styled-components";
import { getAccessToken } from "../../utils/localStorage";
import { MilestoneEditor } from "./MilestoneEditor";
import { MilestoneHeader } from "./MilestoneHeader";
import { MilestoneTable } from "./MilestoneTable";

type IssueCounts = {
  openedIssueCount: number;
  closedIssueCount: number;
};

export type MilestoneData = {
  id: number;
  status: string;
  name: string;
  description: string;
  createdAt: string;
  modifiedAt: string | null;
  deadline: string;
  issues: IssueCounts;
};

type MilestonesResData = {
  openedMilestoneCount: number;
  closedMilestoneCount: number;
  labelCount: number;
  status: "OPENED" | "CLOSED";
  milestones: MilestoneData[];
};

export function Milestone() {
  const navigate = useNavigate();
  const { state = "opened" } = useParams();

  const [milestonesRes, setMilestonesRes] = useState<MilestonesResData>();
  const [isAdding, setIsAdding] = useState(false);

  const fetchData = useCallback(async () => {
    const res = await fetch(`/api/milestones?state=${state}`, {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const milestonesData = await res.json();

    if (milestonesData.code === 200) {
      setMilestonesRes(milestonesData.data);
    }
    // TODO : 에러 예외 처리
  }, [state]);

  useEffect(() => {
    if (state !== "opened" && state !== "closed") {
      navigate("/404");
      return;
    }
    fetchData();
  }, [state, navigate, fetchData]);

  const openAddMilestone = () => {
    setIsAdding(true);
  };

  const closeAddLabel = () => {
    setIsAdding(false);
  };

  return (
    <Div>
      {milestonesRes && (
        <>
          <MilestoneHeader
            onClick={openAddMilestone}
            isAdding={isAdding}
            labelCount={milestonesRes.labelCount}
            openedMilestoneCount={milestonesRes.openedMilestoneCount}
          />
          {isAdding && (
            <EditorWrapper>
              <MilestoneEditor
                fetchData={fetchData}
                onClickClose={closeAddLabel}
                type="add"
              />
            </EditorWrapper>
          )}
          <MilestoneTable
            milestones={milestonesRes.milestones}
            openCount={milestonesRes.openedMilestoneCount}
            closeCount={milestonesRes.closedMilestoneCount}
            status={milestonesRes.status}
            fetchData={fetchData}
          />
        </>
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: flex;
  flex-direction: column;
`;

const EditorWrapper = styled.div`
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background: ${({ theme }) => theme.color.neutralSurfaceStrong};
  margin-bottom: 24px;
  padding: 32px;
`;
