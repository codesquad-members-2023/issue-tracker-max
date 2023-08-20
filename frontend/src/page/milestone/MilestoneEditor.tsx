import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TextInput } from "../../components/TextInput";
import { getAccessToken } from "../../utils/localStorage";
import { MilestoneData } from "./Milestone";

type MilestoneEditorProps = {
  onClickClose: () => void;
  fetchData: () => void;
} & (
  | { type: "add"; milestone?: never }
  | { type: "edit"; milestone: MilestoneData }
);

export function MilestoneEditor({
  onClickClose,
  type,
  milestone,
  fetchData,
}: MilestoneEditorProps) {
  const isEditMode = type === "edit" && milestone;

  const [milestoneName, setMilestoneName] = useState(
    isEditMode ? milestone.name : "",
  );
  const [deadline, setDeadline] = useState(
    isEditMode ? milestone.deadline : "",
  );
  const [description, setDescription] = useState(
    isEditMode ? milestone.description : "",
  );
  const [isValidDeadline, setIsValidDeadline] = useState(true);

  const [isButtonDisabled, setIsButtonDisabled] = useState(true);

  useEffect(() => {
    if (!deadline) {
      setIsValidDeadline(true);
    } else {
      const regex = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
      setIsValidDeadline(regex.test(deadline));
    }
  }, [deadline]);

  useEffect(() => {
    const isMilestoneNameEmpty = milestoneName === "";

    if (isEditMode) {
      const isMilestoneChanged =
        milestone.name !== milestoneName ||
        milestone.deadline !== deadline ||
        milestone.description !== description;

      setIsButtonDisabled(
        !isMilestoneChanged || isMilestoneNameEmpty || !isValidDeadline,
      );
    } else {
      setIsButtonDisabled(isMilestoneNameEmpty || !isValidDeadline);
    }
  }, [
    milestone,
    milestoneName,
    deadline,
    description,
    isValidDeadline,
    isEditMode,
  ]);

  const onChangeMilestoneName = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    const value = event.target.value;

    setMilestoneName(value);
  };

  const onChangeDeadline = (event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;
    value = value.replace(/[^0-9]/g, "");

    if (value.length < 5) {
      value = value.replace(/(\d{4})/, "$1-");
    } else if (value.length < 8) {
      value = value.replace(/(\d{4})(\d{2})/, "$1-$2-");
    } else {
      value = value.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
    }

    setDeadline(value);
  };

  const onChangeDescription = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;

    setDescription(value);
  };

  const submit = async () => {
    const method = type === "add" ? "POST" : "PUT";
    const path = type === "add" ? "" : `/${milestone.id}`;
    const obj = {
      name: milestoneName.trim(),
      description: description.trim(),
      deadline: deadline,
    };

    await fetch(`/api/milestones${path}`, {
      method: method,
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify(obj),
    });

    onClickClose();
    fetchData();
  };

  return (
    <Div>
      <Title>{type === "add" ? "새로운 마일스톤 추가" : "마일스톤 편집"}</Title>
      <Editor>
        <Info>
          <div>
            <TextInput
              width={"100%"}
              size="S"
              label="이름"
              value={milestoneName || ""}
              fixLabel
              placeholder="마일스톤의 이름을 입력하세요."
              onChange={onChangeMilestoneName}
            />
          </div>
          <div>
            <TextInput
              width={"100%"}
              size="S"
              label="완료일(선택)"
              value={deadline || ""}
              fixLabel
              placeholder="YYYY-MM-DD"
              onChange={onChangeDeadline}
              isError={!isValidDeadline}
            />
          </div>
        </Info>
        <TextInput
          width={"100%"}
          size="S"
          label="설명(선택)"
          value={description || ""}
          fixLabel
          placeholder="마일스톤에 대한 설명을 입력하세요"
          onChange={onChangeDescription}
        />
      </Editor>
      <Buttons>
        <Button
          size="S"
          icon="XSquare"
          buttonType="Outline"
          onClick={onClickClose}
        >
          취소
        </Button>
        <Button
          size="S"
          icon="Plus"
          buttonType="Container"
          onClick={submit}
          disabled={isButtonDisabled}
        >
          완료
        </Button>
      </Buttons>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-self: stretch;
  box-sizing: border-box;
  gap: 24px;
`;

const Title = styled.div`
  font: ${({ theme }) => theme.font.displayBold20};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;

const Editor = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const Info = styled.div`
  width: 100%;
  display: flex;
  gap: 16px;
  & > div {
    width: 100%;
  }
`;

const Buttons = styled.div`
  width: 100%;
  display: flex;
  justify-content: right;
  gap: 16px;
`;
