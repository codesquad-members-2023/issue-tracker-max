import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import AssigneeItem from "./AssigneeItem";
import LabelItem from "./LabelItem";
import MilestonesItem from "./MilestonesItem";

export default function SideBar() {
  return (
    <Container>
      <ItemList>
        <DropdownIndicator
          icon={"plus"}
          label={"담당자"}
          padding={"0px"}
          width={"224px"}
          height={"24px"}
          onClick={() => {}}
        ></DropdownIndicator>
        <AssigneeItem imgUrl={"/logo/profile.jpg"} userId={"litae"} />
      </ItemList>
      <ItemList>
        <DropdownIndicator
          icon={"plus"}
          label={"레이블"}
          padding={"0px"}
          width={"224px"}
          height={"24px"}
          onClick={() => {}}
        ></DropdownIndicator>
        <LabelContainer>
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
          <LabelItem label={"feat"} color={"#d93f0b"} />
        </LabelContainer>
      </ItemList>
      <ItemList>
        <DropdownIndicator
          icon={"plus"}
          label={"마일스톤"}
          padding={"0px"}
          width={"224px"}
          height={"24px"}
          onClick={() => {}}
        ></DropdownIndicator>
        <MilestonesItem title={"이슈트래커"} percentage={40} />
      </ItemList>
    </Container>
  );
}

const Container = styled.ul`
  display: flex;
  flex-direction: column;
  width: 288px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > li {
    &::after {
      content: "";
      position: absolute;
      top: 0px;
      left: 0px;
      width: 286px;
      height: 1px;
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.border.default};
    }
  }

  > li:first-child {
    &::after {
      content: "";
      position: absolute;
      width: 0px;
      height: 0px;
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.border.default};
    }
  }
`;

const ItemList = styled.li`
  position: relative;
  display: flex;
  gap: 16px;
  flex-direction: column;
  padding: 32px;
`;

const LabelContainer = styled.div`
  display: flex;
  gap: 8px;
  width: 100%;
  flex-wrap: wrap;
`;
