import { styled } from "styled-components";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import Input from "../common/Input/Input";
import DropdownPanel from "../DropdownPanel/DropdownPanel";
import DropdownItem from "../DropdownPanel/DropdownItem";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

type Props = {
  filterValue: string;
  onChange(e: React.ChangeEvent<HTMLInputElement>): void;
  handleEnterFilter(): void;
};

export default function FilterBar({
  filterValue,
  onChange,
  handleEnterFilter,
}: Props) {
  const navigate = useNavigate();
  const [openFilterDropdown, setOpenFilterDropdown] = useState<boolean>(false);

  const showFilterDropdown = () => {
    setOpenFilterDropdown(true);
  };

  const hideFilterDropdown = () => {
    setOpenFilterDropdown(false);
  };

  const goOpenIssuesPage = () => {
    setOpenFilterDropdown(false);
    navigate("/issues/isOpen=true");
  };

  const goClosedIssuesPage = () => {
    setOpenFilterDropdown(false);
    navigate("/issues/isOpen=false");
  };

  return (
    <Wrapper>
      <FilterButtonField>
        <FilterDropdown>
          <DropdownIndicator label={"이슈 필터"} onClick={showFilterDropdown} />
          {openFilterDropdown && (
            <DropdownPanel
              title={"이슈 필터"}
              top={"48px"}
              left={"0px"}
              closeDropdown={hideFilterDropdown}
            >
              <DropdownItem
                itemName={"열린 이슈"}
                value={"isOpen=true"}
                onClick={goOpenIssuesPage}
              />
              <DropdownItem
                itemName={"내가 작성한 이슈"}
                value={"authorId=1"}
                onClick={() => {}}
              />
              <DropdownItem
                itemName={"나에게 할당된 이슈"}
                value={"assigneesIds=1"}
                onClick={() => {}}
              />
              <DropdownItem
                itemName={"내가 댓글을 남긴 이슈"}
                value={"labelIds=1"}
                onClick={() => {}}
              />
              <DropdownItem
                itemName={"닫힌 이슈"}
                value={"isOpen=false"}
                onClick={goClosedIssuesPage}
              />
            </DropdownPanel>
          )}
        </FilterDropdown>
      </FilterButtonField>
      <FilterInputField>
        <FilterInputLabel htmlFor="filterInput">
          <FilterInputImg src={"/icons/search.svg"} alt={"search"} />
        </FilterInputLabel>
        <Input
          id={"filterInput"}
          placeholder={"Search all issues"}
          onChange={onChange}
          value={filterValue}
          handleEnterFilter={handleEnterFilter}
        />
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
  position: relative;
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

const FilterDropdown = styled.div``;
