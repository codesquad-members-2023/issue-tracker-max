import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { Avatar } from "../../components/Avatar";
import { Button } from "../../components/Button";
import { InformationTag } from "../../components/InformationTag";
import { TextArea } from "../../components/TextArea";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { getElapsedSince } from "../../utils/getElapsedSince";
import { IssueDetailMainContentProps } from "./IssueDetailMainContent";

type IssueContentProps = Omit<IssueDetailMainContentProps, "comments">;

export function IssueContent({
  id,
  content: initialContent,
  createdAt,
  modifiedAt,
  reactions,
  writer,
  fetchIssue,
}: IssueContentProps) {
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
    const sameContent = value === initialContent;
    const overflowContent = value.length > maxContentLength;

    setInvalidContent(sameContent || overflowContent);
    setErrorDescription(
      sameContent
        ? "기존 내용과 동일합니다"
        : overflowContent
        ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
        : "",
    );
  };

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
    await fetch(`/api/issues/${id}/content`, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        credentials: "include",
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
