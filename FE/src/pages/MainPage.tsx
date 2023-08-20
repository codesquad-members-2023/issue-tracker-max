import { styled } from "styled-components";
import FilterBar from "../components/FilterBar/FilterBar";
import Button from "../components/common/Button/Button";
import IssueList from "../components/IssueList/IssueList";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  AssigneesData,
  AuthorsData,
  LabelsData,
  ListDataProps,
  MilestonesData,
} from "../type";
import parseFilter from "../utils/parseFilter";
import parseParam from "../utils/parseParam";
import LoadingIndicator from "../components/LoadingIndicator/LoadingIndicator";
import DropdownIndicator from "../components/DropdownIndicator/DropdownIndicator";
import DropdownItem from "../components/DropdownPanel/DropdownItem";
import DropdownPanel from "../components/DropdownPanel/DropdownPanel";
import Header from "../components/Header/Header";
import { useAuth } from "../context/AuthContext";

type Props = {
  toggleTheme(): void;
};

export default function MainPage({ toggleTheme }: Props) {
  const navigate = useNavigate();
  const { filter } = useParams();
  const { profile } = useAuth();
  const [loading, setLoading] = useState<boolean>(true);
  const [openFilterDropdown, setOpenFilterDropdown] = useState<
    "assignees" | "labels" | "milestones" | "authors" | "state" | "close"
  >("close");
  const [assigneesData, setAssigneesData] = useState<AssigneesData>();
  const [labelsData, setLabelsData] = useState<LabelsData>();
  const [milestonesData, setMilestonesData] = useState<MilestonesData>();
  const [authorsData, setAuthorsData] = useState<AuthorsData>();
  const [data, setData] = useState<ListDataProps>();
  const [selectAll, setSelectAll] = useState<boolean>(false);
  const [selectList, setSelectList] = useState<number[]>([]);
  const [filterValue, setFilterValue] = useState<string>(
    filter ? filter : "isOpen=true",
  );
  const isSelected = selectList.length !== 0;

  const addSelectList = (id: number) => {
    setSelectList((prevList) => [...prevList, id]);
  };

  const removeSelectList = (id: number) => {
    setSelectList((prevList) => prevList.filter((item) => item !== id));
  };

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

  const closeDropdown = () => {
    setOpenFilterDropdown("close");
  };

  const showAssigneesDropdown = () => {
    setOpenFilterDropdown("assignees");
  };

  const showLabelsDropdown = () => {
    setOpenFilterDropdown("labels");
  };

  const showMilestonesDropdown = () => {
    setOpenFilterDropdown("milestones");
  };

  const showAuthorsDropdown = () => {
    setOpenFilterDropdown("authors");
  };

  const showStateDropdown = () => {
    setOpenFilterDropdown("state");
  };

  const toggleSelectAll = () => {
    if (!selectAll) {
      setSelectAll(true);
      setSelectList(data!.issues && data!.issues.map((issue) => issue.id));
      console.log(profile);
      return;
    }
    setSelectAll(false);
    setSelectList([]);
  };

  const openSelectedIssues = async () => {
    const URL = "http://3.34.141.196/api/issues/open-all";

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const patchData = {
      ids: selectList,
      isOpen: true,
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  const closeSelectedIssues = async () => {
    const URL = "http://3.34.141.196/api/issues/open-all";

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const patchData = {
      ids: selectList,
      isOpen: false,
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  useEffect(() => {
    const maxRetryAttempts = 3;
    let retryAttempts = 0;

    const fetchData = async () => {
      while (retryAttempts < maxRetryAttempts) {
        try {
          const headers = new Headers();
          const accessToken = localStorage.getItem("accessToken");
          headers.append("Authorization", `Bearer ${accessToken}`);
          const response = await fetch(
            filter
              ? `http://3.34.141.196/api/issues${parseFilter(filter)}`
              : "http://3.34.141.196/api/issues",
            { headers },
          );

          if (!response.ok) {
            throw new Error("데이터를 가져오는 데 실패했습니다.");
          }

          const jsonData = await response.json();
          setData(jsonData);
          console.log(jsonData);
          setLoading(false);
          return;
        } catch (error) {
          retryAttempts++;
          console.log("에러 발생:", error);
        }
      }

      setLoading(false);
    };

    fetchData();
  }, [filter]);

  useEffect(() => {
    setFilterValue(parseParam(filter!));
  }, [filter]);

  useEffect(() => {
    if (selectAll && selectList.length === 0) setSelectAll(false);
    if (!selectAll && selectList.length === data?.issues.length)
      setSelectAll(true);
  }, [selectList]);

  useEffect(() => {
    if (openFilterDropdown === "close" || openFilterDropdown === "state") {
      return;
    }
    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);

    const URL = `http://3.34.141.196/api/issues/${openFilterDropdown}`;
    const fetchData = async () => {
      try {
        const response = await fetch(URL, { headers });
        if (!response.ok) {
          throw new Error("데이터를 가져오는 데 실패했습니다.");
        }
        const jsonData = await response.json();
        openFilterDropdown === "assignees"
          ? setAssigneesData(jsonData)
          : openFilterDropdown === "labels"
          ? setLabelsData(jsonData)
          : openFilterDropdown === "milestones"
          ? setMilestonesData(jsonData)
          : setAuthorsData(jsonData);
      } catch (error) {
        console.log("error");
      }
    };

    fetchData();
  }, [openFilterDropdown]);

  return (
    <>
      <Header
        toggleTheme={toggleTheme}
        profileImg={profile ? profile.profileImageUri : "/icons/user.png"}
      />
      <Page>
        {loading && (
          <LoadingWrapper>
            <LoadingIndicator />
          </LoadingWrapper>
        )}
        {!loading && data && (
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
                    label={`레이블(${data.metadata.labelCount})`}
                    type={"ghost"}
                    size={"medium"}
                    width={"50%"}
                    onClick={goLabelsPage}
                  />
                  <Button
                    icon={"milestone"}
                    label={`마일스톤(${data.metadata.milestoneCount})`}
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
              {!isSelected && (
                <TableHeader>
                  <CheckboxWrapper>
                    <Checkbox
                      type="checkbox"
                      checked={selectAll}
                      onChange={toggleSelectAll}
                    />
                  </CheckboxWrapper>
                  <TapWrapper>
                    <IssueTap>
                      <Button
                        icon={"alertCircle"}
                        label={`열린 이슈(${data.metadata.issueOpenCount})`}
                        type={"ghost"}
                        size={"medium"}
                        onClick={showOpenIssue}
                      />
                      <Button
                        icon={"archive"}
                        label={`닫힌 이슈(${data.metadata.issueCloseCount})`}
                        type={"ghost"}
                        size={"medium"}
                        onClick={showCloseIssue}
                      />
                    </IssueTap>
                    <FilterTap>
                      <AssigneesDropdown>
                        <DropdownIndicator
                          label={"담당자"}
                          onClick={showAssigneesDropdown}
                        />
                        {openFilterDropdown === "assignees" &&
                          assigneesData && (
                            <DropdownPanel
                              title={"담당자 필터"}
                              top={"40px"}
                              left={"-150px"}
                              closeDropdown={closeDropdown}
                            >
                              <DropdownItem
                                itemName={"담당자가 없는 이슈"}
                                value={"no=assignee"}
                                onClick={() => {
                                  closeDropdown();
                                  navigate("/issues/no=assignee");
                                }}
                              />
                              {assigneesData.assignees.map((assignee) => (
                                <DropdownItem
                                  key={assignee.id}
                                  imgSource={assignee.profileImageUrl}
                                  itemName={assignee.nickname}
                                  value={`assigneeNames=${assignee.nickname}`}
                                  onClick={() => {
                                    closeDropdown();
                                    navigate(
                                      `/issues/assigneeNames=${assignee.nickname}`,
                                    );
                                  }}
                                />
                              ))}
                            </DropdownPanel>
                          )}
                      </AssigneesDropdown>
                      <LabelsDropdown>
                        <DropdownIndicator
                          label={"레이블"}
                          onClick={showLabelsDropdown}
                        />
                        {openFilterDropdown === "labels" && labelsData && (
                          <DropdownPanel
                            title={"레이블 필터"}
                            top={"40px"}
                            left={"-150px"}
                            closeDropdown={closeDropdown}
                          >
                            <DropdownItem
                              itemName={"레이블이 없는 이슈"}
                              value={"no=label"}
                              onClick={() => {
                                closeDropdown();
                                navigate("/issues/no=label");
                              }}
                            />
                            {labelsData.labels.map((label) => (
                              <DropdownItem
                                key={label.id}
                                color={label.backgroundColor}
                                itemName={label.title}
                                value={`labelTitles=${label.title}`}
                                onClick={() => {
                                  closeDropdown();
                                  navigate(
                                    `/issues/labelTitles=${label.title}`,
                                  );
                                }}
                              />
                            ))}
                          </DropdownPanel>
                        )}
                      </LabelsDropdown>
                      <MilestonesDropdown>
                        <DropdownIndicator
                          label={"마일스톤"}
                          onClick={showMilestonesDropdown}
                        />
                        {openFilterDropdown === "milestones" &&
                          milestonesData && (
                            <DropdownPanel
                              title={"마일스톤 필터"}
                              top={"40px"}
                              left={"-150px"}
                              closeDropdown={closeDropdown}
                            >
                              <DropdownItem
                                itemName={"마일스톤이 없는 이슈"}
                                value={"no=milestone"}
                                onClick={() => {
                                  closeDropdown();
                                  navigate("/issues/no=milestone");
                                }}
                              />
                              {milestonesData.milestones.map((milestone) => (
                                <DropdownItem
                                  key={milestone.id}
                                  itemName={milestone.title}
                                  value={`milestoneTitle=${milestone.title}`}
                                  onClick={() => {
                                    closeDropdown();
                                    navigate(
                                      `/issues/milestoneTitle=${milestone.title}`,
                                    );
                                  }}
                                />
                              ))}
                            </DropdownPanel>
                          )}
                      </MilestonesDropdown>
                      <AuthorsDropdown>
                        <DropdownIndicator
                          label={"작성자"}
                          onClick={showAuthorsDropdown}
                        />
                        {openFilterDropdown === "authors" && authorsData && (
                          <DropdownPanel
                            title={"작성자 필터"}
                            top={"40px"}
                            left={"-150px"}
                            closeDropdown={closeDropdown}
                          >
                            {authorsData.authors.map((author) => (
                              <DropdownItem
                                key={author.id}
                                imgSource={author.profileImageUrl}
                                itemName={author.nickname}
                                value={`authorName=${author.nickname}`}
                                onClick={() => {
                                  closeDropdown();
                                  navigate(
                                    `/issues/authorName=${author.nickname}`,
                                  );
                                }}
                              />
                            ))}
                          </DropdownPanel>
                        )}
                      </AuthorsDropdown>
                    </FilterTap>
                  </TapWrapper>
                </TableHeader>
              )}
              {isSelected && (
                <TableHeader>
                  <CheckboxWrapper>
                    <Checkbox
                      type="checkbox"
                      checked={selectAll}
                      onChange={toggleSelectAll}
                    />
                  </CheckboxWrapper>
                  <TapWrapper>
                    <SelectedListCounter>
                      {selectList.length}개가 선택됨
                    </SelectedListCounter>
                    <IssuesStateDropdown>
                      <DropdownIndicator
                        label={"상태 수정"}
                        onClick={showStateDropdown}
                      />
                      {openFilterDropdown === "state" && (
                        <DropdownPanel
                          title={"상태 변경"}
                          top={"40px"}
                          left={"-150px"}
                          closeDropdown={closeDropdown}
                        >
                          <DropdownItem
                            itemName={"선택한 이슈 열기"}
                            checkbox={false}
                            onClick={openSelectedIssues}
                          />
                          <DropdownItem
                            itemName={"선택한 이슈 닫기"}
                            checkbox={false}
                            onClick={closeSelectedIssues}
                          />
                        </DropdownPanel>
                      )}
                    </IssuesStateDropdown>
                  </TapWrapper>
                </TableHeader>
              )}
              <IssueContents>
                {data.issues.length !== 0 ? (
                  data.issues.map((issue) => (
                    <IssueList
                      key={issue.id}
                      issue={issue}
                      selectAll={selectAll}
                      addSelectList={addSelectList}
                      removeSelectList={removeSelectList}
                    />
                  ))
                ) : (
                  <EmptyList>
                    <EmptyContent>등록된 이슈가 없습니다</EmptyContent>
                  </EmptyList>
                )}
              </IssueContents>
            </IssueTable>
          </Main>
        )}
      </Page>
    </>
  );
}

const Page = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const LoadingWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 700px;
`;

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
  appearance: none;
  width: 16px;
  height: 16px;
  background-image: url("/icons/checkBoxInitial.svg");
  &:checked {
    border-color: transparent;
    background-image: url("/icons/checkBoxActive.svg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
  &:indeterminate {
    border-color: transparent;
    background-image: url("/icons/checkBoxDisable.svg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
`;

const TapWrapper = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

const SelectedListCounter = styled.span`
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
`;

const AssigneesDropdown = styled.div`
  position: relative;
`;

const LabelsDropdown = styled.div`
  position: relative;
`;

const MilestonesDropdown = styled.div`
  position: relative;
`;

const AuthorsDropdown = styled.div`
  position: relative;
`;

const IssuesStateDropdown = styled.div`
  position: relative;
`;
