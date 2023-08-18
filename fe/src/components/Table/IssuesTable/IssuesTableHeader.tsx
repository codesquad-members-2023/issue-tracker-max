import alertIcon from "@assets/icon/alertCircle.svg";
import archiveIcon from "@assets/icon/archive.svg";
import DropdownIndicator from "@components/Dropdown/DropdownIndicator";
import { DropdownItemType } from "@components/Dropdown/types";
import RadioGroup from "@components/common/Group/RadioGroup";
import InputCheckbox from "@components/common/Input/InputCheckbox";
import TabBar from "@components/common/TabBar";
import { putIssuesIsOpen } from "api";
import {
  useIssuesFilter,
  useIssuesFilterDispatch,
} from "context/IssuesFilterContext";
import { useState } from "react";
import { styled } from "styled-components";
import { TableHeader } from "../Table.style";
import IssuesFiltersDropdowns from "./IssuesDropdownWrapper";

export default function IssuesTableHeader({
  numOpen,
  numClosed,
  isAllIssuesSelected,
  selectedIssueIds,
  toggleSelectAll,
  refetchIssuesList,
  deselectAllIssues,
}: {
  numOpen: number;
  numClosed: number;
  isAllIssuesSelected: boolean;
  selectedIssueIds: Set<number>;
  toggleSelectAll: () => void;
  refetchIssuesList: () => void;
  deselectAllIssues: () => void;
}) {
  const [issueStateDropdown, setIssueStateDropdown] = useState<
    "open" | "closed"
  >("open");

  const { issuesFilter } = useIssuesFilter();
  const issuesFilterDispatch = useIssuesFilterDispatch();

  const issuesStatus = issuesFilter.state.status!;

  const tabBarLeftInfo = {
    name: "열린 이슈",
    count: numOpen,
    iconSrc: alertIcon,
    callback: () => {
      issuesFilterDispatch({ type: "SET_FILTER_BAR", payload: "open" });
    },
  };

  const tabBarRightInfo = {
    name: "닫힌 이슈",
    count: numClosed,
    iconSrc: archiveIcon,
    callback: () => {
      issuesFilterDispatch({ type: "SET_FILTER_BAR", payload: "closed" });
    },
  };

  const onIssueStateSelect = (state: "open" | "closed") => {
    setIssueStateDropdown(state);
  };

  const onUpdateSelectedIssuesState = async () => {
    try {
      const res = await putIssuesIsOpen({
        issueIds: selectedIssueIds ? [...selectedIssueIds] : [],
        state: issueStateDropdown,
      });

      if (res.status === 200) {
        refetchIssuesList();
        deselectAllIssues();
      }
    } catch (error) {
      // TODO
      console.error(error);
    }
  };

  const currentTabName = {
    open: "열린 이슈",
    closed: "닫힌 이슈",
  };

  const numSelectedIssues = selectedIssueIds.size;

  return (
    <TableHeader>
      <TableHeaderContents>
        <div className="left-wrapper">
          <InputCheckbox
            checked={isAllIssuesSelected}
            onChange={toggleSelectAll}
          />
          {numSelectedIssues > 0 ? (
            <div className="num-selected-issues">
              {numSelectedIssues}개 이슈 선택
            </div>
          ) : (
            <TabBar
              currentTabName={currentTabName[issuesStatus]}
              left={tabBarLeftInfo}
              right={tabBarRightInfo}
              borderStyle="none"
            />
          )}
        </div>

        <div className="right-wrapper">
          {numSelectedIssues > 0 ? (
            <RadioGroup
              value={issueStateDropdown}
              onChange={onIssueStateSelect}>
              <DropdownIndicator
                displayName="상태수정"
                dropdownPanelVariant="select"
                dropdownName="issueState"
                dropdownList={issueStateDropdownList}
                dropdownPanelPosition="right"
                dropdownOption="single"
                valueType="name"
                outsideClickHandler={onUpdateSelectedIssuesState}
              />
            </RadioGroup>
          ) : (
            <IssuesFiltersDropdowns />
          )}
        </div>
      </TableHeaderContents>
    </TableHeader>
  );
}

const issueStateDropdownList: DropdownItemType[] = [
  {
    id: 0,
    variant: "plain",
    name: "open",
    content: "선택한 이슈 열기",
  },
  {
    id: 1,
    variant: "plain",
    name: "closed",
    content: "선택한 이슈 닫기",
  },
];

const TableHeaderContents = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left-wrapper {
    display: flex;
    align-items: center;

    > *:first-child {
      margin-right: 32px;
    }

    > *:last-child {
      gap: 24px;
    }

    .num-selected-issues {
      font: ${({ theme: { font } }) => font.displayBold16};
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }
  }

  .right-wrapper {
    display: flex;
    align-items: center;
    gap: 32px;
  }
`;
