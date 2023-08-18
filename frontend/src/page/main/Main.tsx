import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { getAccessToken } from "../../utils/localStorage";
import { MainHeader } from "./MainHeader";
import { MainTable } from "./MainTable";

type LabelData = {
  id: number;
  name: string;
  color: string;
  background: string;
};

type MilestoneData = {
  id: number;
  name: string;
};

type UserData = {
  id: number;
  name: string;
  avatarUrl: string;
};

export type IssueData = {
  id: number;
  title: string;
  status: "OPENED" | "CLOSED";
  createdAt: Date;
  modifiedAt: Date;
  statusModifiedAt: Date;
  assignees: UserData[];
  labels: LabelData[];
  milestones: MilestoneData;
  author: UserData;
  commentCount: number;
};

export type SingleFilterData = {
  id: number;
  name: string;
  conditions: string;
  selected: boolean;
};

export type Option = {
  id: number;
  name: string;
  selected: boolean;
};

type AssigneeOption = Option & {
  avatarUrl: string | null;
};

type LabelOption = Option & {
  color: string;
  background: string;
};

type MilestoneOption = Option;

type FilterGroup<T> = {
  multipleSelect: boolean;
  options: T[];
};

export type MultiFilters = {
  assignees: FilterGroup<AssigneeOption>;
  authors: FilterGroup<AssigneeOption>;
  labels: FilterGroup<LabelOption>;
  milestones: FilterGroup<MilestoneOption>;
};

export type IssueDataState = {
  input: string;
  openedIssueCount: number;
  closedIssueCount: number;
  labelCount: number;
  milestoneCount: number;
  issues: IssueData[];
  singleFilters: SingleFilterData[];
  multiFilters: MultiFilters;
};

export function Main() {
  const navigate = useNavigate();
  const [issueData, setIssueData] = useState<IssueDataState>();
  const [filterString, setFilterString] = useState<string>();

  const queryString = window.location.search;

  const fetchData = useCallback(async () => {
    const res = await fetch(`/api/issues${queryString}`, {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });

    const { code, data } = await res.json();

    if (code === 200) {
      setIssueData(data);
      setFilterString(data.input);
    }
  }, [queryString]);

  useEffect(() => {
    fetchData();
  }, [queryString, navigate, fetchData]);

  const convertToQueryString = (filterCondition: string) => {
    return filterCondition
      .split(/\s(?=[^:]*:)/)
      .map((part) => {
        const index = part.indexOf(":");
        const key = part.substring(0, index);
        const value = part.substring(index + 1);

        return `${key}=${encodeURIComponent(value)}`;
      })
      .join("&");
  };

  const setSingleFilters = (filterCondition: string) => {
    const newQueryString = convertToQueryString(filterCondition);

    navigate(`/?${newQueryString}`);
  };

  const setMultiFilterString = (
    filterCondition: string,
    multipleSelect: boolean,
  ) => {
    if (!issueData) return;

    const input = issueData.input;

    if (input.includes(filterCondition)) {
      const newInput = input
        .split(/\s(?=[^:]*:)/)
        .filter((part) => part !== filterCondition)
        .join(" ");
      const newQueryString = convertToQueryString(newInput);

      navigate(`/?${newQueryString}`);
      return;
    }

    const [keyToAdd, valueToAdd] = filterCondition.split(":");
    const filteredParts = input
      .split(/\s(?=[^:]*:)/)
      .filter(
        (part) =>
          !(
            part.startsWith(`${keyToAdd}:`) &&
            (part.endsWith(":none") || !multipleSelect)
          ),
      );

    const newInput = [...filteredParts, `${keyToAdd}:${valueToAdd}`].join(" ");
    const newQueryString = convertToQueryString(newInput.trim());

    navigate(`/?${newQueryString}`);
  };

  const onChangeFilterInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFilterString(event.target.value);
  };

  const handleFilterInput = () => {
    if (filterString === undefined) return;

    const newQueryString = convertToQueryString(filterString);

    navigate(`/?${newQueryString}`);
  };

  return (
    <Div>
      {issueData && (
        <>
          <MainHeader
            singleFilters={issueData.singleFilters}
            milestoneCount={issueData.milestoneCount}
            labelCount={issueData.labelCount}
            input={filterString!}
            setSingleFilters={setSingleFilters}
            onChangeFilterInput={onChangeFilterInput}
            handleFilterInput={handleFilterInput}
          />
          <MainTable
            issueData={issueData}
            setMultiFilterString={setMultiFilterString}
            fetchData={fetchData}
            filterString={filterString!}
          />
        </>
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  padding: 0px 80px;
  margin-bottom: 32px;
`;
