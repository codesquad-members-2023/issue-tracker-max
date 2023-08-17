import { useState } from "react";
import { styled } from "styled-components";
import { addCommasToNumber } from "../../utils/addCommasToNumber";
import { getAccessToken } from "../../utils/localStorage";
import { Button } from "../Button";
import { TextArea } from "../TextArea";

type CommentEditorProps = {
  issueId: number;
  fetchIssue: () => void;
};

export function CommentEditor({ issueId, fetchIssue }: CommentEditorProps) {
  const [content, setContent] = useState("");
  const [isFocused, setIsFocused] = useState(false);

  const maxContentLength = 10000;
  const emptyContent = content.length === 0;
  const overflowContent = content.length > maxContentLength;
  const invalidContent = emptyContent || overflowContent;
  const errorDescription = emptyContent
    ? "내용을 입력해주세요"
    : overflowContent
    ? `내용은 ${addCommasToNumber(maxContentLength)}자 이내로 입력해주세요`
    : "";

  const onEditorFocus = () => {
    setIsFocused(true);
  };

  const onContentChange = (value: string) => {
    setContent(value);
  };

  const onContentSubmit = async () => {
    if (invalidContent) {
      return;
    }

    await fetch("/api/comments", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
      body: JSON.stringify({ issueId, content }),
    });

    setContent("");
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
        isEditing={isFocused}
        errorDescription={errorDescription}
        onChange={onContentChange}
        onTextAreaFocus={onEditorFocus}
      />
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