import { useState } from "react";
import { styled } from "styled-components";
import { TextArea } from "../../components/TextArea";
import { TextInput } from "../../components/TextInput";
import { Sidebar } from "../../components/sidebar/Sidebar";

type NewIssueBodyProps = {
  title: string;
  content: string;
  onAssigneeClick: (id: number) => void;
  onLabelClick: (id: number) => void;
  onMilestoneClick: (id: number) => void;
  onTitleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onContentChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
};

export function NewIssueBody({
  title,
  content,
  onAssigneeClick,
  onLabelClick,
  onMilestoneClick,
  onTitleChange,
  onContentChange,
}: NewIssueBodyProps) {
  const [invalidTitle, setInvalidTitle] = useState(false);
  const titleCaption = invalidTitle
    ? "제목은 1글자 이상 50글자 이하로 작성해주세요."
    : "";

  const onTitleFocus = () => {
    setInvalidTitle(title.length === 0);
  };

  // TODO: 제목 에레 상태 안 풀리는 현상 수정

  return (
    <Div>
      <img
        style={{ width: "32px" }}
        src="https://avatars.githubusercontent.com/u/41321198?v=4"
      />
      <NewIssueContent>
        <TextInput
          size="L"
          placeholder="제목"
          label="제목"
          value={title}
          maxLength={50}
          isError={invalidTitle}
          caption={titleCaption}
          onChange={onTitleChange}
          onFocus={onTitleFocus}
        />
        <TextArea
          placeholder="코멘트"
          label="코멘트"
          value={content}
          maxLength={10000}
          onChange={onContentChange}
        />
      </NewIssueContent>
      <Sidebar
        onAssigneeClick={onAssigneeClick}
        onLabelClick={onLabelClick}
        onMilestoneClick={onMilestoneClick}
      />
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  align-items: flex-start;
  gap: 24px;
  flex: 1 0 0;
  align-self: stretch;
`;

const NewIssueContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex: 1 0 0;
  align-self: stretch;

  & > div:first-of-type {
    width: 100%;
    align-self: stretch;
    flex-shrink: 0;

    & > div,
    & input {
      width: 100%;
    }
  }

  & > div:last-of-type {
    align-self: stretch;
  }
`;
