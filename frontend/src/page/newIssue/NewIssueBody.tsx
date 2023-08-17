import { useState } from "react";
import { styled } from "styled-components";
import { Avatar } from "../../components/Avatar";
import { TextArea } from "../../components/TextArea";
import { TextInput } from "../../components/TextInput";
import { Sidebar, SidebarProps } from "../../components/sidebar/Sidebar";
import { getUserInfo } from "../../utils/localStorage";

type NewIssueBodyProps = {
  title: string;
  content: string;
  invalidTitle: boolean;
  onTitleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onContentChange: (value: string) => void;
} & SidebarProps;

export function NewIssueBody({
  title,
  content,
  invalidTitle,
  onTitleChange,
  onContentChange,
  ...rest
}: NewIssueBodyProps) {
  const [isFocused, setIsFocused] = useState(false);

  const loginUserInfo = getUserInfo();
  const titleCaption =
    isFocused && invalidTitle
      ? "제목은 1글자 이상 50글자 이하로 작성해주세요."
      : "";

  const onTitleFocus = () => {
    setIsFocused(true);
  };

  return (
    <Div>
      <Avatar
        size="L"
        src={loginUserInfo.avatarUrl}
        userId={loginUserInfo.loginId}
      />
      <NewIssueContent>
        <TextInput
          size="L"
          placeholder="제목"
          label="제목"
          value={title}
          maxLength={50}
          isError={isFocused && invalidTitle}
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
      <Sidebar {...rest} />
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
