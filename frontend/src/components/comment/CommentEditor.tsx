import { useState } from "react";
import { styled } from "styled-components";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { Button } from "../Button";
import { TextArea } from "../TextArea";
import { getAccessToken } from "../../utils/localStorage";

type CommentEditorProps = {
  issueId: number;
  fetchIssue: () => void;
};

export function CommentEditor({ issueId, fetchIssue }: CommentEditorProps) {
  const [content, setContent] = useState("");
  const [invalidContent, setInvalidContent] = useState(false);
  const [errorDescription, setErrorDescription] = useState("");

  const maxContentLength = 10000;

  const validateContent = (value: string) => {
    const emptyContent = value.length === 0;
    const overflowContent = value.length > maxContentLength;

    setInvalidContent(emptyContent || overflowContent);
    setErrorDescription(
      emptyContent
        ? "내용을 입력해주세요"
        : overflowContent
        ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
        : "",
    );
  };

  const onEditorFocus = () => {
    validateContent(content);
  };

  const onContentChange = (value: string) => {
    setContent(value);
    validateContent(value);
  };

  const onContentSubmit = async () => {
    if (invalidContent) {
      return;
    }

    await fetch("/api/comments", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${getAccessToken()}`,
        "credentials": "include",
      },
      body: JSON.stringify({ issueId, content }),
    });

    setContent("");
    setInvalidContent(false);
    setErrorDescription("");
    fetchIssue();
  };

  return (
    <Div>
      <TextArea
        value={content}
        width="100%"
        height={200}
        placeholder="코멘트를 입력하세요"
        label="코멘트를 입력하세요"
        onChange={onContentChange}
        onTextAreaFocus={onEditorFocus}
      />
      {errorDescription && (
        <ErrorDescription>{errorDescription}</ErrorDescription>
      )}
      <Button
        size="S"
        buttonType="Container"
        icon="Plus"
        disabled={invalidContent}
        onClick={onContentSubmit}
      >
        코멘트 작성
      </Button>
    </Div>
  );
}

const Div = styled.div`
  align-self: stretch;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 24px;
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
