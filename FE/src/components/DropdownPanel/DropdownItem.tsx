import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";

type Props = {
  userImg?: string;
  itemName: string;
  onClick(): void;
};

export default function DropdownItem({
  userImg = "",
  itemName,
  onClick,
}: Props) {
  return (
    <Container onClick={onClick}>
      <Info>
        {userImg && <UserProfileButton src={userImg} size={"small"} />}
        <Label>{itemName}</Label>
      </Info>
      <Checkbox type={"checkbox"}></Checkbox>
    </Container>
  );
}

const Container = styled.button`
  padding: 10px 16px;
  width: 100%;
  z-index: 1000;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
`;

const Info = styled.div`
  display: flex;
  gap: 8px;
`;

const Label = styled.label``;

const Checkbox = styled.input``;
