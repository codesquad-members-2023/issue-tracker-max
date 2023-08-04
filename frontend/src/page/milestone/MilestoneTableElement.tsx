import { useState } from "react";
import { styled, useTheme } from "styled-components";
import { Button } from "../../components/Button";
import { Icon } from "../../components/Icon";
import { MilestoneData } from "./Milestone";
import { MilestoneEditor } from "./MilestoneEditor";

export function MilestoneTableElement({
  milestone,
  status,
}: {
  milestone: MilestoneData;
  status: "OPENED" | "CLOSED";
}) {
  const [isEditing, setIsEditing] = useState(false);

  const onClickEdit = () => {
    setIsEditing(true);
  };

  const closeEditor = () => {
    setIsEditing(false);
  };

  const changeStatus = () => {
    const id = milestone.id;

    if (status === "OPENED") {
      console.log(`close id: ${id}`);
    } else {
      console.log(`open id: ${id}`);
    }
  };

  const deleteMilestone = () => {
    const id = milestone.id;

    console.log(`delete id: ${id}`);
  };

  const theme = useTheme();
  const { issues } = milestone;
  const totalIssueCount = issues.closedIssueCount + issues.openedIssueCount;

  return (
    <Div>
      {isEditing ? (
        <MilestoneEditor
          onClickClose={closeEditor}
          type="edit"
          milestone={milestone}
        />
      ) : (
        <>
          <MilestoneInfo>
            <div>
              <Title>
                <Icon
                  name="milestone"
                  fill={theme.color.paletteBlue}
                  stroke={theme.color.paletteBlue}
                />
                <span>{milestone.name}</span>
              </Title>
              <Deadline>
                <Icon
                  name="calendar"
                  fill={theme.color.neutralTextWeak}
                  stroke={theme.color.neutralTextWeak}
                />
                <span>{milestone.deadline}</span>
              </Deadline>
            </div>
            <Description>{milestone.description}</Description>
          </MilestoneInfo>
          <MilestoneProgress>
            <Buttons>
              <Button
                size="S"
                icon="archive"
                buttonType="Ghost"
                onClick={changeStatus}
              >
                {status === "OPENED" ? "닫기" : "열기"}
              </Button>
              <Button
                size="S"
                icon="edit"
                buttonType="Ghost"
                onClick={onClickEdit}
              >
                편집
              </Button>
              <Button
                size="S"
                icon="trash"
                buttonType="Ghost"
                color={theme.color.dangerSurfaceDefault}
                onClick={deleteMilestone}
              >
                삭제
              </Button>
            </Buttons>
            <Progress value={issues.closedIssueCount} max={totalIssueCount} />
            <ProgressInfo>
              <span>
                {Math.floor((issues.closedIssueCount / totalIssueCount) * 100)}%
              </span>
              <span>
                열린 이슈 {issues.openedIssueCount} 닫힌 이슈{" "}
                {issues.closedIssueCount}
              </span>
            </ProgressInfo>
          </MilestoneProgress>
        </>
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  min-height: 96px;
  padding: 0 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  border-top: solid 1px ${({ theme }) => theme.color.neutralBorderDefault};
`;

const MilestoneInfo = styled.div`
  width: 932px;
  display: flex;
  flex-direction: column;
  gap: 8px;

  & div {
    display: flex;
    gap: 16px;
  }
`;

const Title = styled.div`
  display: flex;
  gap: 8px;

  & span {
    display: flex;
    justify-content: center;
    align-items: center;
    font: ${({ theme }) => theme.font.availableMedium16};
    color: ${({ theme }) => theme.color.neutralTextStrong};
  }
`;

const Deadline = styled.div`
  display: flex;
  gap: 8px;

  & span {
    display: flex;
    justify-content: center;
    align-items: center;
    font: ${({ theme }) => theme.font.displayMedium12};
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;

const Description = styled.span`
  width: 870px;
  display: flex;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextWeak};
  flex: 1;
`;
const MilestoneProgress = styled.div`
  width: 244px;
  display: flex;
  flex-direction: column;
  align-items: end;
`;

const Buttons = styled.div`
  width: 210px;
  display: flex;
  gap: 24px;
`;

const Progress = styled.progress`
  width: 100%;
`;

const ProgressInfo = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;

  & span {
    font: ${({ theme }) => theme.font.displayMedium12};
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;
