// type Props = {};

import { styled } from "styled-components";
import Button from "../common/Button/Button";
import { Label } from "../../type";
import LabelItem from "../SideBar/LabelItem";
import { useState } from "react";
import { generateRandomHex } from "../../utils/generateRandomHex";
import EditLabel from "./EditLabel";

interface Props extends Label {
  openDelete(): void;
}

export default function LabelList({ id, title, color, description }: Props) {
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const [labelName, setLabelName] = useState<string>(title);
  const [labelDescription, setLabelDescription] = useState<string>(description);
  const [labelColor, setLabelColor] = useState<string>(color);

  const confirmEdit = () => {
    setIsEdit(false);
  };

  const editList = () => {
    setIsEdit(true);
  };

  const cancelEdit = () => {
    setLabelName(title);
    setLabelDescription(description);
    setLabelColor(color);
    setIsEdit(false);
  };

  const handleNameInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLabelName(e.target.value);
  };

  const handleDescripInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLabelDescription(e.target.value);
  };

  const handleCInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setLabelColor(e.target.value);
  };

  const randomColor = () => {
    setLabelColor(generateRandomHex());
  };

  const deleteLabel = async () => {
    const URL = `http://3.34.141.196/api/labels/${id}`; // DELETE 요청을 보낼 리소스 URL로 변경

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "DELETE",
        headers: headers,
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log(
          "DELETE 요청에 실패하였습니다. 상태 코드:",
          response.status,
        );
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  return (
    <Container id={id.toString()}>
      {!isEdit && (
        <Default>
          <LabelWrapper>
            <LabelItem label={labelName} color={labelColor} />
          </LabelWrapper>
          <Description>{labelDescription}</Description>
          <ButtonTap>
            <Button
              icon={"edit"}
              type={"ghost"}
              label={"편집"}
              onClick={editList}
            />
            <Button
              icon={"trash"}
              type={"ghost"}
              label={"삭제"}
              onClick={deleteLabel}
            />
          </ButtonTap>
        </Default>
      )}
      {isEdit && (
        <EditLabel
          id={id}
          title={"레이블 편집"}
          name={labelName}
          description={labelDescription}
          color={labelColor}
          onChangeName={handleNameInputChange}
          onChangeDescrip={handleDescripInputChange}
          onChangeColor={handleCInputChange}
          randomColor={randomColor}
          cancelEdit={cancelEdit}
          confirmEdit={confirmEdit}
        />
      )}
    </Container>
  );
}

const Container = styled.li`
  position: relative;
  width: 100%;
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

const Default = styled.div`
  padding: 20px 32px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 96px;
`;

const LabelWrapper = styled.div`
  display: inline-flex;
  width: 700px;
`;

const Description = styled.span`
  width: 870px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const ButtonTap = styled.div`
  display: flex;
  justify-content: end;
  gap: 16px;
  width: 100%;
`;
