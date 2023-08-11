import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import calendarIcon from "@assets/icon/calendar.svg";
import editIcon from "@assets/icon/edit.svg";
import milestoneIcon from "@assets/icon/milestone.svg";
import trashIcon from "@assets/icon/trash.svg";
import MilestoneEditor, {
  MilestoneInfo,
} from "@components/Milestone/MilestoneEditor";
import Button from "@components/common/Button";
import ProgressBar from "@components/common/ProgressBar";
import { Milestone } from "@customTypes/index";
import { deleteMilestone, putMilestoneState } from "api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { TableBodyItem } from "../Table.style";

export default function MilestonesTableItem({
  milestone,
}: {
  milestone: Milestone;
}) {
  const navigate = useNavigate();

  const [isEditing, setIsEditing] = useState(false);

  const openEditor = () => setIsEditing(true);
  const closeEditor = () => setIsEditing(false);

  // TODO: 서버에서 받아온 string 타입을 Number 타입으로 언제 변환하고 타입을 어떻게 관리해야 할까?
  const {
    milestoneId,
    milestoneName,
    dueDate,
    description,
    openIssueCount,
    closedIssueCount,
    isOpen,
  } = milestone;

  const onMilestoneDelete = async () => {
    try {
      const res = await deleteMilestone(milestoneId);
      if (res.status === 204) {
        navigate(0);
        return;
      }
    } catch (error) {
      // TODO: error handling
      console.error(error);
    }
  };

  const onMilestoneClose = async () => {
    try {
      const res = await putMilestoneState(milestoneId, "closed");
      if (res.status === 200) {
        navigate(0);
        return;
      }
    } catch (error) {
      // TODO: error handling
      console.error(error);
    }
  };

  const onMilestoneOpen = async () => {
    try {
      const res = await putMilestoneState(milestoneId, "open");
      if (res.status === 200) {
        navigate(0);
        return;
      }
    } catch (error) {
      // TODO: error handling
      console.error(error);
    }
  };

  const milestoneInfo: MilestoneInfo = {
    milestoneName: milestoneName,
    dueDate,
    description,
  };

  return (
    <StyledMilestoneItem>
      {isEditing ? (
        <MilestoneEditor
          variant="edit"
          milestoneId={milestoneId}
          closeEditor={closeEditor}
          milestoneInfo={milestoneInfo}
        />
      ) : (
        <>
          <LeftWrapper>
            <div className="milestone-info">
              <img src={milestoneIcon} alt="마일스톤 아이콘" />
              <MilestoneName>{milestoneName}</MilestoneName>
              <MilestoneDueDate>
                <img src={calendarIcon} alt="캘린더 아이콘" />
                <span>{dueDate}</span>
              </MilestoneDueDate>
            </div>
            <div className="milestone-description">{description}</div>
          </LeftWrapper>

          <RightWrapper>
            <ButtonsContainer>
              {isOpen ? (
                <Button variant="ghost" size="S" onClick={onMilestoneClose}>
                  <img src={archiveIcon} alt="닫기" />
                  <span className="tab-button-text">닫기</span>
                </Button>
              ) : (
                <Button variant="ghost" size="S" onClick={onMilestoneOpen}>
                  <img src={alertIcon} alt="열기" />
                  <span className="tab-button-text">열기</span>
                </Button>
              )}
              <Button variant="ghost" size="S" onClick={openEditor}>
                <img src={editIcon} alt="편집" />
                <span className="tab-button-text">편집</span>
              </Button>
              <Button
                variant="ghost"
                size="S"
                className="delete"
                onClick={onMilestoneDelete}>
                <img src={trashIcon} alt="삭제" />
                <span className="tab-button-text">삭제</span>
              </Button>
            </ButtonsContainer>
            <ProgressBar
              variant="percent"
              name={milestoneName}
              openCount={Number(openIssueCount)}
              closeCount={Number(closedIssueCount)}
            />
          </RightWrapper>
        </>
      )}
    </StyledMilestoneItem>
  );
}

const StyledMilestoneItem = styled(TableBodyItem)`
  padding: 32px;
  gap: 32px;
`;

const LeftWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex-grow: 1;

  .milestone-info {
    display: flex;
    width: 100%;
    gap: 8px;

    > img {
      filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
    }
  }

  .milestone-description {
    font: ${({ theme: { font } }) => font.availableMD16};
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const RightWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-width: 244px;
  align-items: flex-end;
`;

const ButtonsContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 24px;

  .delete {
    img {
      filter: ${({ theme: { filter } }) => filter.dangerTextDefault};
    }
    span {
      color: ${({ theme: { danger } }) => danger.text.default};
    }
  }
`;

const MilestoneName = styled.span`
  font: ${({ theme: { font } }) => font.availableMD16};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;

const MilestoneDueDate = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme: { font } }) => font.availableMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
`;
