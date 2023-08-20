import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import CheckboxGroup from "@components/common/Group/CheckboxGroup";
import RadioGroup from "@components/common/Group/RadioGroup";
import useFetch from "@hooks/useFetch";
import { getLabels, getMilestones, getUsers } from "api";
import {
  useIssuesFilter,
  useIssuesFilterDispatch,
} from "context/IssuesFilterContext";
import styled from "styled-components";

export default function IssuesFiltersDropdowns() {
  const { data: userList } = useFetch(getUsers);
  const { data: labelList } = useFetch(getLabels);
  const { data: milestonesList } = useFetch(getMilestones);

  const { issuesFilter } = useIssuesFilter();
  const issuesFilterDispatch = useIssuesFilterDispatch();

  const userDropdownList: DropdownItemType[] =
    userList?.map((user) => ({
      id: user.userAccountId,
      variant: "withImg",
      name: "assignee",
      content: user.username,
      imgSrc: user.profileUrl,
    })) || [];

  const labelDropdownList: DropdownItemType[] =
    labelList?.map((label) => ({
      id: label.labelId,
      variant: "withColor",
      name: "label",
      content: label.name,
      colorFill: label.backgroundColor,
    })) || [];

  const milestoneDropdownList: DropdownItemType[] =
    milestonesList?.map((milestone) => ({
      id: milestone.milestoneId,
      variant: "plain",
      name: "milestone",
      content: milestone.milestoneName,
    })) || [];

  return (
    <StyledDropdownWrapper>
      <CheckboxGroup
        values={issuesFilter.state.assignees}
        onChange={(newAssignees) =>
          issuesFilterDispatch({
            type: "SET_ASSIGNEES",
            payload: newAssignees,
          })
        }>
        <DropdownIndicator
          displayName="담당자"
          dropdownPanelVariant="filter"
          dropdownName="assignee"
          dropdownList={userDropdownList}
          dropdownOption="multiple"
          dropdownPanelPosition="right"
          valueType="content"
        />
      </CheckboxGroup>
      <CheckboxGroup
        values={issuesFilter.state.labels}
        onChange={(newLabels) =>
          issuesFilterDispatch({ type: "SET_LABELS", payload: newLabels })
        }>
        <DropdownIndicator
          displayName="레이블"
          dropdownPanelVariant="filter"
          dropdownName="label"
          dropdownList={labelDropdownList}
          dropdownOption="multiple"
          dropdownPanelPosition="right"
          valueType="content"
        />
      </CheckboxGroup>
      <RadioGroup
        value={issuesFilter.state.milestone}
        onChange={(newMilestone) => {
          issuesFilterDispatch({
            type: "SET_MILESTONE",
            payload: newMilestone,
          });
        }}>
        <DropdownIndicator
          displayName="마일스톤"
          dropdownPanelVariant="filter"
          dropdownName="milestone"
          dropdownList={milestoneDropdownList}
          dropdownPanelPosition="right"
          valueType="content"
        />
      </RadioGroup>
      <RadioGroup
        value={issuesFilter.state.author}
        onChange={(newAuthor) =>
          issuesFilterDispatch({ type: "SET_AUTHOR", payload: newAuthor })
        }>
        <DropdownIndicator
          displayName="작성자"
          dropdownPanelVariant="filter"
          dropdownName="author"
          dropdownList={userDropdownList}
          dropdownPanelPosition="right"
          valueType="content"
        />
      </RadioGroup>
    </StyledDropdownWrapper>
  );
}

const StyledDropdownWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 32px;
`;
