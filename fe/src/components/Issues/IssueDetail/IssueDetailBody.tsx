import Sidebar from "@components/common/Sidebar/Sidebar";
import { IssueDetails, IssueSidebar } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { compareSet } from "@utils/compareSet";
import { getIssueSidebar, postEditField } from "api";
import { useCallback, useEffect, useRef, useState } from "react";
import styled from "styled-components";
import IssueCommentContainer from "./IssueCommentContainer";

export default function IssueDetailBody({
  issueNumber,
  issueDetails,
  updateIssueDetails,
}: {
  issueNumber: number;
  issueDetails: IssueDetails;
  updateIssueDetails: () => void;
}) {
  const { data: issueSidebar, reFetch: updateIssueSidebar } =
    useFetch<IssueSidebar>(
      useCallback(() => getIssueSidebar(issueNumber), [issueNumber])
    );

  const [newIssueSidebar, setNewIssueSidebar] = useState<{
    assigneeIds: Set<number>;
    labelIds: Set<number>;
    milestoneId: number;
  }>({
    assigneeIds: new Set<number>(issueSidebar?.assigneeIds),
    labelIds: new Set<number>(issueSidebar?.labelIds),
    milestoneId: issueSidebar?.milestoneId || -1,
  });

  const prevIssueSidebar = useRef(newIssueSidebar);

  useEffect(() => {
    if (issueSidebar) {
      prevIssueSidebar.current = {
        assigneeIds: new Set<number>(issueSidebar.assigneeIds),
        labelIds: new Set<number>(issueSidebar.labelIds),
        milestoneId: issueSidebar.milestoneId,
      };

      setNewIssueSidebar({
        assigneeIds: new Set<number>(issueSidebar.assigneeIds),
        labelIds: new Set<number>(issueSidebar.labelIds),
        milestoneId: issueSidebar.milestoneId,
      });
    }
  }, [issueSidebar]);

  const onAssigneeChange = (assigneeIds: Set<number>) => {
    setNewIssueSidebar((prev) => ({ ...prev, assigneeIds }));
  };

  const onLabelChange = (labelIds: Set<number>) => {
    setNewIssueSidebar((prev) => ({ ...prev, labelIds }));
  };

  const onMilestoneChange = (milestoneId: number) => {
    setNewIssueSidebar((prev) => ({ ...prev, milestoneId }));
  };

  const onEditAssignees = async () => {
    try {
      const { addedElements, removedElements } = compareSet(
        prevIssueSidebar.current.assigneeIds,
        newIssueSidebar.assigneeIds
      );

      const isNotModified = !addedElements.length && !removedElements.length;
      if (isNotModified) return;

      const { status } = await postEditField(issueNumber, "assignees", {
        addUserAccountId: addedElements,
        removeUserAccountId: removedElements,
      });

      status === 200 && updateIssueSidebar();
    } catch (e) {
      // TODO: error handling
      console.log(e);
    }
  };

  const onEditLabels = async () => {
    try {
      const { addedElements, removedElements } = compareSet(
        prevIssueSidebar.current.labelIds,
        newIssueSidebar.labelIds
      );

      const isNotModified = !addedElements.length && !removedElements.length;
      if (isNotModified) return;

      const { status } = await postEditField(issueNumber, "labels", {
        addLabelsId: addedElements,
        removeLabelsId: removedElements,
      });

      status === 200 && updateIssueSidebar();
    } catch (e) {
      // TODO: error handling
      console.log(e);
    }
  };

  // TODO: 마일스톤 없는 이슈 DropdownItem 추가
  const onEditMilestone = async () => {
    try {
      const isNotModified =
        prevIssueSidebar.current.milestoneId === newIssueSidebar.milestoneId;

      if (isNotModified) return;

      const { status } = await postEditField(issueNumber, "milestone", {
        milestoneId: newIssueSidebar.milestoneId,
      });

      status === 200 && updateIssueSidebar();
    } catch (e) {
      // TODO: error handling
      console.log(e);
    }
  };

  return (
    <StyledIssueDetailBody>
      <IssueCommentContainer
        {...{
          issueNumber,
          issueDetails,
          updateIssueDetails,
        }}
      />
      <Sidebar
        {...{
          assignees: newIssueSidebar.assigneeIds,
          labels: newIssueSidebar.labelIds,
          milestone: newIssueSidebar.milestoneId,
          onAssigneeChange,
          onLabelChange,
          onMilestoneChange,
          onEditAssignees,
          onEditLabels,
          onEditMilestone,
        }}
      />
    </StyledIssueDetailBody>
  );
}

const StyledIssueDetailBody = styled.div`
  width: 100%;
  padding-top: 24px;
  display: flex;
  justify-content: center;
  gap: 32px;
  border-top: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
`;
