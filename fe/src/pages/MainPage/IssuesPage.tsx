import XIcon from "@assets/icon/xSquare.svg";
import FilterBar from "@components/FilterBar";
import NavigationBar from "@components/NavigationBar";
import IssuesTable from "@components/Table/IssuesTable/IssuesTable";
import Button from "@components/common/Button";
import {
  INITIAL_FILTER_TEXT,
  useIssuesFilter,
  useIssuesFilterDispatch,
} from "context/IssuesFilterContext";
import { styled } from "styled-components";

export default function IssuesPage() {
  const { issuesFilter } = useIssuesFilter();
  const issuesFilterDispatch = useIssuesFilterDispatch();

  const isIssuesFiltered = issuesFilter.text !== INITIAL_FILTER_TEXT;

  return (
    <StyledIssuesPage>
      <IssuesNavBar>
        <FilterBar />
        <NavigationBar />
      </IssuesNavBar>
      {isIssuesFiltered && (
        <Button
          variant="ghost"
          size="S"
          className="reset-filter-button"
          onClick={() => issuesFilterDispatch({ type: "RESET_FILTER" })}>
          <img src={XIcon} alt="검색 필터 정렬 지우기 아이콘" />
          <span>현재의 검색 필터 및 정렬 지우기</span>
        </Button>
      )}
      <IssuesTable />
    </StyledIssuesPage>
  );
}
const StyledIssuesPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  .reset-filter-button {
    align-self: flex-start;
  }
`;

const IssuesNavBar = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
`;
