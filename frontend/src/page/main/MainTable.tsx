import { styled } from "styled-components";
import { IssueDataState } from "./Main";
import { MainTableElement } from "./MainTableElement";
import { MainTableHeader } from "./MainTableHeader";

type MainTableProps = {
  issueData: IssueDataState;
  setMultiFilterString: (value: string, multipleSelect: boolean) => void;
};

export function MainTable({ issueData, setMultiFilterString }: MainTableProps) {
  return (
    <Div>
      <MainTableHeader
        openedIssueCount={issueData.openedIssueCount}
        closedIssueCount={issueData.closedIssueCount}
        multiFilters={issueData.multiFilters}
        setMultiFilterString={setMultiFilterString}
      />
      <MainTableBody>
        {issueData.issues.map((issue, index) => (
          <MainTableElement key={index} issue={issue} />
        ))}
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
