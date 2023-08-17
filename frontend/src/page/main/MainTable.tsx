import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { Icon } from "../../components/icon/Icon";
import { getAccessToken } from "../../utils/localStorage";
import { IssueDataState } from "./Main";
import { MainTableElement } from "./MainTableElement";
import { MainTableHeader } from "./MainTableHeader";

type MainTableProps = {
  issueData: IssueDataState;
  filterString: string;
  fetchData: () => void;
  setMultiFilterString: (value: string, multipleSelect: boolean) => void;
};

export function MainTable({
  issueData,
  fetchData,
  setMultiFilterString,
  filterString,
}: MainTableProps) {
  const [checkedIssueId, setCheckedIssueId] = useState<number[]>([]);

  useEffect(() => {
    setCheckedIssueId([]);
  }, [issueData]);

  const onChangeIssuesState = async (state: "OPENED" | "CLOSED") => {
    const body = {
      issues: checkedIssueId,
      status: state,
    };

    await fetch("/api/issues/status", {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify(body),
    });

    fetchData();
  };

  const handleHeaderCheckbox = (checked: boolean) => {
    if (checked) {
      setCheckedIssueId(issueData.issues.map((issue) => issue.id));
    } else {
      setCheckedIssueId([]);
    }
  };

  const handleCheckedIssue = (issueId: number) => {
    if (checkedIssueId.includes(issueId)) {
      setCheckedIssueId(checkedIssueId.filter((id) => id !== issueId));
    } else {
      setCheckedIssueId([...checkedIssueId, issueId]);
    }
  };

  return (
    <Div>
      <MainTableHeader
        openedIssueCount={issueData.openedIssueCount}
        closedIssueCount={issueData.closedIssueCount}
        multiFilters={issueData.multiFilters}
        filterString={filterString}
        checkedIssueId={checkedIssueId}
        totalIssueCount={issueData.issues.length}
        setMultiFilterString={setMultiFilterString}
        handleHeaderCheckbox={handleHeaderCheckbox}
        onChangeIssuesState={onChangeIssuesState}
      />
      <MainTableBody>
        {issueData.issues.map((issue, index) => (
          <MainTableElement
            key={index}
            issue={issue}
            handleCheckedIssue={handleCheckedIssue}
            inputChecked={checkedIssueId.includes(issue.id)}
          />
        ))}
        {!issueData.issues.length && (
          <NoneElement>
            <Icon name="AlertCircle" />
            <span>이슈가 비어 있습니다.</span>
          </NoneElement>
        )}
      </MainTableBody>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background: ${({ theme }) => theme.color.neutralSurfaceStrong};
`;

const MainTableBody = styled.div`
  width: 100%;
`;

const NoneElement = styled.div`
  width: 100%;
  height: 96px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.color.neutralTextStrong};
  border-top: solid 1px ${({ theme }) => theme.color.neutralBorderDefault};
`;
