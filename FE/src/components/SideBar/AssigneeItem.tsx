import { styled } from "styled-components";
import UserProfileButton from "../UserProfileButton/UserProfileButton";

type Props = {
  imgUrl: string;
  userId: string;
};

export default function AssigneeItem({ imgUrl, userId }: Props) {
  return (
    <Container>
      <UserProfileButton src={imgUrl} size={"small"} onClick={() => {}} />
      <UserId>{userId}</UserId>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  width: 224px;
  height: 20px;
`;

const UserId = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
