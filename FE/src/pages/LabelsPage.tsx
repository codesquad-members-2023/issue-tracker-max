import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { FetchedLabels } from "../type";
import LabelList from "../components/LabelList/LabelList";
import Alert from "../components/Alert/Alert";
import EditLabel from "../components/LabelList/EditLabel";
import { generateRandomHex } from "../utils/generateRandomHex";

export default function LabelsPage() {
  const navigate = useNavigate();
  const [data, setData] = useState<FetchedLabels | null>(null);
  const [openDeleteAlert, setOpenDeleteAlert] = useState<boolean>(false);
  const [openAdd, setOpenAdd] = useState<boolean>(false);
  const [addLabelName, setAddLabelName] = useState<string>("");
  const [addLabelDescription, setAddLabelDescription] = useState<string>("");
  const [addLabelColor, setAddLabelColor] = useState<string>("#FFFFFF");
  const goLabelsPage = () => {
    navigate("/labels");
  };

  const goMilestonesPage = () => {
    navigate("/milestones/isOpen=true");
  };

  const openDelete = () => {
    setOpenDeleteAlert(true);
  };

  const closeDelete = () => {
    setOpenDeleteAlert(false);
  };

  const openAddLabel = () => {
    setOpenAdd(true);
  };

  const closeAddLabel = () => {
    setOpenAdd(false);
  };

  const handleNameInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddLabelName(e.target.value);
  };

  const handleDescripInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddLabelDescription(e.target.value);
  };

  const handleColorInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddLabelColor(e.target.value);
  };

  const randomColor = () => {
    setAddLabelColor(generateRandomHex());
  };

  const createLabel = async () => {
    const URL = "http://3.34.141.196/api/labels";

    const data = {
      title: addLabelName,
      description: addLabelDescription,
      color: addLabelColor,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        closeAddLabel();
        window.location.reload();
      } else {
        console.log("이슈 생성에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("오류 발생:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://3.34.141.196/api/labels");
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
      <Tap>
        <TapButtonWrapper>
          <Button
            icon={"label"}
            label={`레이블(${data?.metadata.totalLabelCount})`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goLabelsPage}
          />
          <Button
            icon={"milestone"}
            label={`마일스톤(${data?.metadata.totalMilestoneCount})`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goMilestonesPage}
          />
        </TapButtonWrapper>
        <Button
          icon={"plus"}
          label={"레이블 추가"}
          onClick={openAddLabel}
          disabled={openAdd}
        />
      </Tap>
      {openAdd && (
        <EditLabel
          type={"add"}
          title={"새로운 레이블 추가"}
          name={addLabelName}
          description={addLabelDescription}
          color={addLabelColor}
          onChangeName={handleNameInputChange}
          onChangeDescrip={handleDescripInputChange}
          onChangeColor={handleColorInputChange}
          randomColor={randomColor}
          cancelEdit={closeAddLabel}
          confirmEdit={createLabel}
        />
      )}
      <LabelsTable>
        <TableHeader>
          <LabelCounter>{data?.labels?.length}개의 레이블</LabelCounter>
        </TableHeader>
        {data?.labels?.map((label) => (
          <LabelList
            key={label.id}
            id={label.id}
            title={label.title}
            color={label.color}
            description={label.description}
            openDelete={openDelete}
          />
        ))}
        {data?.labels?.length === 0 && (
          <EmptyList>
            <EmptyContent>생성된 레이블이 없습니다.</EmptyContent>
          </EmptyList>
        )}
      </LabelsTable>
      {openDeleteAlert && (
        <Dim>
          <Alert onClickCancel={closeDelete} onClickActive={() => {}} />
        </Dim>
      )}
    </Main>
  );
}

const Main = styled.div`
  padding: 32px 0px;
  display: flex;
  gap: 24px;
  flex-direction: column;
  width: 1280px;
`;

const Tap = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
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
    font: ${({ theme }) => theme.font.selectedBold16};
    color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
    background-color: ${({ theme }) => theme.colorSystem.neutral.surface.bold};
  }
  > button:last-child {
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
  }
`;

const LabelsTable = styled.ul`
  width: 100%;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
  > li:last-child {
    border-bottom-left-radius: inherit;
    border-bottom-right-radius: inherit;
  }
`;
const TableHeader = styled.li`
  width: 100%;
  padding: 20px 32px;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
`;

const LabelCounter = styled.span``;

const EmptyList = styled.li`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 96px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    width: 1280px;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const EmptyContent = styled.span`
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const Dim = styled.div`
  position: absolute;
  top: 0px;
  left: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  z-index: 1000;
  background-color: rgba(16, 20, 26, 0.4);
`;
