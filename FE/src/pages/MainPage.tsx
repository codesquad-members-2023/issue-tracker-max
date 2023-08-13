import { styled } from "styled-components";
import FilterBar from "../components/FilterBar/FilterBar";
import Button from "../components/common/Button/Button";
import DropdownIndicator from "../components/DropdownIndicator/DropdownIndicator";
import IssueList from "../components/IssueList/IssueList";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ListDataProps } from "../type";
import parseFilter from "../utils/parseFilter";
import parseParam from "../utils/parseParam";

export default function MainPage() {
  const navigate = useNavigate();
  const { filter } = useParams();
  const [data, setData] = useState<ListDataProps>();
  const [filterValue, setFilterValue] = useState<string>(
    filter ? filter : "isOpen=true",
  );

  const goAddNewIssuePage = () => {
    navigate("/new");
  };

  const goLabelsPage = () => {
    navigate("/labels");
  };

  const goMilestonesPage = () => {
    navigate("/milestones/isOpen=true");
  };

  const showOpenIssue = () => {
    navigate("/issues/isOpen=true");
  };

  const showCloseIssue = () => {
    navigate("/issues/isOpen=false");
  };

  const handleEnterFilter = () => {
    navigate(`/issues/${filterValue}`);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          filter
            ? `http://3.34.141.196/api/issues${parseFilter(filter)}`
            : "http://3.34.141.196/api/issues",
        );
        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }
        const jsonData = await response.json();
        setData(jsonData);
      } catch (error) {
        console.log("error");
      }
    };

    fetchData();
  }, [filter]);

  useEffect(() => {
    setFilterValue(parseParam(filter!));
  }, [filter]);

  return (
    <Main>
      <FilterSection>
        <FilterBar
          filterValue={filterValue}
          onChange={handleInputChange}
          handleEnterFilter={handleEnterFilter}
        />
        <Taps>
          <TapButtonWrapper>
            <Button
              icon={"label"}
              label={`레이블(${data && data.metadata.labelCount})`}
              type={"ghost"}
              size={"medium"}
              width={"50%"}
              onClick={goLabelsPage}
            />
            <Button
              icon={"milestone"}
              label={`마일스톤(${data && data.metadata.milestoneCount})`}
              type={"ghost"}
              size={"medium"}
              width={"50%"}
              onClick={goMilestonesPage}
            />
          </TapButtonWrapper>
          <Button
            icon={"plus"}
            label={"이슈 작성"}
            onClick={goAddNewIssuePage}
          />
        </Taps>
      </FilterSection>
      {filter !== "isOpen=true" && (
        <RemoveFilterWrapper>
          <Button
            icon={"xSquare"}
            label={"현재의 검색 필터 및 정렬 지우기"}
            type={"ghost"}
            height={"32px"}
            onClick={showOpenIssue}
          />
        </RemoveFilterWrapper>
      )}
      <IssueTable>
        <TableHeader>
          <CheckboxWrapper>
            <CheckboxLabel htmlFor={"tableCheckbox"}></CheckboxLabel>
            <Checkbox id={"tableCheckbox"} type="checkbox" />
          </CheckboxWrapper>
          <TapWrapper>
            <IssueTap>
              <Button
                icon={"alertCircle"}
                label={`열린 이슈(${data && data.metadata.issueOpenCount})`}
                type={"ghost"}
                size={"medium"}
                onClick={showOpenIssue}
              />
              <Button
                icon={"archive"}
                label={`닫힌 이슈(${data && data.metadata.issueCloseCount})`}
                type={"ghost"}
                size={"medium"}
                onClick={showCloseIssue}
              />
            </IssueTap>
            <FilterTap>
              <DropdownIndicator
                type={"assignees"}
                label={"담당자"}
                dropdownTop={"40px"}
              />
              <DropdownIndicator type={"labels"} label={"레이블"} />
              <DropdownIndicator type={"milestones"} label={"마일스톤"} />
              <DropdownIndicator type={"authors"} label={"작성자"} />
            </FilterTap>
          </TapWrapper>
        </TableHeader>
        <IssueContents>
          {data &&
            (data.issues.length !== 0 ? (
              data.issues.map((issue) => (
                <IssueList key={issue.id} issue={issue} />
              ))
            ) : (
              <EmptyList>
                <EmptyContent>등록된 이슈가 없습니다</EmptyContent>
              </EmptyList>
            ))}
        </IssueContents>
      </IssueTable>
    </Main>
  );
}

const Main = styled.div`
  padding: 32px 0px;
  display: flex;
  width: 1280px;
  gap: 24px;
  flex-direction: column;
  align-items: center;
`;

const FilterSection = styled.section`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const Taps = styled.div`
  display: flex;
  gap: 16px;
`;

const TapButtonWrapper = styled.div`
  position: relative;
  display: flex;
  width: 320px;
  height: 40px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    bottom: 0;
    left: 159px;
    width: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
  > button {
    &:hover {
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.surface.bold};
    }
  }
  > button:first-child {
    border-top-right-radius: 0px;
    border-bottom-right-radius: 0px;
  }
  > button:last-child {
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
  }
`;

const IssueTable = styled.section`
  width: 100%;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const TableHeader = styled.div`
  width: 100%;
  height: 64px;
  display: flex;
  padding: 16px 32px;
  gap: 32px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
  border-top-left-radius: ${({ theme }) => theme.radius.large};
  border-top-right-radius: ${({ theme }) => theme.radius.large};
`;

const CheckboxWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Checkbox = styled.input`
  display: none;
`;

const CheckboxLabel = styled.label`
  width: 16px;
  height: 16px;
  cursor: pointer;
  padding-left: 23px;
  background-repeat: no-repeat;
  background-image: url("/icons/checkBoxInitial.svg"); // 체크된 상태 이미지
  &:checked {
    background-image: url("/icons/checkBoxActive.svg"); // 체크된 상태 이미지
  }
`;

const TapWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const IssueTap = styled.form`
  display: flex;
  gap: 24px;
`;

const FilterTap = styled.div`
  position: relative;
  display: flex;
  gap: 32px;
`;

const IssueContents = styled.ul`
  position: relative;
  width: 100%;
  display: flex;
  flex-direction: column;
  > li:last-child {
    border-bottom-left-radius: ${({ theme }) => theme.radius.large};
    border-bottom-right-radius: ${({ theme }) => theme.radius.large};
  }
`;

const EmptyList = styled.li`
  display: flex;
  align-items: center;
  padding: 0px 32px;
  width: 100%;
  height: 96px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    height: 1px;
    width: 100%;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const EmptyContent = styled.span`
  width: 100%;
  text-align: center;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const RemoveFilterWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  align-items: center;
`;
