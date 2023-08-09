import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import { User } from "@customTypes/index";
import useFetch from "@hooks/useFetch";
import { getUsers } from "api";
import styled from "styled-components";
import { Avatar } from "../Avatar";
import CheckboxGroup from "../Group/CheckboxGroup";
import { Container } from "./Container.style";

export default function AssigneeField({
  assignees,
  onAssigneeChange,
  onEditAssignees,
}: {
  assignees: Set<number>;
  onAssigneeChange: (assignees: Set<number>) => void;
  onEditAssignees?: () => void;
}) {
  const { data: userList } = useFetch(getUsers);

  const assigneeDropdownList: DropdownItemType[] | undefined = userList?.map(
    (user) => ({
      id: user.userAccountId,
      variant: "withImg",
      name: "assignee",
      content: user.username,
      imgSrc: user.profileUrl,
    })
  );

  const generateAssignees = (userList: User[]) => {
    const currentAssignees = userList.filter((user) =>
      assignees.has(user.userAccountId)
    );

    return currentAssignees.map((assignee) => {
      return (
        <Wrapper key={assignee.userAccountId}>
          <Avatar
            src={assignee.profileUrl}
            alt={`${assignee.username}`}
            $size="S"
          />
          <Content>{assignee.username}</Content>
        </Wrapper>
      );
    });
  };

  return (
    <Container>
      {assigneeDropdownList && (
        <CheckboxGroup values={assignees} onChange={onAssigneeChange}>
          <DropdownIndicator
            displayName="담당자"
            dropdownPanelVariant="select"
            dropdownName="assignee"
            dropdownList={assigneeDropdownList}
            dropdownPanelPosition="right"
            dropdownOption="multiple"
            outsideClickHandler={onEditAssignees}
          />
        </CheckboxGroup>
      )}
      {userList && generateAssignees(userList)}
    </Container>
  );
}

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

const Content = styled.span`
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;
