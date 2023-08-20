import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import {
  AssigneesData,
  AssigneesList,
  AssigneesSideBar,
  Label,
  LabelsData,
  LabelsSideBar,
  Milestone,
  MilestonesData,
  MilestonesSideBar,
} from "../../type";
import DropdownPanel from "../DropdownPanel/DropdownPanel";
import SideBarItem from "./SideBarItem";
import AssigneeItem from "./AssigneeItem";
import LabelItem from "./LabelItem";
import MilestonesItem from "./MilestonesItem";
import { useEffect, useState } from "react";

type Props = {
  type?: "add" | "detail";
  issueId?: number;
  assignees?: AssigneesList[] | null;
  labels?: Label[] | null;
  milestone?: Milestone | null;
  handleAssignee?(assignee: AssigneesList, assigneeListId: number): void;
  handleLabel?(label: Label, labelListId: number): void;
  handleMilestone?(milestone: Milestone, milestoneListId: number): void;
};

export default function SideBar({
  type = "add",
  issueId,
  assignees,
  labels,
  milestone,
  handleAssignee,
  handleLabel,
  handleMilestone,
}: Props) {
  const [openFilterDropdown, setOpenFilterDropdown] = useState<
    "assignee" | "label" | "milestone" | "close"
  >("close");
  const [addAssigneesData, setAddAssigneesData] = useState<AssigneesData>();
  const [addLabelsData, setAddLabelsData] = useState<LabelsData>();
  const [addMilestonesData, setAddMilestonesData] = useState<MilestonesData>();

  const [assigneesData, setAssigneesData] = useState<AssigneesSideBar>();
  const [labelsData, setLabelsData] = useState<LabelsSideBar>();
  const [milestonesData, setMilestonesData] = useState<MilestonesSideBar>();

  const closeDropdown = () => {
    setOpenFilterDropdown("close");
  };

  const showAssigneesDropdown = () => {
    setOpenFilterDropdown("assignee");
  };

  const showLabelsDropdown = () => {
    setOpenFilterDropdown("label");
  };

  const showMilestonesDropdown = () => {
    setOpenFilterDropdown("milestone");
  };

  const addNewAssignees = async (memberId: number) => {
    const url = `http://3.34.141.196/api/issues/${issueId}/assignees`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const postData = {
      memberId: memberId,
    };

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(postData),
      });

      if (response.status === 200) {
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  const addNewLabel = async (labelId: number) => {
    const url = `http://3.34.141.196/api/issues/${issueId}/assigned-labels`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const postData = {
      labelId: labelId,
    };

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(postData),
      });

      if (response.status === 200) {
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  const addNewMilestone = async (milestoneId: number) => {
    const url = `http://3.34.141.196/api/issues/${issueId}/milestone`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const patchData = {
      milestoneId: milestoneId,
    };

    try {
      const response = await fetch(url, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  const deleteAssignees = async (assigneeId: number) => {
    const url = `http://3.34.141.196/api/issues/${issueId}/assignees/${assigneeId}`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    try {
      const response = await fetch(url, {
        method: "DELETE",
        headers: headers,
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  const deleteLabel = async (labelId: number) => {
    const url = `http://3.34.141.196/api/issues/${issueId}/assigned-labels/${labelId}`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    try {
      const response = await fetch(url, {
        method: "DELETE",
        headers: headers,
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  useEffect(() => {
    if (openFilterDropdown === "close") {
      return;
    }

    if (type === "detail") {
      const headers = new Headers();
      const accessToken = localStorage.getItem("accessToken");
      headers.append("Authorization", `Bearer ${accessToken}`);
      const URL = `http://3.34.141.196/api/issues/${issueId}/${openFilterDropdown}-candidates`;
      const fetchData = async () => {
        try {
          const response = await fetch(URL, { headers });
          if (!response.ok) {
            throw new Error("데이터를 가져오는 데 실패했습니다.");
          }
          const jsonData = await response.json();
          openFilterDropdown === "assignee"
            ? setAssigneesData(jsonData)
            : openFilterDropdown === "label"
            ? setLabelsData(jsonData)
            : setMilestonesData(jsonData);
        } catch (error) {
          console.log("error");
        }
      };
      fetchData();
    }

    if (type === "add") {
      const headers = new Headers();
      const accessToken = localStorage.getItem("accessToken");
      headers.append("Authorization", `Bearer ${accessToken}`);
      const URL = `http://3.34.141.196/api/issues/${openFilterDropdown}s`;
      const fetchData = async () => {
        try {
          const response = await fetch(URL, { headers });
          if (!response.ok) {
            throw new Error("데이터를 가져오는 데 실패했습니다.");
          }
          const jsonData = await response.json();
          openFilterDropdown === "assignee"
            ? setAddAssigneesData(jsonData)
            : openFilterDropdown === "label"
            ? setAddLabelsData(jsonData)
            : setAddMilestonesData(jsonData);
        } catch (error) {
          console.log("error");
        }
      };
      fetchData();
    }
  }, [openFilterDropdown]);
  return (
    <Container>
      {type === "detail" && (
        <DetailSideBar>
          <ItemList>
            <AssigneesDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"담당자"}
                width={"224px"}
                height={"24px"}
                onClick={showAssigneesDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "assignee" && assigneesData && (
                <DropdownPanel
                  title={"담당자 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {assigneesData.assignees &&
                    assigneesData.assignees.map((assignee) => (
                      <SideBarItem
                        key={assignee.id}
                        imgSource={assignee.profileImageUrl}
                        itemName={assignee.nickname}
                        isChecked={true}
                        onClick={() => {
                          deleteAssignees(assignee.id);
                        }}
                      />
                    ))}
                  {assigneesData.members &&
                    assigneesData.members.map((assignee) => (
                      <SideBarItem
                        key={assignee.id}
                        imgSource={assignee.profileImageUrl}
                        itemName={assignee.nickname}
                        isChecked={false}
                        onClick={() => {
                          addNewAssignees(assignee.id);
                        }}
                      />
                    ))}
                </DropdownPanel>
              )}
            </AssigneesDropdown>
            {assignees &&
              assignees.map((assignee) => (
                <AssigneeItem
                  key={assignee.id}
                  imgUrl={assignee.profileImageUrl}
                  userId={assignee.nickname}
                />
              ))}
          </ItemList>
          <ItemList>
            <LabelsDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"레이블"}
                width={"224px"}
                height={"24px"}
                onClick={showLabelsDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "label" && labelsData && (
                <DropdownPanel
                  title={"레이블 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {labelsData.assignedLabels &&
                    labelsData.assignedLabels.map((label) => (
                      <SideBarItem
                        key={label.id}
                        color={label.backgroundColor}
                        itemName={label.title}
                        isChecked={true}
                        onClick={() => {
                          deleteLabel(label.id);
                        }}
                      />
                    ))}
                  {labelsData.labels &&
                    labelsData.labels.map((label) => (
                      <SideBarItem
                        key={label.id}
                        color={label.backgroundColor}
                        itemName={label.title}
                        isChecked={false}
                        onClick={() => {
                          addNewLabel(label.id);
                        }}
                      />
                    ))}
                  {labelsData.assignedLabels.length === 0 &&
                    labelsData.labels.length === 0 && (
                      <EmptyContent>
                        <Info>생성된 레이블이 없습니다</Info>
                      </EmptyContent>
                    )}
                </DropdownPanel>
              )}
            </LabelsDropdown>
            {labels && (
              <LabelList>
                {labels.map((label) => (
                  <LabelItem
                    key={label.id}
                    label={label.title}
                    backgroundColor={label.backgroundColor}
                    textColor={label.textColor}
                  />
                ))}
              </LabelList>
            )}
          </ItemList>
          <ItemList>
            <MilestonesDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"마일스톤"}
                width={"224px"}
                height={"24px"}
                onClick={showMilestonesDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "milestone" && milestonesData && (
                <DropdownPanel
                  title={"마일스톤 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {milestonesData.assignedMilestones &&
                    milestonesData.assignedMilestones.map((milestone) => (
                      <SideBarItem
                        key={milestone.id}
                        itemName={milestone.title}
                        isChecked={true}
                        onClick={() => {
                          addNewMilestone(milestone.id);
                        }}
                      />
                    ))}
                  {milestonesData.milestones &&
                    milestonesData.milestones.map((milestone) => (
                      <SideBarItem
                        key={milestone.id}
                        itemName={milestone.title}
                        isChecked={false}
                        onClick={() => {
                          addNewMilestone(milestone.id);
                        }}
                      />
                    ))}
                  {milestonesData.assignedMilestones.length === 0 &&
                    milestonesData.milestones.length === 0 && (
                      <EmptyContent>
                        <Info>생성된 마일스톤이 없습니다</Info>
                      </EmptyContent>
                    )}
                </DropdownPanel>
              )}
            </MilestonesDropdown>
            {milestone && (
              <MilestonesItem
                title={milestone.title}
                percentage={milestone.progress}
              />
            )}
          </ItemList>
        </DetailSideBar>
      )}
      {type === "add" && (
        <DetailSideBar>
          <ItemList>
            <AssigneesDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"담당자"}
                width={"224px"}
                height={"24px"}
                onClick={showAssigneesDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "assignee" && addAssigneesData && (
                <DropdownPanel
                  title={"담당자 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {addAssigneesData.assignees &&
                    addAssigneesData.assignees.map((assigneeList) => (
                      <SideBarItem
                        key={assigneeList.id}
                        imgSource={assigneeList.profileImageUrl}
                        itemName={assigneeList.nickname}
                        isChecked={assignees!.some(
                          (assignee) => assignee.id === assigneeList.id,
                        )}
                        onClick={() => {
                          handleAssignee!(assigneeList, assigneeList.id);
                        }}
                      />
                    ))}
                </DropdownPanel>
              )}
            </AssigneesDropdown>
            {assignees &&
              assignees.map((assignee) => (
                <AssigneeItem
                  key={assignee.id}
                  imgUrl={assignee.profileImageUrl}
                  userId={assignee.nickname}
                />
              ))}
          </ItemList>
          <ItemList>
            <LabelsDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"레이블"}
                width={"224px"}
                height={"24px"}
                onClick={showLabelsDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "label" && addLabelsData && (
                <DropdownPanel
                  title={"레이블 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {addLabelsData.labels &&
                    addLabelsData.labels.map((labelList) => (
                      <SideBarItem
                        key={labelList.id}
                        color={labelList.backgroundColor}
                        itemName={labelList.title}
                        isChecked={labels!.some(
                          (label) => label.id === labelList.id,
                        )}
                        onClick={() => {
                          handleLabel!(labelList, labelList.id);
                        }}
                      />
                    ))}

                  {addLabelsData.labels.length === 0 && (
                    <EmptyContent>
                      <Info>생성된 레이블이 없습니다</Info>
                    </EmptyContent>
                  )}
                </DropdownPanel>
              )}
            </LabelsDropdown>
            {labels && (
              <LabelList>
                {labels.map((label) => (
                  <LabelItem
                    key={label.id}
                    label={label.title}
                    backgroundColor={label.backgroundColor}
                    textColor={label.textColor}
                  />
                ))}
              </LabelList>
            )}
          </ItemList>
          <ItemList>
            <MilestonesDropdown>
              <DropdownIndicator
                icon={"plus"}
                label={"마일스톤"}
                width={"224px"}
                height={"24px"}
                onClick={showMilestonesDropdown}
              ></DropdownIndicator>
              {openFilterDropdown === "milestone" && addMilestonesData && (
                <DropdownPanel
                  title={"마일스톤 추가"}
                  top={"30px"}
                  left={"-8px"}
                  closeDropdown={closeDropdown}
                >
                  {addMilestonesData.milestones &&
                    addMilestonesData.milestones.map((milestoneList) => (
                      <SideBarItem
                        key={milestoneList.id}
                        itemName={milestoneList.title}
                        isChecked={milestoneList.id === milestone?.id}
                        onClick={() => {
                          closeDropdown();
                          handleMilestone!(milestoneList, milestoneList.id);
                        }}
                      />
                    ))}
                  {addMilestonesData.milestones.length === 0 && (
                    <EmptyContent>
                      <Info>생성된 마일스톤이 없습니다</Info>
                    </EmptyContent>
                  )}
                </DropdownPanel>
              )}
            </MilestonesDropdown>
            {milestone && (
              <MilestonesItem
                key={milestone.id}
                title={milestone.title}
                percentage={milestone.progress}
              />
            )}
          </ItemList>
        </DetailSideBar>
      )}
    </Container>
  );
}

const Container = styled.ul``;

const ItemList = styled.li`
  position: relative;
  display: flex;
  gap: 16px;
  flex-direction: column;
  padding: 32px;
`;

const AssigneesDropdown = styled.div`
  position: relative;
`;

const LabelsDropdown = styled.div`
  position: relative;
`;

const MilestonesDropdown = styled.div`
  position: relative;
`;

const LabelList = styled.div`
  width: 100%;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
`;

const EmptyContent = styled.div`
  padding: 10px 16px;
  width: 100%;
  height: 44px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
`;

const Info = styled.span`
  font: ${({ theme }) => theme.font.availableMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const DetailSideBar = styled.div`
  display: flex;
  flex-direction: column;
  width: 288px;
  height: min-content;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  > li {
    &::after {
      content: "";
      position: absolute;
      top: 0px;
      left: 0px;
      width: 286px;
      height: 1px;
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.border.default};
    }
  }

  > li:first-child {
    &::after {
      content: "";
      position: absolute;
      width: 0px;
      height: 0px;
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.border.default};
    }
  }
`;
