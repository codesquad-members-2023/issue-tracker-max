import { styled } from "styled-components";
import FilterBar from "../components/FilterBar/FilterBar";
import Button from "../components/common/Button/Button";
import DropdownIndicator from "../components/DropdownIndicator/DropdownIndicator";
import IssueList from "../components/IssueList/IssueList";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { ListDataProps } from "../type";

export default function MainPage() {
  const navigate = useNavigate();
  const goAddNewIssuePage = () => {
    navigate("/new");
  };

  const [data, setData] = useState<ListDataProps | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://3.34.141.196/api/issues");
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
  }, []);

  return (
    <Main>
      <FilterSection>
        <FilterBar />
        <Taps>
          <TapButtonWrapper>
            <Button
              icon={"label"}
              label={"레이블"}
              type={"ghost"}
              size={"medium"}
              width={"50%"}
              onClick={() => {}}
            />
            <Button
              icon={"milestone"}
              label={"마일스톤"}
              type={"ghost"}
              size={"medium"}
              width={"50%"}
              onClick={() => {}}
            />
          </TapButtonWrapper>
          <Button
            icon={"plus"}
            label={"이슈 작성"}
            onClick={goAddNewIssuePage}
          />
        </Taps>
      </FilterSection>
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
                label={"열린 이슈"}
                type={"ghost"}
                size={"medium"}
                width={"50%"}
                onClick={() => {}}
              />
              <Button
                icon={"archive"}
                label={"닫힌 이슈"}
                type={"ghost"}
                size={"medium"}
                width={"50%"}
                onClick={() => {}}
              />
            </IssueTap>
            <FilterTap>
              <DropdownIndicator label={"담당자"} onClick={() => {}} />
              <DropdownIndicator label={"레이블"} onClick={() => {}} />
              <DropdownIndicator label={"마일스톤"} onClick={() => {}} />
              <DropdownIndicator label={"작성자"} onClick={() => {}} />
            </FilterTap>
          </TapWrapper>
        </TableHeader>
        <IssueContents>
          {data &&
            data.issues.map((issue, key) => (
              <IssueList key={key} issue={issue} />
            ))}
        </IssueContents>
      </IssueTable>
    </Main>
  );
}

const Main = styled.div`
  padding-top: 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 100vw;
  min-height: 100vh;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const FilterSection = styled.section`
  width: 1280px;
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
  margin: 24px 0px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const TableHeader = styled.div`
  width: 1280px;
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
  width: 1168px;
  display: flex;
  justify-content: space-between;
`;

const IssueTap = styled.form`
  display: flex;
  gap: 24px;
`;

const FilterTap = styled.div`
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
