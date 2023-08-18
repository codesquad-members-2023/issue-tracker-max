import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import { FetchedDetail } from "../type";
import LabelInput from "../components/common/TextInput/LabelInput";
import { calculateTime } from "../utils/calculateTime";
import SideBar from "../components/SideBar/SideBar";
import Comment from "../components/Comment/Comment";
import TextArea from "../components/common/TextArea/TextArea";
import InformationTag from "../components/InformationTag/InformationTag";
import Alert from "../components/Alert/Alert";
import Header from "../components/Header/Header";
import { useAuth } from "../context/AuthContext";

type Props = {
  toggleTheme(): void;
};

export default function IssueDetail({ toggleTheme }: Props) {
  const navigate = useNavigate();
  const { profile } = useAuth();
  const [data, setData] = useState<FetchedDetail>();
  const [isEditTitle, setIsEditTitle] = useState<boolean>(false);
  const [issueTitle, setIssueTitle] = useState<string | undefined>(data?.title);
  const [addComment, setAddComment] = useState<string>("");
  const [openDeleteAlert, setOpenDeleteAlert] = useState<boolean>(false);
  const { id } = useParams();

  const editTitle = () => {
    setIsEditTitle(true);
    setIssueTitle(data?.title);
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

  const showDeleteAlert = () => {
    setOpenDeleteAlert(true);
  };

  const closeDeleteAlert = () => {
    setOpenDeleteAlert(false);
  };

  const updateTitle = async () => {
    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const URL = `http://3.34.141.196/api/issues/${data!.id}/title`;

    const patchData = {
      title: issueTitle,
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

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const patchData = {
      isOpen: false,
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

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const patchData = {
      isOpen: true,
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

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    const postData = {
      content: addComment,
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

  const deleteIssue = async () => {
    const URL = `http://3.34.141.196/api/issues/${data!.id}`;

    const headers = new Headers();
    const accessToken = localStorage.getItem("accessToken");
    headers.append("Authorization", `Bearer ${accessToken}`);
    headers.append("Content-Type", "application/json");

    try {
      const response = await fetch(URL, {
        method: "DELETE",
        headers: headers,
      });

      if (response.status === 204) {
        navigate("/issues/isOpen=true");
      } else {
        console.log(
          "DELETE 요청에 실패하였습니다. 상태 코드:",
          response.status,
        );
      }
    } catch (error) {
      console.error("API 호출 오류:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      const headers = new Headers();
      const accessToken = localStorage.getItem("accessToken");
      headers.append("Authorization", `Bearer ${accessToken}`);
      try {
        const response = await fetch(`http://3.34.141.196/api/issues/${id}`, {
          headers,
        });
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
    <>
      <Header
        toggleTheme={toggleTheme}
        profileImg={profile ? profile.profileImageUri : "/icons/user.png"}
      />
      <Page>
        {data && (
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
                      disabled={!issueTitle || data.title === issueTitle}
                    />
                  </HeaderButtons>
                )}
              </TitleWrapper>
              <StateInfo>
                <InformationTag
                  type={data.isOpen ? "open" : "close"}
                  icon={"alertCircle"}
                  label={data.isOpen ? "열린 이슈" : "닫힌 이슈"}
                  size={"L"}
                />
                {data && (
                  <LogInfo>
                    이 이슈가 {calculateTime(data.createAt)}{" "}
                    {data.author.nickname}
                    님에 의해 작성되었습니다
                  </LogInfo>
                )}
                {data?.comments?.length !== 0 && (
                  <CommentCounterWrapper>
                    <Divider>∙</Divider>
                    <CommentCounter>
                      코멘트 {data?.comments?.length}개
                    </CommentCounter>
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
              <SideBarWrapper>
                {data && (
                  <SideBar
                    type={"detail"}
                    issueId={data.id}
                    assignees={data.assignees}
                    labels={data.labels}
                    milestone={data.milestone}
                  />
                )}
                <DeleteButtonWrapper>
                  <Button
                    icon={"trash"}
                    label={"이슈 삭제"}
                    type={"ghost"}
                    onClick={showDeleteAlert}
                  />
                </DeleteButtonWrapper>
              </SideBarWrapper>
            </IssueContents>
            {openDeleteAlert && (
              <Alert
                content={"이슈를 정말 삭제하시겠습니까?"}
                leftButtonLabel={"취소"}
                rightButtonLabel={"삭제"}
                onClickLeftButton={closeDeleteAlert}
                onClickRightButton={deleteIssue}
              />
            )}
          </Main>
        )}
      </Page>
    </>
  );
}

const Page = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

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

const CommentCounterWrapper = styled.div`
  display: flex;
  gap: 8px;
`;

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

const SideBarWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const DeleteButtonWrapper = styled.div`
  padding: 0px 16px;
  display: flex;
  justify-content: end;
  width: 100%;
`;
