import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";

type Props = {
  userImg: string;
  title: string;
};

export default function DropdownItem({ userImg, title }: Props) {
  return (
    <Container>
      {userImg && <UserProfileButton src={userImg} size={"small"} />}
      <Label>{title}</Label>
      <Checkbox type={"checkbox"}></Checkbox>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Label = styled.label``;

const Checkbox = styled.input``;
