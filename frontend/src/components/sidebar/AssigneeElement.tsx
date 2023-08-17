import { styled } from "styled-components";
import { Avatar } from "../Avatar";

type AssigneeElementProps = {
  name: string;
  profile?: string;
};

export function AssigneeElement({ name, profile }: AssigneeElementProps) {
  return (
    <Div>
      <Avatar size="S" src={profile} userId={name} />
      <Text>{name}</Text>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  width: 100%;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
`;

const Text = styled.span`
  flex-grow: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;
