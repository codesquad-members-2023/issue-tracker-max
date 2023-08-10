import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { FetchedMilestone } from "../type";
import MilestoneList from "../components/MilestoneList/MilestoneList";
import EditMilestone from "../components/MilestoneList/EditMilestone";
import parseFilter from "../utils/parseFilter";

export default function MilestonesPage() {
  const navigate = useNavigate();
  const { state } = useParams();
  const [data, setData] = useState<FetchedMilestone | null>(null);
  const [isAdd, setIsAdd] = useState<boolean>(false);
  const [addTitle, setAddTitle] = useState<string>("");
  const [addDescription, setAddDescription] = useState<string>("");
  const [addDeadline, setAddDeadline] = useState<string>("");

  const goLabelsPage = () => {
    navigate("/labels");
  };

  const goMilestonesPage = () => {
    navigate("/milestones/isOpen=true");
    window.location.reload();
  };

  const goCloseMilestonesPage = () => {
    navigate("/milestones/isOpen=false");
    window.location.reload();
  };

  const openAdd = () => {
    setIsAdd(true);
  };

  const cancelAdd = () => {
    setIsAdd(false);
  };

  const handleTitleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddTitle(e.target.value);
  };

  const handleDeadlineInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setAddDeadline(e.target.value);
  };

  const handleDescriptionInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setAddDescription(e.target.value);
  };

  const createMilestone = async () => {
    const URL = "http://3.34.141.196/api/milestones";

    const data = {
      title: addTitle,
      description: addDescription,
      deadline: addDeadline,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        cancelAdd();
        navigate("/milestones/isOpen=true");
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          `http://3.34.141.196/api/milestones${parseFilter(state)}`,
        );
        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }
        const jsonData = await response.json();
        setData(jsonData);
      } catch (error) {
        console.log("error");
      }
    };

    fetchData();
  }, []);

  return (
    <Main>
      <Tap>
        <TapButtonWrapper>
          <Button
            icon={"label"}
            label={`레이블(${data?.metadata.totalLabelCount})`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goLabelsPage}
          />
          <Button
            icon={"milestone"}
            label={`마일스톤(${data?.metadata.totalMilestoneCount})`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goMilestonesPage}
          />
        </TapButtonWrapper>
        <Button
          icon={"plus"}
          label={"마일스톤 추가"}
          onClick={openAdd}
          disabled={isAdd}
        />
      </Tap>
      {isAdd && (
        <EditMilestone
          type={"add"}
          title={addTitle}
          description={addDescription}
          deadline={addDeadline}
          onChangeTitle={handleTitleInputChange}
          onChangeDescription={handleDescriptionInputChange}
          onChangeDeadline={handleDeadlineInputChange}
          cancelEdit={cancelAdd}
          confirmEdit={createMilestone}
        />
      )}
      <LabelsTable>
        <TableHeader>
          <SelectButtonTap>
            <Button
              icon={"alertCircle"}
              label={`열린 마일스톤(${data?.metadata.openMilestoneCount})`}
              type={"ghost"}
              size={"medium"}
              onClick={goMilestonesPage}
            />
            <Button
              icon={"archive"}
              label={`닫힌 마일스톤(${data?.metadata.closeMilestoneCount})`}
              type={"ghost"}
              size={"medium"}
              onClick={goCloseMilestonesPage}
            />
          </SelectButtonTap>
        </TableHeader>
        {data?.milestones?.map((milestone) => (
          <MilestoneList
            key={milestone.id}
            id={milestone.id}
            title={milestone.title}
            progress={milestone.progress}
            deadline={milestone.deadline}
            description={milestone.description}
            isOpen={milestone.isOpen}
            openIssueCount={milestone.openIssueCount}
            closeIssueCount={milestone.closeIssueCount}
          />
        ))}
        {data?.milestones?.length === 0 && (
          <EmptyList>
            <EmptyContent>생성된 마일스톤이 없습니다.</EmptyContent>
          </EmptyList>
        )}
      </LabelsTable>
    </Main>
  );
}

const Main = styled.div`
  padding: 32px 0px;
  display: flex;
  gap: 24px;
  flex-direction: column;
  width: 1280px;
`;

const Tap = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const TapButtonWrapper = styled.div`
  position: relative;
  display: flex;
  width: 320px;
  height: 40px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    bottom: 0;
    left: 159px;
    width: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
  > button {
    &:hover {
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.surface.bold};
    }
  }
  > button:first-child {
    border-top-right-radius: 0px;
    border-bottom-right-radius: 0px;
  }
  > button:last-child {
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
    font: ${({ theme }) => theme.font.selectedBold16};
    color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
    background-color: ${({ theme }) => theme.colorSystem.neutral.surface.bold};
  }
`;

const LabelsTable = styled.ul`
  width: 100%;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > li:last-child {
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
  }
`;
const TableHeader = styled.li`
  width: 100%;
  padding: 20px 32px;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
`;

const SelectButtonTap = styled.div`
  display: flex;
  gap: 24px;
`;

const EmptyList = styled.li`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 96px;
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

const EmptyContent = styled.span`
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;
