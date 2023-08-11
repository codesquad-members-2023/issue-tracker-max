import plusIcon from "@assets/icon/plus.svg";
import xIcon from "@assets/icon/xSquare.svg";
import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import { validateDate } from "@utils/time";
import { postMilestone, putMilestoneContent } from "api";
import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

export type MilestoneInfo = {
  milestoneName: string;
  dueDate?: string;
  description?: string;
};

export default function MilestoneEditor({
  variant,
  milestoneId,
  milestoneInfo,
  closeEditor,
}: {
  variant: "add" | "edit";
  milestoneId?: number;
  milestoneInfo?: MilestoneInfo | null;
  closeEditor: () => void;
}) {
  const navigate = useNavigate();

  // TODO: dueDate useInput validate 개선 후 적용
  const [newMilestone, setNewMilestone] = useState({
    milestoneName: milestoneInfo?.milestoneName || "",
    dueDate: milestoneInfo?.dueDate || "",
    description: milestoneInfo?.description || "",
  });

  const isValidDueDate = validateDate(newMilestone.dueDate);

  const onNameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewMilestone((prev) => {
      return { ...prev, milestoneName: e.target.value };
    });
  };

  const onDueDateChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewMilestone((prev) => {
      return { ...prev, dueDate: e.target.value };
    });
  };

  const onDescriptionChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNewMilestone((prev) => {
      return { ...prev, description: e.target.value };
    });
  };

  // TODO: 개선 필요
  const isReadyToSubmit = {
    add: !!newMilestone.milestoneName,
    edit:
      !!newMilestone.milestoneName &&
      (newMilestone.milestoneName !== milestoneInfo?.milestoneName ||
        newMilestone.dueDate !== milestoneInfo?.dueDate ||
        newMilestone.description !== milestoneInfo?.description),
  };

  const onSubmit = async (e: FormEvent) => {
    e.preventDefault();

    // TODO: 수정된 내용이 있는 영역만 보내기
    try {
      const { milestoneName, dueDate, description } = newMilestone;
      const body = {
        milestoneName,
        description,
        dueDate,
      };

      const res = milestoneId
        ? await putMilestoneContent(milestoneId, body)
        : await postMilestone(body);

      if (res.status === 201 || res.status === 200) {
        // TODO: 마일스톤 상태 관리 필요
        navigate(0);
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  return (
    <StyledMilestoneEditor>
      <H2>{variant === "add" ? "새로운 마일스톤 추가" : "마일스톤 편집"}</H2>

      <EditForm onSubmit={onSubmit}>
        <InputsWrapper>
          <div className="upper-wrapper">
            <TextInput
              name="제목"
              variant="short"
              value={newMilestone.milestoneName}
              placeholder="마일스톤의 이름을 입력하세요"
              onChange={onNameChange}
            />
            <TextInput
              name="완료일(선택)"
              variant="short"
              value={newMilestone.dueDate}
              placeholder="YYYY-MM-DD"
              onChange={onDueDateChange}
              hasError={!!newMilestone.dueDate && !isValidDueDate}
              helpText='"YYYY-MM-DD" 형식만 가능해요.'
            />
          </div>
          <TextInput
            name="설명(선택)"
            variant="short"
            value={newMilestone.description}
            placeholder="마일스톤에 대한 설명을 입력하세요"
            onChange={onDescriptionChange}
          />
        </InputsWrapper>
        <ButtonsWrapper>
          <Button
            type="button"
            variant="outline"
            size="S"
            onClick={closeEditor}>
            <img src={xIcon} alt="취소" />
            <span>취소</span>
          </Button>
          <Button
            type="submit"
            variant="container"
            size="S"
            disabled={!isReadyToSubmit[variant]}>
            <img src={plusIcon} alt="완료" />
            <span>완료</span>
          </Button>
        </ButtonsWrapper>
      </EditForm>
    </StyledMilestoneEditor>
  );
}

const StyledMilestoneEditor = styled.div`
  width: 100%;
`;

const EditForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 24px;

  .inner-wrapper {
    display: flex;
    gap: 24px;
  }
`;

const H2 = styled.h2`
  margin-bottom: 24px;
  font: ${({ theme: { font } }) => font.displayBold20};
  color: ${({ theme: { neutral } }) => neutral.text.strong};
`;

const ButtonsWrapper = styled.div`
  display: flex;
  gap: 16px;
  justify-content: flex-end;
`;

const InputsWrapper = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 1rem;

  .upper-wrapper {
    display: flex;
    gap: 1rem;
  }
`;
