import { useEffect, useState } from "react";
import { styled, useTheme } from "styled-components";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { Button } from "../Button";
import { InformationTag } from "../InformationTag";
import { TextArea } from "../TextArea";

type CommentItemProps = {
  id: number;
  userId: string;
  avatarUrl: string;
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
  writer: {
    id: number;
    name: string;
    avatarUrl: string;
  };
  fetchIssue: () => void;
};

export function CommentItem({
  id,
  userId,
  avatarUrl,
  content: initialContent,
  createdAt,
  modifiedAt,
  writer,
  fetchIssue,
}: CommentItemProps) {
  const theme = useTheme();

  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(initialContent);
  const [invalidContent, setInvalidContent] = useState(false);
  const [errorDescription, setErrorDescription] = useState("");

  useEffect(() => {
    setContent(initialContent);
  }, [initialContent]);

  const maxContentLength = 10000;
  const writtenAt = modifiedAt ?? createdAt;

  const validateContent = (value: string) => {
    const emptyContent = value.length === 0;
    const sameContent = value === initialContent;
    const overflowContent = value.length > maxContentLength;

    setInvalidContent(emptyContent || sameContent || overflowContent);
    setErrorDescription(
      emptyContent
        ? "내용을 입력해주세요"
        : sameContent
        ? "기존 내용과 동일합니다"
        : overflowContent
        ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
        : "",
    );
  };

  const deleteComment = async () => {
    await fetch(`/api/comments/${id}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("accessToken")}`,
        "credentials": "include",
      },
    });

    fetchIssue();
  }

  const onEditButtonClick = () => {
    setIsEditing(true);
    validateContent(content);
  };

  const onEditCancelButtonClick = () => {
    setContent(initialContent);
    setIsEditing(false);
  };

  const onContentChange = (value: string) => {
    setContent(value);
    validateContent(value);
  };

  const onEditConfirmClick = async () => {
    await fetch(`/api/comments/${id}`, {
      method: "PATCH",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("accessToken")}`,
        "credentials": "include",
      },
      body: JSON.stringify({
        content: content,
      }),
    });

    setContent(initialContent);
    setIsEditing(false);
    setInvalidContent(false);
    setErrorDescription("");
    fetchIssue();
  };

  return (
    <>
      <TextArea
        value={content}
        width="100%"
        isEditing={isEditing}
        onChange={onContentChange}
      >
        <WriterInfo>
          {avatarUrl && (
            <img
              width="32"
              height="32"
              style={{ borderRadius: "50%" }}
              src={avatarUrl}
              alt="아바타"
            />
          )}
          <h3>{userId}</h3>
          <TimeStamp>{getElapsedSince(writtenAt)} 전</TimeStamp>
        </WriterInfo>
        <ControlButtons>
          {userId === writer.name && (
            <InformationTag
              value="작성자"
              size="S"
              fill="neutralSurfaceBold"
              stroke="Default"
            />
          )}
          <Button
            height={32}
            size="S"
            buttonType="Ghost"
            icon="Trash"
            color={theme.color.dangerTextDefault}
            onMouseDown={(e) => {
              e.preventDefault();
              deleteComment();
            }}
          >
            삭제
          </Button>
          <Button
            height={32}
            size="S"
            buttonType="Ghost"
            icon="Edit"
            onClick={onEditButtonClick}
          >
            편집
          </Button>
          <Button
            height={32}
            size="S"
            buttonType="Ghost"
            icon="Smile"
            onMouseDown={(e) => {
              e.preventDefault();
            }}
          >
            반응
          </Button>
        </ControlButtons>
      </TextArea>
      {isEditing && errorDescription && (
        <ErrorDescription>{errorDescription}</ErrorDescription>
      )}
      {isEditing && (
        <EditorButtons>
          <Button
            size="S"
            buttonType="Outline"
            icon="XSquare"
            onClick={onEditCancelButtonClick}
          >
            편집 취소
          </Button>
          <Button
            size="S"
            buttonType="Container"
            icon="Edit"
            disabled={invalidContent}
            onClick={onEditConfirmClick}
          >
            편집 완료
          </Button>
        </EditorButtons>
      )}
    </>
  );
}

const WriterInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.color.neutralTextDefault};
`;

const TimeStamp = styled.span`
  color: ${({ theme }) => theme.color.neutralTextWeak};
`;

const ControlButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;

  & button span {
    padding: 0;
    padding-left: 4px;
  }
`;

const ErrorDescription = styled.span`
  display: flex;
  padding-left: 0px;
  align-items: flex-start;
  align-self: stretch;
  padding-left: 16px;
  color: ${({ theme }) => theme.color.dangerTextDefault};
  font: ${({ theme }) => theme.font.displayMedium12};
`;

const EditorButtons = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 16px;
`;
