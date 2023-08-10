import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";

type Props = {
  id: number;
  userImg?: string;
  itemName: string;
  onClick(): void;
};

export default function DropdownItem({
  id,
  userImg = "/logo/profile.jpg",
  itemName,
  onClick,
}: Props) {
  return (
    <Container onClick={onClick}>
      <Info>
        {userImg && <UserProfileButton src={userImg} size={"small"} />}
        <Label>
          {itemName}
          {id}
        </Label>
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
