import { styled } from "styled-components";
import LabelItem from "../SideBar/LabelItem";
import LabelInput from "../common/TextInput/LabelInput";
import Button from "../common/Button/Button";
import DropdownIndicator from "../DropdownIndicator/DropdownIndicator";
import DropdownPanel from "../DropdownPanel/DropdownPanel";
import DropdownItem from "../DropdownPanel/DropdownItem";
import { useState } from "react";

type Props = {
  type?: "edit" | "add";
  id?: number;
  title: string;
  name: string;
  description: string;
  backgroundColor: string;
  textColor: string;
  onChangeName(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeDescrip(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeColor(e: React.ChangeEvent<HTMLInputElement>): void;
  randomColor(): void;
  cancelEdit(): void;
  confirmEdit(): void;
  changeTextColor(color: string): void;
};

export default function EditLabel({
  type = "edit",
  id,
  title,
  name,
  description,
  backgroundColor,
  textColor,
  onChangeName,
  onChangeDescrip,
  onChangeColor,
  randomColor,
  cancelEdit,
  confirmEdit,
  changeTextColor,
}: Props) {
  const [openTextColorDropdown, setOpenTextColorDropdown] =
    useState<boolean>(false);

  const showTextColorDropdown = () => {
    setOpenTextColorDropdown(true);
  };

  const hideTextColorDropdown = () => {
    setOpenTextColorDropdown(false);
  };

  const confirmEditLabel = async () => {
    const URL = `http://3.34.141.196/api/labels/${id}`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const putData = {
      title: name,
      description: description,
      backgroundColor: backgroundColor,
      textColor: textColor,
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
          <LabelItem
            label={name}
            backgroundColor={backgroundColor}
            textColor={textColor}
          />
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
          <ColorInputWrapper>
            <LabelInput
              id={"color"}
              icon={"refreshCcw"}
              label={"배경 색상"}
              placeholder={"레이블의 이름을 입력하세요"}
              value={backgroundColor}
              maxLength={7}
              onChange={onChangeColor}
              onClick={randomColor}
            />
            <LabelTextDropdown>
              <DropdownIndicator
                width={"94px"}
                label={"텍스트 색상"}
                onClick={showTextColorDropdown}
              />
              {openTextColorDropdown && (
                <DropdownPanel
                  title={"텍스트 색상 변경"}
                  top={"30px"}
                  left={"-10px"}
                  closeDropdown={hideTextColorDropdown}
                >
                  <DropdownItem
                    color={"#14142B"}
                    itemName={"Black"}
                    criteria={textColor}
                    value={"#14142B"}
                    onClick={() => {
                      hideTextColorDropdown();
                      changeTextColor("#14142B");
                    }}
                  />
                  <DropdownItem
                    color={"#A0A3BD"}
                    itemName={"Gray"}
                    criteria={textColor}
                    value={"#A0A3BD"}
                    onClick={() => {
                      hideTextColorDropdown();
                      changeTextColor("#A0A3BD");
                    }}
                  />
                  <DropdownItem
                    color={"#FEFEFE"}
                    itemName={"White"}
                    criteria={textColor}
                    value={"#FEFEFE"}
                    onClick={() => {
                      hideTextColorDropdown();
                      changeTextColor("#FEFEFE");
                    }}
                  />
                </DropdownPanel>
              )}
            </LabelTextDropdown>
          </ColorInputWrapper>
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

const ColorInputWrapper = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  width: 320px;
  gap: 24px;
`;

const ButtonTap = styled.div`
  display: flex;
  justify-content: end;
  gap: 16px;
  width: 100%;
`;

const LabelTextDropdown = styled.div`
  position: relative;
`;
