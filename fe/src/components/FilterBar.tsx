import searchIcon from "@assets/icon/search.svg";
import {
  useIssuesFilter,
  useIssuesFilterDispatch,
} from "context/IssuesFilterContext";
import { ChangeEvent, useEffect, useState } from "react";
import { styled } from "styled-components";
import DropdownIndicator from "./Dropdown/DropdownIndicator";
import { DropdownItemType } from "./Dropdown/types";
import RadioGroup from "./common/Group/RadioGroup";

export default function FilterBar() {
  const { issuesFilter } = useIssuesFilter();
  const issuesFilterDispatch = useIssuesFilterDispatch();

  const [userInput, setUserInput] = useState(issuesFilter.text);

  // TODO: useEffect 내부에서 상태 변경하지 않도록 개선
  useEffect(() => {
    setUserInput(issuesFilter.text);
  }, [issuesFilter.text]);

  const onUserInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUserInput(e.target.value);
  };

  const onSubmit = (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    issuesFilterDispatch({
      type: "SET_FILTER_TEXT",
      payload: userInput,
    });
  };

  return (
    <StyledFilterBar>
      <FilterButtonContainer className="filter-button-container">
        <RadioGroup
          value={issuesFilter.state.filterBar}
          onChange={(newFilterBarOption) => {
            issuesFilterDispatch({
              type: "SET_FILTER_BAR",
              payload: newFilterBarOption,
            });
          }}>
          <DropdownIndicator
            displayName="필터"
            dropdownPanelVariant="filter"
            dropdownName="issue"
            dropdownList={filterOptions}
            dropdownPanelPosition="left"
            valueType="name"
          />
        </RadioGroup>
      </FilterButtonContainer>

      <FilterForm className="filter-form" onSubmit={onSubmit}>
        <img src={searchIcon} alt="Search Filter" />
        <FilterInput
          type="text"
          placeholder="Search all issues"
          value={userInput}
          onChange={onUserInputChange}
        />
      </FilterForm>
    </StyledFilterBar>
  );
}

const filterOptions: DropdownItemType[] = [
  { id: 0, variant: "plain", name: "open", content: "열린 이슈" },
  { id: 1, variant: "plain", name: "writtenByMe", content: "내가 작성한 이슈" },
  {
    id: 2,
    variant: "plain",
    name: "assignedToMe",
    content: "나에게 할당된 이슈",
  },
  {
    id: 3,
    variant: "plain",
    name: "commentedByMe",
    content: "내가 댓글을 남긴 이슈",
  },
  { id: 4, variant: "plain", name: "closed", content: "닫힌 이슈" },
];

const StyledFilterBar = styled.div`
  width: 100%;
  height: 40px;
  display: flex;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.m};

  &:focus-within {
    border: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.defaultActive}`};

    .filter-button-container {
      background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
    }

    .filter-form {
      background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
    }
  }
`;

const FilterButtonContainer = styled.div`
  height: 100%;
  padding-inline: 24px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-right: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-top-left-radius: ${({ theme: { radius } }) => radius.m};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.m};
`;

const FilterForm = styled.form`
  padding-inline: 24px;
  display: flex;
  align-items: center;
  flex-grow: 1;
  gap: 8px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  border-top-right-radius: ${({ theme: { radius } }) => radius.m};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.m};

  img {
    width: 16px;
    height: 16px;
    filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
  }
`;

const FilterInput = styled.input`
  width: 100%;
  height: 100%;
  background: none;
  border: none;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD16};
  outline: none;
`;
