import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";
import { useNavigate, useParams } from "react-router-dom";

type Props = {
  id: number;
  userImg?: string;
  itemName: string;
  closeDropdown(): void;
};

export default function DropdownItem({
  id,
  userImg = "/logo/profile.jpg",
  itemName,
  closeDropdown,
}: Props) {
  const navigate = useNavigate();
  const { filter } = useParams();
  const checkAssignee = () => {
    navigate(`/issues/${filter}&assigneeIds=${id}`);
    closeDropdown();
  };

  return (
    <Container>
      <Info>
        {userImg && (
          <UserProfileButton
            src={userImg}
            size={"small"}
            onClick={checkAssignee}
          />
        )}
        <Label>{itemName}</Label>
      </Info>
      <Checkbox type={"checkbox"}></Checkbox>
    </Container>
  );
}

const Container = styled.div`
  padding: 10px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border-top: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
`;

const Info = styled.div`
  display: flex;
  gap: 8px;
`;

const Label = styled.label``;

const Checkbox = styled.input``;
