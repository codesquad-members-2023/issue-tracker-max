import { styled } from "styled-components";
import { AssigneesProps } from "../../type";
import DropdownItem from "./DropdownItem";

type Props = {
  title: string;
  assigneesList: AssigneesProps;
  closeDropdown(): void;
};

export default function DropdownPanel({
  title,
  assigneesList,
  closeDropdown,
}: Props) {
  return (
    <Container>
      <Header>
        <Title>{title}</Title>
      </Header>
      {assigneesList &&
        assigneesList.assignees.map((assignee, key) => (
          <DropdownItem
            key={key}
            id={assignee.id}
            userImg={assignee.profile_image_url}
            itemName={assignee.nickname}
            closeDropdown={closeDropdown}
          />
        ))}
    </Container>
  );
}

const Container = styled.div`
  position: absolute;
  z-index: 100;
  top: 40px;
  right: 0px;
  width: 240px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > div:last-child {
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
  }
`;

const Header = styled.div`
  padding: 8px 16px;
  border-top-left-radius: inherit;
  border-top-right-radius: inherit;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const Title = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;
