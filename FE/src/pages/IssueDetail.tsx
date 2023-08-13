import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import { FetchedDetail } from "../type";
import LabelInput from "../components/common/TextInput/LabelInput";
import { calculateTime } from "../utils/calculateTime";
import SideBar from "../components/SideBar/SideBar";
import Comment from "../components/Comment/Comment";
import TextArea from "../components/common/TextArea/TextArea";
import InformationTag from "../components/InformationTag/InformationTag";

export default function IssueDetail() {
  // const [isLoading, setIsLoading] = useState<boolean>(true);
  const [data, setData] = useState<FetchedDetail>();
  const [isEditTitle, setIsEditTitle] = useState<boolean>(false);
  const [issueTitle, setIssueTitle] = useState<string | undefined>(data?.title);
  const [addComment, setAddComment] = useState<string>("");
  const { id } = useParams();

  const editTitle = () => {
    setIsEditTitle(true);
  };

  const cancelEditTitle = () => {
    setIssueTitle(data?.title);
    setIsEditTitle(false);
  };

  const confirmEditTitle = () => {
    setIsEditTitle(false);
    updateTitle();
  };

  const handleTitleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIssueTitle(e.target.value);
  };

  const handleAddCommentInputChange = (
    e: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setAddComment(e.target.value);
  };

  const updateTitle = async () => {
    const URL = `http://3.34.141.196/api/issues/${data!.id}/title`;

    const patchData = {
      title: issueTitle,
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

  const closeIssue = async () => {
    const URL = `http://3.34.141.196/api/issues/${data!.id}/open`;

    const patchData = {
      isOpen: false,
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

  const openIssue = async () => {
    const URL = `http://3.34.141.196/api/issues/${data!.id}/open`;

    const patchData = {
      isOpen: true,
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

  const addNew = async () => {
    const URL = `http://3.34.141.196/api/issues/${data?.id}/comments`;

    const postData = {
      content: addComment,
    };

    const headers = {
      "Content-Type": "application/json",
    };

    try {
      const response = await fetch(URL, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(postData),
      });

      if (response.status === 200) {
        window.location.reload();
      } else {
        console.log("POST 요청에 실패하였습니다. 상태 코드:", response.status);
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://3.34.141.196/api/issues/${id}`);
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const responseData = await response.json();
        console.log(responseData);
        setData(responseData);
        setIssueTitle(data?.title);
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        // setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <Main>
      <IssueHeader>
        <TitleWrapper>
          {!isEditTitle && (
            <Title>
              <IssueTitle>{data?.title}</IssueTitle>
              <IssueNumber>#{data?.id}</IssueNumber>
            </Title>
          )}
          {isEditTitle && data && (
            <Title>
              <LabelInput
                id={"title"}
                label={"제목"}
                placeholder={"제목을 입력해주세요"}
                value={issueTitle}
                onChange={handleTitleInputChange}
              />
            </Title>
          )}
          {!isEditTitle && (
            <HeaderButtons>
              <Button
                icon={"edit"}
                label={"제목 편집"}
                type={"outline"}
                onClick={editTitle}
              />
              <Button
                icon={"archive"}
                label={data?.isOpen ? "이슈 닫기" : "이슈 열기"}
                type={"outline"}
                onClick={data?.isOpen ? closeIssue : openIssue}
              />
            </HeaderButtons>
          )}
          {isEditTitle && (
            <HeaderButtons>
              <Button
                icon={"xSquare"}
                label={"편집 취소"}
                type={"outline"}
                onClick={cancelEditTitle}
              />
              <Button
                icon={"edit"}
                label={"편집 완료"}
                onClick={confirmEditTitle}
                disabled={!issueTitle}
              />
            </HeaderButtons>
          )}
        </TitleWrapper>
        <StateInfo>
          <InformationTag
            type={data?.isOpen ? "open" : "close"}
            icon={"alertCircle"}
            label={data?.isOpen ? "열린 이슈" : "닫힌 이슈"}
            size={"L"}
          />
          {data && (
            <LogInfo>
              이 이슈가 {calculateTime(data.createAt)} {data.author.nickname}
              님에 의해 열렸습니다
            </LogInfo>
          )}
          {data?.comments?.length !== 0 && (
            <CommentCounterWrapper>
              <Divider>∙</Divider>
              <CommentCounter>코멘트 {data?.comments?.length}개</CommentCounter>
            </CommentCounterWrapper>
          )}
        </StateInfo>
      </IssueHeader>
      <IssueContents>
        <Comments>
          {data && (
            <Comment
              id={data.id}
              type={"content"}
              author={data.author}
              createAt={data.createAt}
              content={data.content}
            />
          )}
          {data &&
            data.comments &&
            data.comments.map((comment) => (
              <Comment
                key={comment.id}
                id={data.id}
                commentId={comment.id}
                author={comment.author}
                createAt={comment.createAt}
                content={comment.content}
              />
            ))}
          <AddCommentWrapper>
            <TextArea
              type={"small"}
              inputValue={addComment}
              onChange={handleAddCommentInputChange}
            />
            <AddButtonWrapper>
              <Button
                icon={"plus"}
                label={"코멘트 작성"}
                onClick={addNew}
                disabled={!addComment}
              />
            </AddButtonWrapper>
          </AddCommentWrapper>
        </Comments>
        <SideBar />
      </IssueContents>
    </Main>
  );
}

const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 50px;
  width: 1280px;
  margin-bottom: 100px;
`;

const IssueHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 100%;
`;

const TitleWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const Title = styled.div`
  display: flex;
  gap: 8px;
  width: 992px;
  font: ${({ theme }) => theme.font.displayBold32};
`;

const IssueTitle = styled.h1`
  color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
`;

const IssueNumber = styled.span`
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
`;

const HeaderButtons = styled.div`
  display: flex;
  gap: 16px;
`;

const StateInfo = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
  height: 32px;
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme }) => theme.colorSystem.neutral.text.weak};
  &::after {
    content: "";
    position: absolute;
    bottom: -24px;
    left: 0px;
    width: 1280px;
    height: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
`;

const LogInfo = styled.span``;

const Divider = styled.span``;

const CommentCounter = styled.span``;

const IssueContents = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Comments = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 960px;
`;

const CommentCounterWrapper = styled.div``;

const AddCommentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 24px;
  width: 100%;
`;

const AddButtonWrapper = styled.div`
  display: flex;
  width: 100%;
  justify-content: end;
`;
