import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import Input from "../Input/Input";

export default function FilterBar() {
  return (
    <Wrapper>
      <FilterButtonField>
        <DropdownIndicator
          text={"필터"}
          padding={"7px 24px"}
          onClick={() => {}}
        />
      </FilterButtonField>
      <FilterInputField>
        <FilterInputLabel htmlFor="filterInput">
          <FilterInputImg src={"/icons/search.svg"} alt={"search"} />
        </FilterInputLabel>
        <Input id={"filterInput"} inputLabel={"Search all issues"} />
      </FilterInputField>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: relative;
  display: flex;
  width: 560px;
  height: 40px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  &::after {
    content: "";
    position: absolute;
    top: 0;
    bottom: 0;
    left: 128px;
    width: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const FilterButtonField = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 128px;
  height: 100%;
  border-top-left-radius: ${({ theme }) => theme.radius.medium};
  border-bottom-left-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const FilterInputField = styled.form`
  padding: 8px 24px;
  width: 430px;
  display: flex;
  gap: 8px;
  align-items: center;
  border-top-right-radius: ${({ theme }) => theme.radius.medium};
  border-bottom-right-radius: ${({ theme }) => theme.radius.medium};
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.bold};
`;

const FilterInputLabel = styled.label`
  width: 16px;
  height: 16px;
`;

const FilterInputImg = styled.img`
  filter: ${({ theme }) => theme.filter.neutral.text.default};
`;
