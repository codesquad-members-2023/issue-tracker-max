import { styled } from "styled-components";
import { AssigneesList } from "../../type";
import UserProfileButton from "../UserProfileButton/UserProfileButton";
import { calculateTime } from "../../utils/calculateTime";
import Button from "../common/Button/Button";
import { useEffect, useState } from "react";

type Props = {
  id: number;
  commentId?: number;
  type?: "content" | "comment";
  author: AssigneesList;
  createAt: string;
  content: string;
};

export default function Comment({
  id,
  commentId,
  type = "comment",
  author,
  createAt,
  content,
}: Props) {
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const [commentContent, setCommentContent] = useState<string>(content);
  const [showCounter, setShowCounter] = useState<boolean>(false);

  const editComment = () => {
    setIsEdit(true);
  };

  const cancelEditComment = () => {
    setCommentContent(content);
    setIsEdit(false);
  };

  const handleCommentInputChange = (
    e: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    const { target } = e;
    target.style.height = "auto";
    target.style.height = `${target.scrollHeight}px`;
    setCommentContent(e.target.value);
  };

  const updateContent = async () => {
    const URL = `http://3.34.141.196/api/issues/${id}/content`; // PATCH 요청을 보낼 리소스 URL로 변경

    const patchData = {
      content: commentContent,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  const updateComment = async () => {
    const URL = `http://3.34.141.196/api/issues/${id}/comments/${commentId}`; // PATCH 요청을 보낼 리소스 URL로 변경

    const patchData = {
      content: commentContent,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "PATCH",
        headers: headers,
        body: JSON.stringify(patchData),
      });

      if (response.status === 204) {
        window.location.reload();
      } else {
        console.log("PATCH 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  useEffect(() => {
    let timer: NodeJS.Timeout | null = null;

    if (commentContent) {
      setShowCounter(true);
      timer = setTimeout(() => {
        setShowCounter(false);
      }, 2000);
    }

    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [commentContent]);

  return (
    <Container>
      <CommentWrapper $isEdit={isEdit}>
        <Header>
          <Info>
            <UserProfileButton src={author.profileImageUrl} />
            <UserName>{author.nickname}</UserName>
            <Time>{calculateTime(createAt)}</Time>
          </Info>
          <ButtonTap>
            <Button
              icon={"edit"}
              label={"편집"}
              type={"ghost"}
              height={"32px"}
              onClick={editComment}
            />
            <Button
              icon={"smile"}
              label={"반응"}
              type={"ghost"}
              height={"32px"}
              onClick={() => {}}
            />
          </ButtonTap>
        </Header>
        {!isEdit && (
          <ContentWrapper $isEdit={isEdit}>
            <Content readOnly value={commentContent}></Content>
          </ContentWrapper>
        )}
        {isEdit && (
          <EditWrapper>
            <ContentWrapper $isEdit={isEdit}>
              <Content
                value={commentContent}
                onChange={handleCommentInputChange}
              ></Content>
            </ContentWrapper>
            <CountWrapper>
              {showCounter && (
                <Counter>띄어쓰기 포함 {commentContent.length}자</Counter>
              )}
              <IconImg src={"/icons/grip.svg"} />
            </CountWrapper>
            <AddFileWrapper>
              <AddFileInput type={"file"} />
            </AddFileWrapper>
          </EditWrapper>
        )}
      </CommentWrapper>
      {isEdit && (
        <EditButtons>
          <Button
            icon={"xSquare"}
            label={"편집 취소"}
            type={"outline"}
            onClick={cancelEditComment}
          />
          <Button
            icon={"edit"}
            label={"편집 완료"}
            onClick={type === "content" ? updateContent : updateComment}
            disabled={commentContent === content}
          />
        </EditButtons>
      )}
    </Container>
  );
}

const Container = styled.li`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 100%;
  height: min-content;
`;

const Header = styled.div`
  padding: 16px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 64px;
  border-top-left-radius: inherit;
  border-top-right-radius: inherit;
`;

const Info = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
  height: 32px;
  font: ${({ theme }) => theme.font.displayMedium16};
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.default};
`;

const UserName = styled.span`
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
`;

const Time = styled.span`
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const ButtonTap = styled.div`
  display: flex;
  gap: 16px;
`;

const ContentWrapper = styled.div<{ $isEdit: boolean }>`
  position: relative;
  padding: ${({ $isEdit }) => ($isEdit ? "16px" : "16px 24px 24px 24px")};
  width: 100%;
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border-bottom-left-radius: inherit;
  border-bottom-right-radius: inherit;
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const Content = styled.textarea`
  padding: 0px;
  width: 100%;
  min-height: 24px;
  resize: none;
  border: none;
  outline: none;
  overflow: hidden;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.default};
  background-color: transparent;
`;

const CommentWrapper = styled.div<{ $isEdit: boolean }>`
  display: flex;
  flex-direction: column;
  border: ${({ $isEdit, theme }) =>
    $isEdit
      ? `${theme.border.default} ${theme.colorSystem.neutral.border.defaultActive}`
      : `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.large};
`;

const EditButtons = styled.div`
  display: flex;
  gap: 16px;
  justify-content: end;
  width: 100%;
`;

const CountWrapper = styled.div`
  display: flex;
  justify-content: end;
  align-items: center;
  gap: 8px;
  width: 100%;
  height: 52px;
  padding: 16px;
`;

const Counter = styled.span`
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const IconImg = styled.img`
  width: 20px;
  height: 20px;
`;

const AddFileWrapper = styled.div`
  padding: 10px 16px;
  display: flex;
  align-items: center;
  width: 100%;
  height: 52px;
  border-top: ${({ theme }) =>
    `${theme.border.dash} ${theme.colorSystem.neutral.border.default}`};
`;

const AddFileInput = styled.input``;

const EditWrapper = styled.div`
  background-color: ${({ theme }) => theme.colorSystem.neutral.surface.strong};
  border-bottom-left-radius: inherit;
  border-bottom-right-radius: inherit;
`;
