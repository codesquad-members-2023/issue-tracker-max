import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";

export default function SideBar() {
  return (
    <Container>
      <ItemList>
        <DropdownIndicator
          type={"none"}
          icon={"plus"}
          label={"담당자"}
          padding={"0px"}
          width={"224px"}
          height={"24px"}
        ></DropdownIndicator>
      </ItemList>
      <ItemList>
        <DropdownIndicator
          type={"none"}
          icon={"plus"}
          label={"레이블"}
          padding={"0px"}
          width={"224px"}
          height={"24px"}
        ></DropdownIndicator>
      </ItemList>
      <ItemList>
        <DropdownIndicator
          type={"none"}
          icon={"plus"}
          label={"마일스톤"}
          padding={"0px"}
          width={"224px"}
          height={"32px"}
        ></DropdownIndicator>
      </ItemList>
    </Container>
  );
}

const Container = styled.ul`
  display: flex;
  flex-direction: column;
  width: 288px;
  height: min-content;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
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
