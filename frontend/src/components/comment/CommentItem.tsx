import { useEffect, useState } from "react";
import { styled, useTheme } from "styled-components";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { getAccessToken, getUserInfo } from "../../utils/localStorage";
import { Avatar } from "../Avatar";
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

  useEffect(() => {
    setContent(initialContent);
  }, [initialContent]);

  const maxContentLength = 10000;
  const writtenAt = modifiedAt ?? createdAt;
  const loginUserInfo = getUserInfo();
  
  const emptyContent = content.length === 0;
  const sameContent = content === initialContent;
  const overflowContent = content.length > maxContentLength;
  const invalidContent = emptyContent || sameContent || overflowContent;
  const errorDescription = emptyContent
        ? "내용을 입력해주세요"
        : sameContent
        ? "기존 내용과 동일합니다"
        : overflowContent
        ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
        : "";

  const deleteComment = async () => {
    const response = await fetch(`/api/comments/${id}`, {
      method: "DELETE",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const { code, message } = await response.json();

    if (code === 200) {
      fetchIssue();
      return;
    }

    throw new Error(message);
  };

  const onEditButtonClick = () => {
    setIsEditing(true);
  };

  const onEditCancelButtonClick = () => {
    setContent(initialContent);
    setIsEditing(false);
  };

  const onContentChange = (value: string) => {
    setContent(value);
  };

  const onEditConfirmClick = async () => {
    if (invalidContent) {
      return;
    }

    const response = await fetch(`/api/comments/${id}`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify({
        content: content,
      }),
    });
    const { code, message, data } = await response.json();

    if (code === 200) {
      setContent(initialContent);
      setIsEditing(false);
      fetchIssue();
      return;
    }

    const errorMessage = data ? data[0].defaultMessage : message;
    
    throw new Error(errorMessage);
  };

  return (
    <>
      <TextArea
        value={content}
        width="100%"
        isEditing={isEditing}
        errorDescription={errorDescription}
        onChange={onContentChange}
      >
        <WriterInfo>
          <Avatar size="L" src={avatarUrl} userId={userId} />
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
          {userId === loginUserInfo?.loginId && (
            <>
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
            </>
          )}
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

const EditorButtons = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 16px;
`;
