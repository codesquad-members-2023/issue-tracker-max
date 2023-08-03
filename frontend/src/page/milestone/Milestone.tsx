import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { styled } from "styled-components";
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
  useEffect(() => {
    if (state !== "opened" && state !== "closed") {
      navigate("/404");
      return;
    }

    const fetchData = async () => {
      const res = await fetch(
        `https://8e24d81e-0591-4cf2-8200-546f93981656.mock.pstmn.io/api/milestones?state=${state}`,
      );

      setMilestonesRes(await res.json());
    };

    fetchData();
  }, [state]);

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
              <MilestoneEditor onClickClose={closeAddLabel} type="add" />
            </EditorWrapper>
          )}
          <MilestoneTable
            milestones={milestonesRes.milestones}
            openCount={milestonesRes.openedMilestoneCount}
            closeCount={milestonesRes.closedMilestoneCount}
            status={milestonesRes.status}
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
  margin-bottom: 24px;
`;
