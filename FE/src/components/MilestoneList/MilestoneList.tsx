import { styled } from "styled-components";
import ProgressIndicator from "../ProgressIndicator/ProgressIndicator";
import Button from "../common/Button/Button";
import { MilestoneData } from "../../type";
import { useState } from "react";
import EditMilestone from "./EditMilestone";

export default function MilestoneList({
  id,
  title,
  progress,
  deadline,
  isOpen,
  description,
  openIssueCount,
  closeIssueCount,
}: MilestoneData) {
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const [milestoneTitle, setMilestoneTitle] = useState<string>(title);
  const [milestoneDeadline, setMilestoneDeadline] = useState<string>(deadline);
  const [milestoneDescription, setMilestoneDescription] =
    useState<string>(description);

  const editMilestone = () => {
    setIsEdit(true);
  };

  const confirmEdit = () => {
    setIsEdit(false);
  };

  const cancelEditMilestone = () => {
    setMilestoneTitle(title);
    setMilestoneDeadline(deadline);
    setMilestoneDescription(description);
    setIsEdit(false);
  };

  const handleTitleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMilestoneTitle(e.target.value);
  };

  const handleDeadlineInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setMilestoneDeadline(e.target.value);
  };

  const handleDescriptionInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setMilestoneDescription(e.target.value);
  };

  const closeMilestone = async () => {
    const URL = `http://3.34.141.196/api/milestones/${id}/open`;

    const patchData = {
      isOpen: false,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  const openMilestone = async () => {
    const URL = `http://3.34.141.196/api/milestones/${id}/open`;

    const patchData = {
      isOpen: true,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  const deleteMilestone = async () => {
    const URL = `http://3.34.141.196/api/milestones/${id}`; // DELETE 요청을 보낼 리소스 URL로 변경

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "DELETE",
        headers: headers,
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log(
          "DELETE 요청에 실패하였습니다. 상태 코드:",
          response.status,
        );
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  return (
    <Container>
      {!isEdit && (
        <Default>
          <TitleInfo>
            <TitleWrapper>
              <Title>
                <MilestoneIcon src={"/icons/milestone.svg"} />
                <Name>{milestoneTitle}</Name>
              </Title>
              <DeadlineWrapper>
                <DeadlineIcon src={"/icons/calendar.svg"} />
                <DeadlineDate>{milestoneDeadline}</DeadlineDate>
              </DeadlineWrapper>
            </TitleWrapper>
            <DescriptionWrapper>
              <Description>{milestoneDescription}</Description>
            </DescriptionWrapper>
          </TitleInfo>
          <Info>
            <ButtonTap>
              <Button
                icon={"archive"}
                label={isOpen ? "닫기" : "열기"}
                type={"ghost"}
                height={"32px"}
                onClick={isOpen ? closeMilestone : openMilestone}
              />
              <Button
                icon={"edit"}
                label={"편집"}
                type={"ghost"}
                height={"32px"}
                onClick={editMilestone}
              />
              <Button
                icon={"trash"}
                label={"삭제"}
                type={"ghost"}
                height={"32px"}
                onClick={deleteMilestone}
              />
            </ButtonTap>
            <ProgressIndicator
              percentage={progress}
              openIssues={openIssueCount}
              closedIssues={closeIssueCount}
            />
          </Info>
        </Default>
      )}
      {isEdit && (
        <EditMilestone
          id={id}
          title={milestoneTitle}
          description={milestoneDescription}
          deadline={milestoneDeadline}
          onChangeTitle={handleTitleInputChange}
          onChangeDescription={handleDescriptionInputChange}
          onChangeDeadline={handleDeadlineInputChange}
          cancelEdit={cancelEditMilestone}
          confirmEdit={confirmEdit}
        />
      )}
    </Container>
  );
}

const Container = styled.li`
  position: relative;
  width: 100%;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    width: 1280px;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const Default = styled.div`
  padding: 16px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 104px;
`;

const TitleInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 932px;
  height: 56px;
`;

const TitleWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  width: 932px;
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  height: 24px;
`;

const Name = styled.span`
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;

const MilestoneIcon = styled.img`
  width: 16px;
  height: 16px;
  filter: ${({ theme }) => theme.filter.brand.text.weak};
`;

const DescriptionWrapper = styled.div``;

const DeadlineWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  height: 24px;
`;

const DeadlineIcon = styled.img`
  width: 16px;
  height: 16px;
`;

const DeadlineDate = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Info = styled.div`
  display: flex;
  flex-direction: column;
  align-items: end;
  gap: 8px;
  width: 244px;
`;

const Description = styled.p`
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const ButtonTap = styled.div`
  display: flex;
  justify-content: end;
  gap: 24px;
  width: 100%;
  height: 32px;
`;
