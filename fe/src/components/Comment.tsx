import editIcon from "@assets/icon/edit.svg";
import smileIcon from "@assets/icon/smile.svg";
import xSquareIcon from "@assets/icon/xSquare.svg";
import { convertPastTimestamp } from "@utils/time";
import { putIssueComment, putIssueContent } from "api";
import { useAuth } from "context/authContext";
import { useState } from "react";
import ReactMarkdown from "react-markdown";
import { styled } from "styled-components";
import { Avatar } from "./common/Avatar";
import Button from "./common/Button";
import FileUploadArea from "./common/TextArea/FileUploadArea";
import TextAreaWrapper from "./common/TextArea/TextAreaWrapper";

export default function Comment({
  issueNumber,
  commentId,
  author,
  createdAt,
  content,
  isIssueAuthor,
  updateContent,
}: {
  issueNumber: number;
  commentId?: number;
  author: {
    username: string;
    profileUrl: string;
  };
  createdAt: string;
  content: string;
  isIssueAuthor: boolean;
  updateContent: (newContent: string, commendId?: number) => void;
}) {
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [prevContent, setPrevContent] = useState<string>(content);
  const [newContent, setNewContent] = useState<string>(content);

  const { userInfo } = useAuth();
  const isAuthor = userInfo.username === author.username;

  if (prevContent !== content) {
    setPrevContent(content);
    setNewContent(content);
  }

  const isChangedContent = prevContent !== newContent;

  const onContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setNewContent(e.target.value);
  };

  const appendContent = (content: string) => {
    setNewContent((prev) => `${prev} ${content}`);
  };

  const onCancelEditing = () => {
    setIsEditing(false);
    setNewContent(content);
  };

  const onEditComplete = async () => {
    setIsEditing(false);
    const body = { content: newContent };

    try {
      const { status } = commentId
        ? await putIssueComment(issueNumber, commentId, body)
        : await putIssueContent(issueNumber, body);

      if (status === 200) {
        updateContent(newContent, commentId);
      }
    } catch (error) {
      // TODO: error handling
      console.log(error);
    }
  };

  return (
    <>
      <StyledComment>
        <Header>
          <div className="left-wrapper">
            <Avatar
              src={author.profileUrl}
              alt={`${author.username}-avatar`}
              $size="M"
            />
            <span className="comment-author">{author.username}</span>
            <span className="comment-date">
              {createdAt && convertPastTimestamp(createdAt)}
            </span>
          </div>

          <div className="right-wrapper">
            {isIssueAuthor && <AuthorTag>작성자</AuthorTag>}

            {isAuthor && (
              <Button
                variant="ghost"
                size="S"
                onClick={() => setIsEditing(true)}>
                <img className="button-icon" src={editIcon} alt="코멘트 편집" />
                <span className="button-text">편집</span>
              </Button>
            )}

            <Button variant="ghost" size="S">
              <img className="button-icon" src={smileIcon} alt="코멘트 반응" />
              <span className="button-text">반응</span>
            </Button>
          </div>
        </Header>
        <Body>
          {isEditing ? (
            <>
              <TextAreaWrapper
                value={newContent}
                onChange={onContentChange}
                rows={5}
              />
              <FileUploadArea {...{ appendContent }} />
            </>
          ) : (
            <div className="comment-wrapper">
              <ReactMarkdown children={content} />
            </div>
          )}
        </Body>
      </StyledComment>
      {isEditing && (
        <ButtonWrapper>
          <Button variant="outline" size="S" onClick={onCancelEditing}>
            <img
              className="button-icon"
              src={xSquareIcon}
              alt="코멘트 편집 취소"
            />
            <span className="button-text">편집 취소</span>
          </Button>
          <Button
            variant="container"
            size="S"
            disabled={!isChangedContent}
            onClick={onEditComplete}>
            <img
              className="button-icon"
              src={editIcon}
              alt="코멘트 편집 완료"
            />
            <span className="button-text">편집 완료</span>
          </Button>
        </ButtonWrapper>
      )}
    </>
  );
}

const StyledComment = styled.div`
  width: 100%;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
`;

const Header = styled.header`
  width: 100%;
  height: 64px;
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};

  .left-wrapper {
    display: flex;
    align-items: center;
    gap: 8px;

    .comment-author {
      font: ${({ theme: { font } }) => font.displayMD16};
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }

    .comment-date {
      font: ${({ theme: { font } }) => font.displayMD16};
      color: ${({ theme: { neutral } }) => neutral.text.weak};
    }
  }

  .right-wrapper {
    display: flex;
    gap: 16px;

    .button-icon {
      filter: ${({ theme: { filter } }) => filter.neutralTextDefault};
    }

    .button-text {
      font: ${({ theme: { font } }) => font.displayMD12};
      color: ${({ theme: { neutral } }) => neutral.text.default};
    }
  }
`;

const AuthorTag = styled.span`
  height: 24px;
  padding-inline: 12px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.bold};
  font: ${({ theme: { font } }) => font.displayMD12};
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
  line-height: 24px;
`;

const Body = styled.div`
  width: 100%;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.l};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.l};
  font: ${({ theme: { font } }) => font.displayMD16};
  color: ${({ theme: { neutral } }) => neutral.text.default};

  .comment-wrapper {
    padding: 16px 24px;
  }
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  width: 100%;
`;
