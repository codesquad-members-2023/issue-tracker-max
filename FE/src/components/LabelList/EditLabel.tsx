import { styled } from "styled-components";
import LabelItem from "../SideBar/LabelItem";
import LabelInput from "../common/TextInput/LabelInput";
import Button from "../common/Button/Button";

type Props = {
  type?: "edit" | "add";
  id?: number;
  title: string;
  name: string;
  description: string;
  color: string;
  onChangeName(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeDescrip(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeColor(e: React.ChangeEvent<HTMLInputElement>): void;
  randomColor(): void;
  cancelEdit(): void;
  confirmEdit(): void;
};

export default function EditLabel({
  type = "edit",
  id,
  title,
  name,
  description,
  color,
  onChangeName,
  onChangeDescrip,
  onChangeColor,
  randomColor,
  cancelEdit,
  confirmEdit,
}: Props) {
  const confirmEditLabel = async () => {
    const URL = `http://3.34.141.196/api/labels/${id}`;

    const putData = {
      title: name,
      description: description,
      color: color,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "PUT",
        headers: headers,
        body: JSON.stringify(putData),
      });

      if (response.status === 204) {
        confirmEdit();
      } else {
        console.log("PUT 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  return (
    <Edit $type={type}>
      <EditHeader>{title}</EditHeader>
      <EditContents>
        <Preview>
          <LabelItem label={name} color={color} />
        </Preview>
        <EditInputs>
          <LabelInput
            id={"name"}
            label={"이름"}
            placeholder={"레이블의 이름을 입력하세요"}
            value={name}
            onChange={onChangeName}
          />
          <LabelInput
            id={"description"}
            label={"설명(선택)"}
            placeholder={"레이블에 대한 설명을 입력하세요"}
            value={description}
            onChange={onChangeDescrip}
          />
          <LabelInput
            id={"color"}
            icon={"refreshCcw"}
            label={"배경 색상"}
            placeholder={"레이블의 이름을 입력하세요"}
            value={color}
            maxLength={7}
            onChange={onChangeColor}
            onClick={randomColor}
          />
        </EditInputs>
      </EditContents>
      <ButtonTap>
        <Button
          icon={"xSquare"}
          type={"outline"}
          label={"취소"}
          onClick={cancelEdit}
        />
        <Button
          icon={"edit"}
          label={type === "edit" ? "편집 완료" : "완료"}
          onClick={type === "edit" ? confirmEditLabel : confirmEdit}
          disabled={!name}
        />
      </ButtonTap>
    </Edit>
  );
}

const Edit = styled.li<{ $type: "edit" | "add" }>`
  position: relative;
  padding: 32px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 100%;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  ${({ $type, theme }) =>
    $type === "add" &&
    `
    border: ${theme.border.default} ${theme.colorSystem.neutral.border.default};
    border-radius: ${theme.radius.large};
  `}
  ${({ $type, theme }) =>
    $type === "edit" &&
    `
    &::after {
      content: "";
      position: absolute;
      top: 0px;
      left: 0px;
      width: 1280px;
      height: 1px;
      background-color: ${theme.colorSystem.neutral.border.default};
    }
  `}
`;

const EditHeader = styled.h1`
  width: 100%;
  font: ${({ theme }) => theme.font.displayBold20};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;

const EditContents = styled.div`
  display: flex;
  gap: 24px;
`;

const Preview = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 290px;
  height: 155px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
`;

const EditInputs = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 900px;
`;

const ButtonTap = styled.div`
  display: flex;
  justify-content: end;
  gap: 16px;
  width: 100%;
`;
