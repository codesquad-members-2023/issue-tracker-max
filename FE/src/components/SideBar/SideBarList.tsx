import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import AssigneeItem from "./AssigneeItem";

type Props = {
  title: "담당자" | "레이블" | "마일스톤";
};

export default function SideBarList({ title }: Props) {
  return (
    <Container>
      <DropdownIndicator
        icon={"plus"}
        label={title}
        padding={"0px"}
        width={"224px"}
        height={"24px"}
        onClick={() => {}}
      ></DropdownIndicator>
      <AssigneeItem imgUrl={"/logo/profile.jpg"} userId={"litae"} />
    </Container>
  );
}

const Container = styled.li``;
