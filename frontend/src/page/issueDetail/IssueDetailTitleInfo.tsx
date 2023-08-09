import { ChangeEvent, useEffect, useState } from "react";
import { styled } from "styled-components";
import { Button } from "../../components/Button";
import { TextInput } from "../../components/TextInput";

export function IssueDetailTitleInfo({
  id,
  title: initialTitle,
  status,
  fetchIssue,
}: {
  id: number;
  title: string;
  status: "OPENED" | "CLOSED";
  fetchIssue: () => void;
}) {
  const [isTitleEditing, setIsTitleEditing] = useState(false);
  const [title, setTitle] = useState("");

  useEffect(() => {
    setTitle(initialTitle);
  }, [initialTitle]);

  const sameTitle = title === initialTitle;
  const overflowTitle = title.length > 50;
  const emptyTitle = title.length < 1;
  const invalidTitle = sameTitle || overflowTitle || emptyTitle;
  const errorDescription = invalidTitle
    ? sameTitle
      ? "기존 제목과 동일합니다"
      : emptyTitle
      ? "제목을 입력해주세요"
      : "제목은 50자 이내로 입력해주세요"
    : "";

  const onTitleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const onEditClick = async () => {
    await fetch(`/api/issues/${id}/title`, {
      method: "PATCH",
      body: JSON.stringify({ title }),
    });

    setIsTitleEditing(false);
    fetchIssue();
  };

  const onStatusChangeClick = async () => {
    await fetch(`/api/issues/${id}/status`, {
      method: "PATCH",
      body: JSON.stringify({
        status: status === "OPENED" ? "CLOSED" : "OPENED",
      }),
    });

    fetchIssue();
  };

  return (
    <TitleInfo>
      {isTitleEditing ? (
        <>
          <TextInput
            size="S"
            label="제목"
            value={title}
            fixLabel
            placeholder="이슈 제목을 입력하세요"
            isError={invalidTitle}
            caption={errorDescription}
            onChange={onTitleChange}
          />
          <Button
            size="S"
            buttonType="Outline"
            icon="XSquare"
            onClick={() => setIsTitleEditing(false)}
          >
            편집 취소
          </Button>
          <Button
            size="S"
            buttonType="Container"
            icon="Edit"
            disabled={invalidTitle}
            onClick={onEditClick}
          >
            편집 완료
          </Button>
        </>
      ) : (
        <>
          <Title>
            {initialTitle}
            <span>#{id}</span>
          </Title>
          <Button
            size="S"
            buttonType="Outline"
            icon="Edit"
            onClick={() => setIsTitleEditing(true)}
          >
            제목 편집
          </Button>
          <Button
            size="S"
            buttonType="Outline"
            icon="Archive"
            onClick={onStatusChangeClick}
          >
            {status === "OPENED" ? "이슈 닫기" : "다시 열기"}
          </Button>
        </>
      )}
    </TitleInfo>
  );
}

const TitleInfo = styled.div`
  align-self: stretch;
  display: flex;
  align-items: flex-start;
  gap: 16px;

  & > div {
    flex: 1 0 0;
  }
`;

const Title = styled.h2`
  flex: 1 0 0;
  display: flex;
  gap: 8px;
  font: ${({ theme }) => theme.font.displayBold32};
  color: ${({ theme }) => theme.color.neutralTextStrong};

  & > span {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;
