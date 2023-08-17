import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { Avatar } from "../../components/Avatar";
import { Button } from "../../components/Button";
import { InformationTag } from "../../components/InformationTag";
import { TextArea } from "../../components/TextArea";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { getAccessToken, getUserInfo } from "../../utils/localStorage";
import { IssueDetailMainContentProps } from "./IssueDetailMainContent";

type IssueContentProps = Omit<IssueDetailMainContentProps, "comments">;

export function IssueContent({
  id,
  content: initialContent,
  createdAt,
  modifiedAt,
  writer,
  fetchIssue,
}: IssueContentProps) {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState(initialContent);

  useEffect(() => {
    setContent(initialContent);
  }, [initialContent]);

  const maxContentLength = 10000;
  const writtenAt = modifiedAt ?? createdAt;
  const loginUserInfo = getUserInfo();

  const sameContent = content === initialContent;
  const overflowContent = content.length > maxContentLength;
  const invalidContent = sameContent || overflowContent;
  const errorDescription = sameContent
    ? "기존 내용과 동일합니다"
    : overflowContent
    ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
    : "";

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
    await fetch(`/api/issues/${id}/content`, {
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

    setContent(initialContent);
    setIsEditing(false);
    fetchIssue();
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
          <Avatar size="L" src={writer.avatarUrl} userId={writer.name} />
          <h3>{writer.name}</h3>
          <TimeStamp>{getElapsedSince(writtenAt)} 전</TimeStamp>
        </WriterInfo>
        <ControlButtons>
          <InformationTag
            value="작성자"
            size="S"
            fill="neutralSurfaceBold"
            stroke="Default"
          />
          {writer.name === loginUserInfo?.loginId && (
            <Button
              height={32}
              size="S"
              buttonType="Ghost"
              icon="Edit"
              onClick={onEditButtonClick}
            >
              편집
            </Button>
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
