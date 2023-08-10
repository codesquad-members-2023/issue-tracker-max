import { styled } from "styled-components";
import LabelInput from "../common/TextInput/LabelInput";
import Button from "../common/Button/Button";

type Props = {
  id?: number;
  type?: "edit" | "add";
  title: string;
  description: string;
  deadline: string;
  onChangeTitle(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeDescription(e: React.ChangeEvent<HTMLInputElement>): void;
  onChangeDeadline(e: React.ChangeEvent<HTMLInputElement>): void;
  cancelEdit(): void;
  confirmEdit(): void;
};

export default function EditMilestone({
  id,
  type = "edit",
  title,
  description,
  deadline,
  onChangeTitle,
  onChangeDescription,
  onChangeDeadline,
  cancelEdit,
  confirmEdit,
}: Props) {
  const confirmEditMilestone = async () => {
    const URL = `http://3.34.141.196/api/milestones/${id}`;

    const putData = {
      title: title,
      description: description,
      deadline: deadline,
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
    <Container $type={type}>
      <Title>
        {type === "edit" ? "마일스톤 편집" : "새로운 마일스톤 추가"}
      </Title>
      <InputWrapper>
        <UpperWrapper>
          <LabelInput
            id={"name"}
            label={"이름"}
            placeholder={"마일스톤의 이름을 입력하세요"}
            value={title}
            onChange={onChangeTitle}
          />
          <LabelInput
            id={"deadline"}
            label={"완료일(선택)"}
            placeholder={"YYYY-MM-DD"}
            value={deadline}
            onChange={onChangeDeadline}
          />
        </UpperWrapper>
        <LabelInput
          id={"description"}
          label={"설명(선택)"}
          placeholder={"마일스톤에 대한 설명을 입력하세요"}
          value={description}
          onChange={onChangeDescription}
        />
      </InputWrapper>
      <ButtonWrapper>
        <Button
          icon={"xSquare"}
          label={"취소"}
          type={"outline"}
          onClick={cancelEdit}
        />
        <Button
          icon={"plus"}
          label={"완료"}
          onClick={type === "edit" ? confirmEditMilestone : confirmEdit}
          disabled={!title}
        />
      </ButtonWrapper>
    </Container>
  );
}

const Container = styled.li<{ $type: "edit" | "add" }>`
  display: flex;
  flex-direction: column;
  gap: 24px;
  padding: 36px 32px 32px 32px;
  width: 100%;
  height: 284px;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border: ${({ $type, theme }) =>
    $type === "add"
      ? `${theme.border.default} ${theme.colorSystem.neutral.border.default}`
      : ""};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const Title = styled.h1`
  width: 100%;
  font: ${({ theme }) => theme.font.displayBold20};
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: end;
  gap: 16px;
  width: 100%;
`;

const UpperWrapper = styled.div`
  display: flex;
  gap: 16px;
`;
