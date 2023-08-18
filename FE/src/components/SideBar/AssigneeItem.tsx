import { styled } from "styled-components";

type Props = {
  imgUrl: string;
  userId: string;
};

export default function AssigneeItem({ imgUrl, userId }: Props) {
  return (
    <Container>
      <UserProfileImg src={imgUrl} />
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

const UserProfileImg = styled.img`
  width: 20px;
  height: 20px;
  border-radius: ${({ theme }) => theme.radius.half};
`;

const UserId = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;
